/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.model.Consultation;
import org.model.Doctor;
import org.model.Patient;
import org.model.User;

public class Command implements Serializable {

	private static final long serialVersionUID = 101L;

	private CommandType command;
	private List<Patient> patients;
	private List<User> users;
	private List<Doctor> doctors;
	private List<Consultation> consultations;

	public Command() {
		this.patients = new ArrayList<>();
		this.users = new ArrayList<>();
		this.doctors = new ArrayList<>();
		this.consultations = new ArrayList<>();
	}

	public CommandType getCommand() {
		return command;
	}

	public void setCommand(CommandType command) {
		this.command = command;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(List<Consultation> consultations) {
		this.consultations = consultations;
	}

	public void addPatient(Patient patient) {
		this.patients.add(patient);
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void addDoctor(Doctor doctor) {
		this.doctors.add(doctor);
	}

	public void addConsultation(Consultation consultation) {
		this.consultations.add(consultation);
	}

}
