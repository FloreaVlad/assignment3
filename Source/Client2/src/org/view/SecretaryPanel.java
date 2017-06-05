/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

import org.model.*;

public class SecretaryPanel extends javax.swing.JFrame {

	private static final long serialVersionUID = 3L;
	private static final String DATE_FORMAT = "dd-MMM-yyyy";
	private static final String TIME_FORMAT = "HH:mm";

	private javax.swing.JButton jButtonAddPatient;
	private javax.swing.JButton jButtonConsultationCreate;
	private javax.swing.JButton jButtonConsultationDelete;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboConsultationDay;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboConsultationDoctor;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboConsultationHour;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboConsultationMinutes;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboConsultationMonth;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboDay;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboMonth;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboYear;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JTable jTableConsultations;
	private javax.swing.JTable jTablePatients;
	private javax.swing.JTextField jTextAddress;
	private javax.swing.JTextField jTextCNP;
	private javax.swing.JTextField jTextCardID;
	private javax.swing.JTextField jTextFullName;

	public SecretaryPanel() {
		initComponents();
		start();
		addListeners();
		getAvailableDoctors();
	}

	public void displayPatients(List<Patient> patients) {
		DefaultTableModel patientsTable = (DefaultTableModel) jTablePatients.getModel();
		for (int i = jTablePatients.getRowCount() - 1; i >= 0; i--) {
			patientsTable.removeRow(i);
		}
		patientsTable.setRowCount(0);
		for (Patient patient : patients) {
			String name = patient.getName();
			int cardID = patient.getCardID();
			long CNP = patient.getCNP();
			DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
			String dateOfBirth = df.format(patient.getDateOfBirth());
			String address = patient.getAddress();
			patientsTable.addRow(new Object[] { name, cardID, CNP, dateOfBirth, address });
		}
	}

	public void displayConsultations(List<Consultation> consultations) {
		DefaultTableModel consultationsTable = (DefaultTableModel) jTableConsultations.getModel();
		for (int i = jTableConsultations.getRowCount() - 1; i >= 0; i--) {
			consultationsTable.removeRow(i);
		}
		consultationsTable.setRowCount(0);
		for (Consultation consultation : consultations) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			DateFormat tf = new SimpleDateFormat(TIME_FORMAT);
			String date = df.format(consultation.getDate());
			String time = tf.format(consultation.getDate());
			String doctorName = consultation.getDoctorName();
			consultationsTable.addRow(new Object[] { date, time, doctorName });
		}
	}

	public void displayTables() {
		List<Patient> patients;
		Command command = new Command();
		command.setCommand(CommandType.ALL_PATIENTS);
		patients = Client.getInstance().executeCommand(command).getPatients();
		this.displayPatients(patients);
	}

	private void addListeners() {
		this.addAddPatientListener(new AddPatientListener(this));
		this.addPatientsTableListener(new PatientsTableListener(this));
		this.addComboConsultationListener(new ComboConsultationListener(this));
		this.addCreateConsultationListener(new CreateConsultationListener(this));
		this.addConsultationsTableListener(new ConsultationsTableListener(this));
		this.addDeleteConsultationListener(new DeleteConsultationListener(this));
		this.addWindowListener(new NewWindowListener(this));
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTablePatients = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jTextFullName = new javax.swing.JTextField();
		jTextCardID = new javax.swing.JTextField();
		jTextCNP = new javax.swing.JTextField();
		jComboDay = new javax.swing.JComboBox();
		jComboMonth = new javax.swing.JComboBox();
		jComboYear = new javax.swing.JComboBox();
		jTextAddress = new javax.swing.JTextField();
		jButtonAddPatient = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTableConsultations = new javax.swing.JTable();
		jLabel7 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel8 = new javax.swing.JLabel();
		jComboConsultationDay = new javax.swing.JComboBox();
		jComboConsultationMonth = new javax.swing.JComboBox();
		jLabel9 = new javax.swing.JLabel();
		jComboConsultationHour = new javax.swing.JComboBox();
		jComboConsultationMinutes = new javax.swing.JComboBox();
		jLabel10 = new javax.swing.JLabel();
		jComboConsultationDoctor = new javax.swing.JComboBox();
		jButtonConsultationCreate = new javax.swing.JButton();
		jButtonConsultationDelete = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jTablePatients.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Full Name", "card ID", "CNP", "Date of Birth", "Address" }) {
			Class[] types = new Class[] { java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class,
					java.lang.String.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane1.setViewportView(jTablePatients);

		jLabel1.setText("Patients:");

		jLabel2.setText("Full Name:");

		jLabel3.setText("card ID:");

		jLabel4.setText("CNP:");

		jLabel5.setText("Date of Birth");

		jLabel6.setText("Address:");

		jComboDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31" }));

		jComboMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" }));

		jComboYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2005", "2004", "2003", "2002", "2001", "2000", "1999",
				"1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984",
				"1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970" }));

		jButtonAddPatient.setText("Add Patient");

		jTableConsultations.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Date", "Time", "Doctor" }) {
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane2.setViewportView(jTableConsultations);

		jLabel7.setText("Consultations:");

		jLabel8.setText("Date:");

		jComboConsultationDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31" }));

		jComboConsultationMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));

		jLabel9.setText("Time:");

		jComboConsultationHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09", "10", "11", "12", "13", "15", "16", "17",
				"18" }));

		jComboConsultationMinutes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "30" }));

		jLabel10.setText("Doctor:");

		jButtonConsultationCreate.setText("Create");

		jButtonConsultationDelete.setText("Delete");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButtonAddPatient).addGap(95, 95, 95))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator1)
												.addComponent(jScrollPane1)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jLabel1)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel2)
																												.addComponent(
																														jTextFullName,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														138,
																														javax.swing.GroupLayout.PREFERRED_SIZE))
																								.addGap(67, 67, 67)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel3)
																												.addComponent(
																														jTextCardID,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														115,
																														javax.swing.GroupLayout.PREFERRED_SIZE))
																								.addGap(81, 81, 81)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel4)
																																.addGap(174,
																																		174,
																																		174)
																																.addComponent(
																																		jLabel5))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jTextCNP,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		109,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addGap(63,
																																		63,
																																		63)
																																.addComponent(
																																		jComboDay,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		jComboMonth,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		jComboYear,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGap(75, 75, 75)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel6)
																												.addComponent(
																														jTextAddress,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														158,
																														javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addComponent(jLabel7)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel8)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jComboConsultationDay,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		jComboConsultationMonth,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGap(48, 48, 48)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel9)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jComboConsultationHour,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		jComboConsultationMinutes,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGap(47, 47, 47)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(jLabel10)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jComboConsultationDoctor,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addGap(102,
																																		102,
																																		102)
																																.addComponent(
																																		jButtonConsultationCreate)
																																.addGap(30,
																																		30,
																																		30)
																																.addComponent(
																																		jButtonConsultationDelete)))))
																.addGap(0, 44, Short.MAX_VALUE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(14, 14, 14)
						.addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
										.addComponent(jLabel3).addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jTextFullName, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jTextCardID, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jTextCNP, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboDay, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboMonth, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboYear, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jTextAddress, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jButtonAddPatient)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel7)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel8)
										.addComponent(jLabel9).addComponent(jLabel10))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jComboConsultationDay, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboConsultationMonth, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboConsultationHour, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboConsultationMinutes, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboConsultationDoctor, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonConsultationCreate).addComponent(jButtonConsultationDelete))
						.addContainerGap(54, Short.MAX_VALUE)));

		pack();
	}

	private void start() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("System".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SecretaryPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	public void openWindow() {
		this.setVisible(true);
	}

	public void closeWindow() {
		this.setVisible(false);
	}

	// End of variables declaration//GEN-END:variables

	private String getPatientNameInput() {
		return jTextFullName.getText();
	}

	private String getPatientAddressInput() {
		return jTextAddress.getText();
	}

	private long getPatientCNPInput() {
		return Long.parseLong(jTextCNP.getText());
	}

	private int getPatientCardIDInput() {
		return Integer.parseInt(jTextCardID.getText());
	}

	private Date getPatientDateOfBirthInput() {
		Date date;
		int month = 0;
		switch ((String) jComboMonth.getSelectedItem()) {
		case "Jan":
			month = 0;
			break;
		case "Feb":
			month = 1;
			break;
		case "Mar":
			month = 2;
			break;
		case "Apr":
			month = 3;
			break;
		case "May":
			month = 4;
			break;
		case "Jun":
			month = 5;
			break;
		case "Jul":
			month = 6;
			break;
		case "Aug":
			month = 7;
			break;
		case "Sep":
			month = 8;
			break;
		case "Oct":
			month = 9;
			break;
		case "Nov":
			month = 10;
			break;
		case "Dec":
			month = 11;
			break;
		}
		int day = Integer.parseInt((String) jComboDay.getSelectedItem());
		int year = Integer.parseInt((String) jComboYear.getSelectedItem());
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		date = cal.getTime();
		return date;
	}

	private void addAddPatientListener(AddPatientListener a) {
		jButtonAddPatient.addActionListener(a);
	}

	private Patient getSelectedPatient() {
		Patient patient = new Patient();
		int i = jTablePatients.getSelectedRow();
		if (i >= 0) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
			String name = (String) jTablePatients.getValueAt(i, 0);
			int cardID = (int) jTablePatients.getValueAt(i, 1);
			long CNP = (long) jTablePatients.getValueAt(i, 2);
			Date dob = null;
			try {
				dob = df.parse((String) jTablePatients.getValueAt(i, 3));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String address = (String) jTablePatients.getValueAt(i, 4);
			patient.setName(name);
			patient.setCardID(cardID);
			patient.setCNP(CNP);
			patient.setDateOfBirth(dob);
			patient.setAddress(address);
		}
		return patient;
	}

	private void addPatientsTableListener(PatientsTableListener a) {
		jTablePatients.getModel().addTableModelListener(a);
		jTablePatients.getSelectionModel().addListSelectionListener(a);
	}

	private Date getConsultationDate() {
		int day = Integer.parseInt((String) jComboConsultationDay.getSelectedItem());
		int month = 0;
		int hours = Integer.parseInt((String) jComboConsultationHour.getSelectedItem());
		int minutes = Integer.parseInt((String) jComboConsultationMinutes.getSelectedItem());
		switch ((String) jComboConsultationMonth.getSelectedItem()) {
		case "Jan":
			month = 0;
			break;
		case "Feb":
			month = 1;
			break;
		case "Mar":
			month = 2;
			break;
		case "Apr":
			month = 3;
			break;
		case "May":
			month = 4;
			break;
		case "Jun":
			month = 5;
			break;
		case "Jul":
			month = 6;
			break;
		case "Aug":
			month = 7;
			break;
		case "Sep":
			month = 8;
			break;
		case "Oct":
			month = 9;
			break;
		case "Nov":
			month = 10;
			break;
		case "Dec":
			month = 11;
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.getInstance().get(Calendar.YEAR), month, day, hours, minutes, 0);
		return cal.getTime();
	}

	private Doctor getSelectedDoctor() {
		Doctor doctor = new Doctor();
		doctor.setName((String) jComboConsultationDoctor.getSelectedItem());
		Command command = new Command();
		command.setCommand(CommandType.GET_DOCTOR_BY_NAME);
		command.addDoctor(doctor);
		doctor.setID(Client.getInstance().executeCommand(command).getDoctors().get(0).getID());
		return doctor;
	}

	private Consultation getNewConsultation() {
		Doctor doctor = getSelectedDoctor();
		Patient patient = getSelectedPatient();
		Consultation consultation = new Consultation();
		consultation.setDate(getConsultationDate());
		consultation.setDoctorName(doctor.getName());
		consultation.setDoctorID(doctor.getID());
		consultation.setClientCNP(patient.getCNP());
		return consultation;
	}

	@SuppressWarnings("unchecked")
	private void getAvailableDoctors() {
		Date consultationDate = getConsultationDate();
		Command command = new Command();
		command.setCommand(CommandType.GET_AVAILABLE_DOCTORS);
		Consultation cons = new Consultation();
		cons.setDate(consultationDate);
		command.addConsultation(cons);
		List<Doctor> doctors = Client.getInstance().executeCommand(command).getDoctors();
		jComboConsultationDoctor.removeAllItems();
		for (Doctor doctor : doctors) {
			jComboConsultationDoctor.addItem(doctor.getName());
		}
	}

	private void addComboConsultationListener(ComboConsultationListener a) {
		jComboConsultationDay.addActionListener(a);
		jComboConsultationMonth.addActionListener(a);
		jComboConsultationMinutes.addActionListener(a);
		jComboConsultationHour.addActionListener(a);
	}

	private void addCreateConsultationListener(CreateConsultationListener a) {
		jButtonConsultationCreate.addActionListener(a);
	}

	private void addConsultationsTableListener(ConsultationsTableListener a) {
		jTableConsultations.getSelectionModel().addListSelectionListener(a);
		jTableConsultations.getModel().addTableModelListener(a);
	}

	private Consultation getSelectedConsultation() {
		Consultation consultation = new Consultation();
		int i = jTableConsultations.getSelectedRow();
		if (i >= 0) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			DateFormat tf = new SimpleDateFormat(TIME_FORMAT);
			Date date = null;
			Date time = null;
			try {
				date = df.parse((String) jTableConsultations.getValueAt(i, 0));
				time = tf.parse((String) jTableConsultations.getValueAt(i, 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Doctor doctor = new Doctor();
			doctor.setName((String) jTableConsultations.getValueAt(i, 2));
			Command command = new Command();
			command.setCommand(CommandType.GET_DOCTOR_BY_NAME);
			command.addDoctor(doctor);
			doctor = Client.getInstance().executeCommand(command).getDoctors().get(0);
			int day, month, year, hour, minutes;
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			day = cal.get(Calendar.DATE);
			month = cal.get(Calendar.MONTH);
			year = cal.get(Calendar.YEAR);
			cal.setTime(time);
			hour = cal.get(Calendar.HOUR_OF_DAY);
			minutes = cal.get(Calendar.MINUTE);
			cal.set(year, month, day, hour, minutes);
			Date newDate = cal.getTime();
			consultation.setDate(newDate);
			consultation.setDoctorID(doctor.getID());
			consultation.setDoctorName(doctor.getName());
			Patient patient = getSelectedPatient();
			consultation.setClientCNP(patient.getCNP());
		}
		return consultation;
	}

	private void addDeleteConsultationListener(DeleteConsultationListener a) {
		jButtonConsultationDelete.addActionListener(a);
	}

	private class AddPatientListener implements ActionListener {
		private SecretaryPanel secretaryPanel;

		private AddPatientListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Command command = new Command();
			command.setCommand(CommandType.ADD_NEW_PATIENT);
			Patient patient = new Patient();
			patient.setName(secretaryPanel.getPatientNameInput());
			patient.setCardID(secretaryPanel.getPatientCardIDInput());
			patient.setCNP(secretaryPanel.getPatientCNPInput());
			patient.setDateOfBirth(secretaryPanel.getPatientDateOfBirthInput());
			patient.setAddress(secretaryPanel.getPatientAddressInput());
			command.addPatient(patient);
			@SuppressWarnings("unused")
			Response res = Client.getInstance().executeCommand(command);
			secretaryPanel.displayTables();
		}
	}

	private class PatientsTableListener implements TableModelListener, ListSelectionListener {
		private SecretaryPanel secretaryPanel;
		private Patient patient;

		private PatientsTableListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				patient = secretaryPanel.getSelectedPatient();
				// get consultations
				Command command = new Command();
				command.setCommand(CommandType.GET_CONSULTATION_BY_PATIENT);
				command.addPatient(patient);
				secretaryPanel.displayConsultations(Client.getInstance().executeCommand(command).getConsultations());
			}
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				Patient newPatient;
				newPatient = secretaryPanel.getSelectedPatient();
				Command command = new Command();
				command.setCommand(CommandType.UPDATE_PATIENT);
				command.addPatient(patient);
				command.addPatient(newPatient);
				@SuppressWarnings("unused")
				Response res = Client.getInstance().executeCommand(command);
			}
		}

	}

	private class ComboConsultationListener implements ActionListener {
		private SecretaryPanel secretaryPanel;

		private ComboConsultationListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			secretaryPanel.getAvailableDoctors();
		}
	}

	private class CreateConsultationListener implements ActionListener {
		private SecretaryPanel secretaryPanel;

		private CreateConsultationListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Consultation consultation = secretaryPanel.getNewConsultation();
			Command command = new Command();
			command.setCommand(CommandType.CREATE_CONSULTATION);
			command.addConsultation(consultation);
			@SuppressWarnings("unused")
			Response res = Client.getInstance().executeCommand(command);
			secretaryPanel.displayTables();
		}

	}

	private class ConsultationsTableListener implements ListSelectionListener, TableModelListener {
		private SecretaryPanel secretaryPanel;
		private Consultation consultation;

		private ConsultationsTableListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				Consultation newConsultation = secretaryPanel.getSelectedConsultation();
				Command command = new Command();
				command.setCommand(CommandType.UPDATE_CONSULTATION);
				command.addConsultation(consultation);
				command.addConsultation(newConsultation);
				@SuppressWarnings("unused")
				Response res = Client.getInstance().executeCommand(command);
				secretaryPanel.displayTables();
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				this.consultation = secretaryPanel.getSelectedConsultation();
			}
		}
	}

	private class DeleteConsultationListener implements ActionListener {
		private SecretaryPanel secretaryPanel;

		private DeleteConsultationListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Consultation consultation = secretaryPanel.getSelectedConsultation();
			Command command = new Command();
			command.setCommand(CommandType.DELETE_CONSULTATION);
			command.addConsultation(consultation);
			@SuppressWarnings("unused")
			Response res = Client.getInstance().executeCommand(command);
			secretaryPanel.displayTables();
		}

	}

	private class NewWindowListener extends WindowAdapter {
		private SecretaryPanel secretaryPanel;

		private NewWindowListener(SecretaryPanel secretaryPanel) {
			this.secretaryPanel = secretaryPanel;
		}

		@Override
		public void windowOpened(WindowEvent e) {
			SecretaryPanelUpdate secretaryUpdate = new SecretaryPanelUpdate(secretaryPanel);
			Timer updateCheck = new Timer();
			updateCheck.schedule(secretaryUpdate, 0, 5000);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			Command command = new Command();
			command.setCommand(CommandType.FINISH);
			Client.getInstance().executeCommand(command);
			try {
				Client.getInstance().closeConnection();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
