package org.model;

import java.util.ArrayList;
import java.util.List;

import org.gateway.ConsultationsGateway;
import org.gateway.DoctorsGateway;

public class ConsultationsModel {

	public List<Consultation> getAllConsultations() {
		List<Consultation> consultations;
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		DoctorsGateway doctorsGateway = new DoctorsGateway();
		consultations = consultationsGateway.read();
		int i = 0;
		for (Consultation consultation : consultations) {
			int doctorID = consultation.getDoctorID();
			Doctor doctor = doctorsGateway.readByID(doctorID);
			consultation.setDoctorName(doctor.getName());
			consultations.set(i, consultation);
			i++;
		}
		return consultations;
	}

	public List<Consultation> getConsultationsByPatient(Patient patient) {
		List<Consultation> allConsultations;
		List<Consultation> consultations = new ArrayList<>();
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		allConsultations = consultationsGateway.read();
		for (Consultation consultation : allConsultations) {
			if (consultation.getClientCNP() == patient.getCNP()) {
				consultations.add(consultation);
			}
		}
		return consultations;
	}

	public void createConsultation(Consultation consultation) {
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		consultationsGateway.create(consultation);
	}

	public void updateConsultation(Consultation oldConsultation, Consultation newConsultation) {
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		consultationsGateway.update(oldConsultation, newConsultation);
	}

	public void deleteConsultation(Consultation consultation) {
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		consultationsGateway.delete(consultation);
	}

}
