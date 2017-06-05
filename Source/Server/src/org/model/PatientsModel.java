/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

import java.util.List;
import org.gateway.PatientsGateway;

public class PatientsModel {

	public List<Patient> readPatients() {
		PatientsGateway patientsGateway = new PatientsGateway();
		return patientsGateway.read();
	}

	public Patient getPatientByCNP(long CNP) {
		PatientsGateway patientsGateway = new PatientsGateway();
		List<Patient> patients = patientsGateway.read();
		for (Patient patient : patients) {
			if (patient.getCNP() == CNP)
				return patient;
		}
		return null;
	}

	public void createPatient(Patient patient) {
		PatientsGateway patientsGateway = new PatientsGateway();
		patientsGateway.create(patient);
	}

	public void updatePatient(Patient patient, Patient newPatient) {
		PatientsGateway patientsGateway = new PatientsGateway();
		patientsGateway.update(patient, newPatient);
	}

	public void deletePatient(Patient patient) {
		PatientsGateway patientsGateway = new PatientsGateway();
		patientsGateway.delete(patient);
	}

}
