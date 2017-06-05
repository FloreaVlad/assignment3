/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.DB;
import org.model.Doctor;

public class DoctorsGateway {

	public List<Doctor> read() {
		List<Doctor> doctors = new ArrayList<>();
		ResultSet res = DB.getDBInstance().executeQuery("select * from doctors;");
		try {
			while (res.next()) {
				Doctor doctor = new Doctor();
				doctor.setID(res.getInt("ID"));
				doctor.setName(res.getString("Name"));
				doctors.add(doctor);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DoctorsGateway.class.getName()).log(Level.SEVERE, null, ex);
		}
		return doctors;
	}

	public Doctor readByID(int doctorID) {
		Doctor doctor = new Doctor();
		StringBuilder query = new StringBuilder();
		query.append("select * from doctors where ID='");
		query.append(doctorID);
		query.append("';");
		ResultSet res = DB.getDBInstance().executeQuery(query.toString());
		doctor.setID(doctorID);
		try {
			res.first();
			doctor.setName(res.getString("Name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctor;
	}
}
