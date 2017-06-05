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
import org.model.Patient;

public class PatientsGateway {

    public List<Patient> read() {
        List<Patient> patients = new ArrayList<>();
        ResultSet res = DB.getDBInstance().executeQuery("select * from patients;");
        try {
            while (res.next()) {
                Patient patient = new Patient();
                patient.setName(res.getString("Name"));
                patient.setCardID(res.getInt("cardID"));
                patient.setCNP(res.getLong("CNP"));
                patient.setDateOfBirth(new java.util.Date(res.getDate("DateOfBirth").getTime()));
                patient.setAddress(res.getString("Address"));
                patients.add(patient);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientsGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patients;
    }

    public void create(Patient patient) {
        StringBuilder query = new StringBuilder();
        query.append("insert into patients (Name,cardID,CNP,DateOfBirth,Address) values ('");
        query.append(patient.getName());
        query.append("','");
        query.append(patient.getCardID());
        query.append("','");
        query.append(patient.getCNP());
        query.append("','");
        query.append(new java.sql.Date(patient.getDateOfBirth().getTime()));
        query.append("','");
        query.append(patient.getAddress());
        query.append("');");
        DB.getDBInstance().executeUpdate(query.toString());
    }

    public void update(Patient oldPatient, Patient newPatient) {
        StringBuilder query = new StringBuilder();
        query.append("update patients set Name='");
        query.append(newPatient.getName());
        query.append("',cardID='");
        query.append(newPatient.getCardID());
        query.append("',CNP='");
        query.append(newPatient.getCNP());
        query.append("',DateOfBirth='");
        query.append(new java.sql.Date(newPatient.getDateOfBirth().getTime()));
        query.append("',Address='");
        query.append(newPatient.getAddress());
        query.append("' where CNP='");
        query.append(oldPatient.getCNP());
        query.append("';");
        DB.getDBInstance().executeUpdate(query.toString());
    }

    public void delete(Patient patient) {
        StringBuilder query = new StringBuilder();
        query.append("delete from patients where CNP='");
        query.append(patient.getCNP());
        query.append("';");
        DB.getDBInstance().executeUpdate(query.toString());
    }
}
