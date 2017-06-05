/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gateway.ConsultationsGateway;
import org.gateway.DoctorsGateway;

public class DoctorsModel {

	public List<Doctor> availableDoctors(Date date) {
		DoctorsGateway doctorsGateway = new DoctorsGateway();
		ConsultationsGateway consultationsGateway = new ConsultationsGateway();
		List<Doctor> doctors = doctorsGateway.read();
		List<Doctor> availableDoctors = new ArrayList<>();
		List<Consultation> consultations = consultationsGateway.read();
		int year, month, day, hour, minutes;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DATE);
		hour = cal.get(Calendar.HOUR);
		minutes = cal.get(Calendar.MINUTE);
		for (Doctor doctor : doctors) {
			boolean available = true;
			for (Consultation consultation : consultations) {
				if (consultation.getDoctorID() == doctor.getID()) {
					int year2, month2, day2, hour2, minutes2;
					cal.setTime(consultation.getDate());
					year2 = cal.get(Calendar.YEAR);
					month2 = cal.get(Calendar.MONTH);
					day2 = cal.get(Calendar.DATE);
					hour2 = cal.get(Calendar.HOUR);
					minutes2 = cal.get(Calendar.MINUTE);
					if (year == year2 && month == month2 && day == day2 && hour == hour2 && minutes == minutes2) {
						available = false;
					}
				}
			}
			if (available) {
				availableDoctors.add(doctor);
			}
		}
		return availableDoctors;
	}

	public Doctor readDoctorByID(int doctorID) {
		DoctorsGateway doctorsGateway = new DoctorsGateway();
		return doctorsGateway.readByID(doctorID);
	}

	public Doctor readDoctorByName(String name) {
		DoctorsGateway doctorsGateway = new DoctorsGateway();
		List<Doctor> doctors = doctorsGateway.read();
		for (Doctor doctor : doctors) {
			if (doctor.getName().equalsIgnoreCase(name))
				return doctor;
		}
		return null;
	}
}
