package org.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

import org.model.*;

public class DoctorPanel extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private static final String PATIENT_DATE_FORMAT = "dd-MMM-yyyy";
	private static final String CONSULTATION_TIME_FORMAT = "HH:mm";
	private Doctor doctor;
	private Consultation selectedConsultation;

	public DoctorPanel(Doctor doctor) {
		this.doctor = doctor;
		initComponents();
		start();
		addListeners();
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setSelectedConsultation(Consultation consultation) {
		this.selectedConsultation = consultation;
	}

	public Consultation getConsultation() {
		return selectedConsultation;
	}

	public void displayPatients(List<Patient> patients) {
		DefaultTableModel patientsTable = (DefaultTableModel) jTablePatients.getModel();
		for (int i = jTablePatients.getRowCount() - 1; i >= 0; i--) {
			patientsTable.removeRow(i);
		}
		patientsTable.setRowCount(0);
		for (Patient patient : patients) {
			String name = patient.getName();
			long CNP = patient.getCNP();
			patientsTable.addRow(new Object[] { name, CNP });
		}
	}

	public void displayConsultations(List<Consultation> consultations) {
		DefaultTableModel consultationsTable = (DefaultTableModel) jTableConsultations.getModel();
		for (int i = jTableConsultations.getRowCount() - 1; i >= 0; i--) {
			consultationsTable.removeRow(i);
		}
		consultationsTable.setRowCount(0);
		for (Consultation consultation : consultations) {
			DateFormat df = new SimpleDateFormat(PATIENT_DATE_FORMAT, Locale.ENGLISH);
			String date = df.format(consultation.getDate());
			DateFormat tf = new SimpleDateFormat(CONSULTATION_TIME_FORMAT, Locale.ENGLISH);
			String time = tf.format(consultation.getDate());
			String details = consultation.getDetails();
			consultationsTable.addRow(new Object[] { date, time, details });
		}
	}

	public void eraseConsultationsTable() {
		DefaultTableModel consultationsTable = (DefaultTableModel) jTableConsultations.getModel();
		for (int i = jTableConsultations.getRowCount() - 1; i >= 0; i--) {
			consultationsTable.removeRow(i);
		}
		consultationsTable.setRowCount(0);
	}

	private void addListeners() {
		this.addPatientSelectionListener(new PatientSelectionListener(this));
		this.addConsultationSelectionListener(new ConsultationSelectionListener(this));
		this.addAddConsultationDetailsListener(new AddConsultationDetailsListener(this));
		this.addWindowListener(new NewWindowListener(this));
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTablePatients = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTableConsultations = new javax.swing.JTable();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		// jTextSearchPatient = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jScrollPane = new javax.swing.JScrollPane();
		jTextConsultationDetails = new javax.swing.JTextArea();
		jButtonAddConsultationDetails = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jTablePatients.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Full Name", "CNP" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.Long.class };
			boolean[] canEdit = new boolean[] { false, false };

			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane1.setViewportView(jTablePatients);

		jLabel1.setText("Patients:");

		jTableConsultations.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Date", "Time", "Details" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false };

			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane2.setViewportView(jTableConsultations);

		jLabel2.setText("Consultations:");

		jLabel3.setText("                                  ");

		jLabel4.setText("Consultation details:");

		jTextConsultationDetails.setColumns(20);
		jTextConsultationDetails.setRows(5);
		jScrollPane.setViewportView(jTextConsultationDetails);

		jButtonAddConsultationDetails.setText("Add Details");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel1)
												.addGroup(
														layout.createSequentialGroup().addComponent(jLabel3)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jLabel2)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(jLabel4)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										jScrollPane,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(jButtonAddConsultationDetails)))
																.addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(23, 23, 23)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
												.addComponent(jLabel2))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																								.addComponent(jLabel3))
																				.addComponent(jLabel4)
																				.addComponent(jScrollPane,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														layout.createSequentialGroup().addGap(35, 35, 35)
																.addComponent(jButtonAddConsultationDetails)))
								.addContainerGap(50, Short.MAX_VALUE)));

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
			java.util.logging.Logger.getLogger(DoctorPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	public void openWindow() {
		this.setVisible(true);
	}

	public void closeWindow() {
		this.setVisible(false);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonAddConsultationDetails;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JScrollPane jScrollPane;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTableConsultations;
	private javax.swing.JTable jTablePatients;
	private javax.swing.JTextArea jTextConsultationDetails;

	// End of variables declaration//GEN-END:variables

	public void displayTables() {
		List<Patient> patients;
		Command command;

		command = new Command();
		command.setCommand(CommandType.GET_PATIENTS_BY_DOCTOR);
		command.addDoctor(doctor);
		patients = Client.getInstance().executeCommand(command).getPatients();

		this.displayPatients(patients);
	}

	private Patient getSelectedPatient() {
		Patient patient = new Patient();
		int i = jTablePatients.getSelectedRow();
		patient.setName((String) jTablePatients.getValueAt(i, 0));
		patient.setCNP((long) jTablePatients.getValueAt(i, 1));
		return patient;
	}

	@SuppressWarnings("deprecation")
	private Consultation getSelectedConsultation() {
		Consultation consultation = new Consultation();
		int i = jTableConsultations.getSelectedRow();
		Date date = null;
		Date time;
		try {
			DateFormat df = new SimpleDateFormat(PATIENT_DATE_FORMAT, Locale.ENGLISH);
			date = df.parse((String) jTableConsultations.getValueAt(i, 0));
			df = new SimpleDateFormat(CONSULTATION_TIME_FORMAT, Locale.ENGLISH);
			time = df.parse((String) jTableConsultations.getValueAt(i, 1));
			int hours = time.getHours();
			int minutes = time.getMinutes();
			date.setHours(hours);
			date.setMinutes(minutes);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		consultation.setDate(date);
		consultation.setDetails((String) jTableConsultations.getValueAt(i, 2));
		return consultation;
	}

	private void addPatientSelectionListener(PatientSelectionListener a) {
		jTablePatients.getSelectionModel().addListSelectionListener(a);
	}

	private class PatientSelectionListener implements ListSelectionListener {
		private DoctorPanel doctorPanel;

		private PatientSelectionListener(DoctorPanel doctorPanel) {
			this.doctorPanel = doctorPanel;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				Patient patient = doctorPanel.getSelectedPatient();
				List<Consultation> consultations;
				Command command = new Command();
				command.addDoctor(doctorPanel.getDoctor());
				command.addPatient(patient);
				command.setCommand(CommandType.GET_CONSULTATIONS_BY_DOCTOR_AND_PATIENT);
				consultations = Client.getInstance().executeCommand(command).getConsultations();
				doctorPanel.displayConsultations(consultations);
			}
		}

	}

	private void addConsultationSelectionListener(ConsultationSelectionListener a) {
		jTableConsultations.getSelectionModel().addListSelectionListener(a);
	}

	private class ConsultationSelectionListener implements ListSelectionListener {
		private DoctorPanel doctorPanel;

		private ConsultationSelectionListener(DoctorPanel doctorPanel) {
			this.doctorPanel = doctorPanel;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				Consultation consultation = doctorPanel.getSelectedConsultation();
				Patient patient = doctorPanel.getSelectedPatient();
				Doctor doctor = doctorPanel.getDoctor();
				doctorPanel.setDetailsText(consultation.getDetails());
				consultation.setDoctorID(doctor.getID());
				consultation.setClientCNP(patient.getCNP());
				doctorPanel.setSelectedConsultation(consultation);
			}
		}
	}

	private void addAddConsultationDetailsListener(AddConsultationDetailsListener a) {
		jButtonAddConsultationDetails.addActionListener(a);
	}

	private class AddConsultationDetailsListener implements ActionListener {
		private DoctorPanel doctorPanel;

		private AddConsultationDetailsListener(DoctorPanel doctorPanel) {
			this.doctorPanel = doctorPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Consultation oldConsultation = doctorPanel.getConsultation();
			Consultation newConsultation = new Consultation();
			newConsultation.setClientCNP(oldConsultation.getClientCNP());
			newConsultation.setDate(oldConsultation.getDate());
			newConsultation.setDoctorID(oldConsultation.getDoctorID());
			newConsultation.setDetails(jTextConsultationDetails.getText());
			Command command = new Command();
			command.setCommand(CommandType.UPDATE_CONSULTATION);
			command.addConsultation(oldConsultation);
			command.addConsultation(newConsultation);
			@SuppressWarnings("unused")
			Response res = Client.getInstance().executeCommand(command);
			doctorPanel.displayTables();
		}

	}

	private void setDetailsText(String details) {
		jTextConsultationDetails.setText(details);
	}

	private class NewWindowListener extends WindowAdapter {
		private DoctorPanel doctorPanel;

		private NewWindowListener(DoctorPanel doctorPanel) {
			this.doctorPanel = doctorPanel;
		}

		@Override
		public void windowOpened(WindowEvent e) {
			DoctorPanelUpdate updateRoutine = new DoctorPanelUpdate(doctorPanel);
			Timer updateTimer = new Timer();
			updateTimer.schedule(updateRoutine, 0, 5000);
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
