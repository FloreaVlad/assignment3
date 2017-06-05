
package org.model;

import java.io.Serializable;
import java.util.Date;

public class Consultation implements Serializable {

	private static final long serialVersionUID = 103L;

	private int doctorID;
	private String doctorName;
	private long clientCNP;
	private Date date;
	private String details;

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public long getClientCNP() {
		return clientCNP;
	}

	public void setClientCNP(long clientCNP) {
		this.clientCNP = clientCNP;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

}
