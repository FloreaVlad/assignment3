package org.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.database.DB;
import org.model.Consultation;

public class ConsultationsGateway {

	public List<Consultation> read() {
		List<Consultation> consultations = new ArrayList<>();
		ResultSet res = DB.getDBInstance().executeQuery("select * from consultations;");
		try {
			while (res.next()) {
				Consultation consultation = new Consultation();
				consultation.setClientCNP(res.getLong("clientCNP"));
				consultation.setDoctorID(res.getInt("doctorID"));
				consultation.setDate(new java.util.Date(res.getTimestamp("date").getTime()));
				consultation.setDetails(res.getString("details"));
				consultations.add(consultation);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConsultationsGateway.class.getName()).log(Level.SEVERE, null, ex);
		}
		return consultations;
	}

	public void create(Consultation consultation) {
		StringBuilder query = new StringBuilder();
		query.append("insert into consultations (clientCNP,doctorID,date,details) values ('");
		query.append(consultation.getClientCNP());
		query.append("','");
		query.append(consultation.getDoctorID());
		query.append("','");
		query.append(new java.sql.Timestamp(consultation.getDate().getTime()));
		query.append("','");
		query.append(consultation.getDetails());
		query.append("');");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	public void update(Consultation oldConsultation, Consultation newConsultation) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(oldConsultation.getDate());
		cal2.setTime(oldConsultation.getDate());
		cal1.add(Calendar.MINUTE, -5);
		cal2.add(Calendar.MINUTE, 5);
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		StringBuilder query = new StringBuilder();
		query.append("update consultations set clientCNP='");
		query.append(newConsultation.getClientCNP());
		query.append("',doctorID='");
		query.append(newConsultation.getDoctorID());
		query.append("',date='");
		query.append(new java.sql.Timestamp(newConsultation.getDate().getTime()));
		query.append("',details='");
		query.append(newConsultation.getDetails());
		query.append("' where date>'");
		query.append(new java.sql.Timestamp(date1.getTime()));
		query.append("' and date<'");
		query.append(new java.sql.Timestamp(date2.getTime()));
		query.append("' and clientCNP='");
		query.append(oldConsultation.getClientCNP());
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}

	public void delete(Consultation consultation) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(consultation.getDate());
		cal2.setTime(consultation.getDate());
		cal1.add(Calendar.MINUTE, -5);
		cal2.add(Calendar.MINUTE, 5);
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		StringBuilder query = new StringBuilder();
		query.append("delete from consultations where clientCNP='");
		query.append(consultation.getClientCNP());
		query.append("' and date>'");
		query.append(new java.sql.Timestamp(date1.getTime()));
		query.append("' and date<'");
		query.append(new java.sql.Timestamp(date2.getTime()));
		query.append("';");
		DB.getDBInstance().executeUpdate(query.toString());
	}
}
