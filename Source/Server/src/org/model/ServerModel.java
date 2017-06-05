package org.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.protocol.Command;
import net.protocol.Response;

import org.view.AdminPanel;

public class ServerModel extends java.util.Observable {

	private AdminPanel adminPanel;
	private String whatChanged;

	public ServerModel(AdminPanel adminPanel) {
		this.adminPanel = adminPanel;
		this.addObserver(adminPanel);
	}

	public void setWhatChanged(String whatChanged) {
		this.whatChanged = whatChanged;
	}

	public String getWhatChanged() {
		return whatChanged;
	}

	public Response processCommand(Command command) throws IOException {
		Response response = new Response();
		List<Patient> patients;
		Consultation consultation;
		List<Consultation> consultations;
		Patient newPatient, oldPatient;
		Doctor doctor;
		Date consultationDate;
		switch (command.getCommand()) {
		case CHECK_UPDATE:
			if (adminPanel.getIsChanged() && AdminPanel.nrOfUpdateRequests > 0) {
				patients = getAllPatients();
				consultations = getAllConsultations();
				response.setConsultations(consultations);
				response.setPatients(patients);
				response.setInfo(whatChanged);
				AdminPanel.nrOfUpdateRequests--;
			} else {
				adminPanel.setIsChanged(false);
			}
			break;
		case LOGIN:
			User user = loginCheck(command.getUsers().get(0));
			response.addUser(user);
			break;
		case ALL_PATIENTS:
			patients = getAllPatients();
			response.setPatients(patients);
			break;
		case GET_PATIENTS_BY_DOCTOR:
			patients = getPatientsByDoctor(command.getDoctors().get(0));
			response.setPatients(patients);
			break;
		case ALL_CONSULTATIONS:
			consultations = getAllConsultations();
			response.setConsultations(consultations);
			break;
		case GET_CONSULTATIONS_BY_DOCTOR_AND_PATIENT:
			consultations = getConsultationsByDoctorAndPatient(command.getDoctors().get(0), command.getPatients().get(0));
			response.setConsultations(consultations);
			break;
		case UPDATE_CONSULTATION:
			updateConsultation(command.getConsultations().get(0), command.getConsultations().get(1));
			break;
		case ADD_NEW_PATIENT:
			newPatient = command.getPatients().get(0);
			addNewPatient(newPatient);
			break;
		case GET_CONSULTATION_BY_PATIENT:
			Patient patient = command.getPatients().get(0);
			response.setConsultations(getConsultationsByPatient(patient));
			break;
		case UPDATE_PATIENT:
			oldPatient = command.getPatients().get(0);
			newPatient = command.getPatients().get(1);
			updatePatient(oldPatient, newPatient);
			break;
		case GET_DOCTOR_BY_NAME:
			doctor = getDoctorByName(command.getDoctors().get(0).getName());
			response.addDoctor(doctor);
			break;
		case GET_AVAILABLE_DOCTORS:
			consultationDate = command.getConsultations().get(0).getDate();
			response.setDoctors(getAvailableDoctors(consultationDate));
			break;
		case CREATE_CONSULTATION:
			consultation = command.getConsultations().get(0);
			createConsultation(consultation);
			break;
		case DELETE_CONSULTATION:
			deleteConsultation(command.getConsultations().get(0));
			break;
		case FINISH:
			break;
		default:
			System.out.println("Unsupported command received!");
			break;
		}
		return response;
	}

	public List<Patient> getPatientsByDoctor(Doctor doctor) {
		PatientsModel patientsModel = new PatientsModel();
		List<Patient> allPatients = patientsModel.readPatients();
		List<Patient> patients = new ArrayList<>();
		for (Patient patient : allPatients) {
			List<Consultation> consultations = getConsultationsByDoctorAndPatient(doctor, patient);
			boolean ok = false;
			for (Consultation consultation : consultations) {
				if (consultation.getDoctorID() == doctor.getID())
					ok = true;
			}
			if (ok)
				patients.add(patient);
		}
		return patients;
	}

	public void deleteConsultation(Consultation consultation) {
		ConsultationsModel consultationsModel = new ConsultationsModel();
		consultationsModel.deleteConsultation(consultation);
		PatientsModel patientsModel = new PatientsModel();
		Patient patient = patientsModel.getPatientByCNP(consultation.getClientCNP());
		setWhatChanged(patient.getName() + "'s consultation at " + consultation.getDate().toString() + " got canceled.");
		setChanged();
		notifyObservers();
	}

	public void createConsultation(Consultation consultation) {
		ConsultationsModel consultationsModel = new ConsultationsModel();
		consultationsModel.createConsultation(consultation);
		PatientsModel patientsModel = new PatientsModel();
		Patient patient = patientsModel.getPatientByCNP(consultation.getClientCNP());
		setWhatChanged("New consultation at " + consultation.getDate() + " for " + patient.getName());
		setChanged();
		notifyObservers();
	}

	public List<Doctor> getAvailableDoctors(Date consultationDate) {
		DoctorsModel doctorsModel = new DoctorsModel();
		return doctorsModel.availableDoctors(consultationDate);
	}

	public Doctor getDoctorByName(String name) {
		DoctorsModel doctorsModel = new DoctorsModel();
		return doctorsModel.readDoctorByName(name);
	}

	public void updatePatient(Patient oldPatient, Patient newPatient) {
		PatientsModel patientsModel = new PatientsModel();
		patientsModel.updatePatient(oldPatient, newPatient);
		setWhatChanged("patient update");
		setChanged();
		notifyObservers();
	}

	public void updateConsultation(Consultation oldConsultation, Consultation newConsultation) {
		ConsultationsModel consultationsModel = new ConsultationsModel();
		consultationsModel.updateConsultation(oldConsultation, newConsultation);
		PatientsModel patientsModel = new PatientsModel();
		Patient patient = patientsModel.getPatientByCNP(newConsultation.getClientCNP());
		setWhatChanged("Consultation update\n" + patient.getName() + " at " + newConsultation.getDate());
		setChanged();
		notifyObservers();
	}

	private List<Consultation> getConsultationsByDoctorAndPatient(Doctor doctor, Patient patient) {
		List<Consultation> allConsultations;
		List<Consultation> consultations = new ArrayList<>();
		ConsultationsModel consultationsModel = new ConsultationsModel();
		allConsultations = consultationsModel.getAllConsultations();
		for (Consultation consultation : allConsultations) {
			if (consultation.getDoctorID() == doctor.getID()) {
				if (consultation.getClientCNP() == patient.getCNP()) {
					consultations.add(consultation);
				}
			}
		}
		return consultations;
	}

	public List<Consultation> getAllConsultations() {
		List<Consultation> consultations;
		ConsultationsModel consultationsModel = new ConsultationsModel();
		consultations = consultationsModel.getAllConsultations();
		return consultations;
	}

	public User loginCheck(User user) {
		User checkedUser = null;
		UsersModel usersModel = new UsersModel();
		List<User> users = usersModel.readUsers();
		for (User userCheck : users) {
			if (userCheck.getUsername().equals(user.getUsername()) && userCheck.getPassword().equals(user.getPassword())) {
				checkedUser = new User();
				checkedUser.setID(userCheck.getID());
				checkedUser.setUsername(userCheck.getUsername());
				checkedUser.setPassword(userCheck.getPassword());
				checkedUser.setType(userCheck.getType());
			}
		}
		return checkedUser;
	}

	public List<Patient> getAllPatients() {
		List<Patient> patients;
		PatientsModel patientsModel = new PatientsModel();
		patients = patientsModel.readPatients();
		return patients;
	}

	public void addNewPatient(Patient patient) {
		PatientsModel patientsModel = new PatientsModel();
		patientsModel.createPatient(patient);
		setWhatChanged("new patient");
		setChanged();
		notifyObservers();
	}

	public List<Consultation> getConsultationsByPatient(Patient patient) {
		List<Consultation> consultations;
		ConsultationsModel consultationsModel = new ConsultationsModel();
		consultations = consultationsModel.getConsultationsByPatient(patient);
		DoctorsModel doctorsModel = new DoctorsModel();
		int i = 0;
		for (Consultation consultation : consultations) {
			Doctor doctor = doctorsModel.readDoctorByID(consultation.getDoctorID());
			consultation.setDoctorName(doctor.getName());
			consultations.set(i, consultation);
			i++;
		}
		return consultations;
	}

	public void deleteUser(User user) {
		UsersModel usersModel = new UsersModel();
		usersModel.deleteUser(user);
		setWhatChanged("");
		setChanged();
		notifyObservers();
	}

	public void createUser(User user) {
		UsersModel usersModel = new UsersModel();
		usersModel.createUser(user);
		setWhatChanged("");
		setChanged();
		notifyObservers();
	}

	public void updateUser(User oldUser, User newUser) {
		UsersModel usersModel = new UsersModel();
		usersModel.updateUser(oldUser, newUser);
		setWhatChanged("");
		setChanged();
		notifyObservers();
	}

	public void deletePatient(Patient patient) {
		PatientsModel patientsModel = new PatientsModel();
		patientsModel.deletePatient(patient);
		setWhatChanged("patient deleted");
		setChanged();
		notifyObservers();
	}
}
