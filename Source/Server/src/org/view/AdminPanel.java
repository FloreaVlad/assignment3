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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.model.Patient;
import org.model.PatientsModel;
import org.model.Server;
import org.model.ServerModel;
import org.model.User;
import org.model.UsersModel;

public class AdminPanel extends javax.swing.JFrame implements java.util.Observer {

	private static final long serialVersionUID = 1L;

	private static final String PATIENT_DATE_FORMAT = "dd-MMM-yyyy";
	private UsersModel usersModel;
	private PatientsModel patientsModel;
	private ServerModel serverModel;
	private Server server;
	private boolean isChanged;
	public static int nrOfUpdateRequests;

	/**
	 * Creates new form AdminPanel
	 */
	public AdminPanel() {
		initComponents();
		start();
		addListeners();
	}

	public void setServerModel(ServerModel serverModel) {
		this.serverModel = serverModel;
	}

	public ServerModel getServerModel() {
		return serverModel;
	}

	public void setIsChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public boolean getIsChanged() {
		return isChanged;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

	public void setPatientsModel(PatientsModel patientsModel) {
		this.patientsModel = patientsModel;
	}

	public PatientsModel getPatientsModel() {
		return patientsModel;
	}

	public void setUsersModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}

	public UsersModel getUsersModel() {
		return usersModel;
	}

	private void addListeners() {
		this.addPatientsTableListener(new PatientsTableListener(this));
		this.addPatientsCreateButtonListener(new PatientsCreateListener(this));
		this.addPatientsDeleteButtonListener(new PatientsDeleteListener(this));
		this.addUsersTableListener(new UsersTableListener(this));
		this.addUsersCreateButtonListener(new UsersCreateListener(this));
		this.addUsersDeleteButtonListener(new UsersDeleteListener(this));
		this.addWindowListener(new NewWindowListener(this));
	}

	public void displayUsers(List<User> users) {
		DefaultTableModel usersTable = (DefaultTableModel) jUsersTable.getModel();
		for (int i = jUsersTable.getRowCount() - 1; i >= 0; i--) {
			usersTable.removeRow(i);
		}
		usersTable.setRowCount(0);
		for (User user : users) {
			String username = user.getUsername();
			String password = user.getPassword();
			String type = user.getType();
			usersTable.addRow(new Object[] { username, password, type });
		}
	}

	public void displayPatients(List<Patient> patients) {
		DefaultTableModel patientsTable = (DefaultTableModel) jPatientsTable.getModel();
		for (int i = jPatientsTable.getRowCount() - 1; i >= 0; i--) {
			patientsTable.removeRow(i);
		}
		patientsTable.setRowCount(0);
		for (Patient patient : patients) {
			String name = patient.getName();
			int cardID = patient.getCardID();
			long CNP = patient.getCNP();
			DateFormat df = new SimpleDateFormat(PATIENT_DATE_FORMAT, Locale.ENGLISH);
			String dateOfBirth = df.format(patient.getDateOfBirth());
			String address = patient.getAddress();
			patientsTable.addRow(new Object[] { name, cardID, CNP, dateOfBirth, address });
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jUsersTable = new javax.swing.JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		jPatientsTable = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jTextUsersUsername = new javax.swing.JTextField();
		jTextUsersPassword = new javax.swing.JTextField();
		jComboUsersType = new javax.swing.JComboBox();
		jButtonUsersCreate = new javax.swing.JButton();
		jButtonUsersDelete = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jTextPatientsFullName = new javax.swing.JTextField();
		jTextPatientsCardID = new javax.swing.JTextField();
		jButtonPatientsCreate = new javax.swing.JButton();
		jButtonPatientsDelete = new javax.swing.JButton();
		jLabel9 = new javax.swing.JLabel();
		jTextPatientsCNP = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jComboPatientsDay = new javax.swing.JComboBox();
		jComboPatientsMonth = new javax.swing.JComboBox();
		jComboPatientsYear = new javax.swing.JComboBox();
		jTextPatientsAddress = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jUsersTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "UserName", "Password", "Type" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane1.setViewportView(jUsersTable);

		jPatientsTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Full Name", "Card ID", "CNP", "Date of Birth", "Address" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.String.class, java.lang.Integer.class, java.lang.Long.class, java.lang.String.class,
					java.lang.String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane2.setViewportView(jPatientsTable);

		jLabel1.setText("User Accounts:");

		jLabel2.setText("Patients:");

		jLabel4.setText("Type:");

		jLabel5.setText("UserName:");

		jLabel6.setText("Password:");

		jComboUsersType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "doctor", "secretary" }));

		jButtonUsersCreate.setText("Create");

		jButtonUsersDelete.setText("Delete");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(jLabel6)
																		.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jLabel5).addComponent(jLabel4))
																		.addGap(29, 29, 29)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								jPanel1Layout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(
																												jTextUsersPassword,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												100,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(
																												jTextUsersUsername,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												100,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(jComboUsersType,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69,
																				Short.MAX_VALUE)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jButtonUsersDelete)
																						.addComponent(jButtonUsersCreate))
																		.addGap(32, 32, 32)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(48, 48, 48)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel4)
																		.addComponent(jComboUsersType,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(22, 22, 22)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel5)
																		.addComponent(jTextUsersUsername,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												jPanel1Layout.createSequentialGroup().addGap(22, 22, 22).addComponent(jButtonUsersCreate)
														.addGap(26, 26, 26).addComponent(jButtonUsersDelete)))
						.addGap(18, 18, 18)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel6)
										.addComponent(jTextUsersPassword, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(80, Short.MAX_VALUE)));

		jLabel7.setText("Full Name:");

		jLabel8.setText("Card ID:");

		jButtonPatientsCreate.setText("Create");

		jButtonPatientsDelete.setText("Delete");

		jLabel9.setText("CNP:");

		jLabel10.setText("Date of Birth:");

		jLabel11.setText("Address:");

		jComboPatientsDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31" }));

		jComboPatientsMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" }));

		jComboPatientsYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2005", "2004", "2003", "2002", "2001", "2000",
				"1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985",
				"1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970" }));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jLabel7).addComponent(jLabel8)
																						.addComponent(jLabel9))
																		.addGap(33, 33, 33)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(jTextPatientsFullName)
																						.addComponent(jTextPatientsCardID)
																						.addComponent(jTextPatientsCNP,
																								javax.swing.GroupLayout.DEFAULT_SIZE, 101,
																								Short.MAX_VALUE))
																		.addGap(41, 41, 41)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jButtonPatientsCreate)
																						.addComponent(jButtonPatientsDelete)))
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jLabel10).addComponent(jLabel11))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jComboPatientsDay,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jComboPatientsMonth,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(jTextPatientsAddress))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(jComboPatientsYear,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addGroup(
								jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel2Layout
														.createSequentialGroup()
														.addContainerGap()
														.addGroup(
																jPanel2Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel7)
																		.addComponent(jTextPatientsFullName,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(
																jPanel2Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel8)
																		.addComponent(jTextPatientsCardID,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(18, 18, 18)
														.addGroup(
																jPanel2Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel9)
																		.addComponent(jTextPatientsCNP,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												jPanel2Layout.createSequentialGroup().addGap(24, 24, 24)
														.addComponent(jButtonPatientsCreate)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(jButtonPatientsDelete)))
						.addGap(18, 18, 18)
						.addGroup(
								jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel10)
										.addComponent(jComboPatientsDay, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboPatientsMonth, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboPatientsYear, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(21, 21, 21)
						.addGroup(
								jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel11)
										.addComponent(jTextPatientsAddress, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup().addComponent(jLabel2).addGap(0, 0, Short.MAX_VALUE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(34, 34, 34)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel1)
										.addComponent(jLabel2))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap()));

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
			java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	public void openWindow() {
		this.setVisible(true);
	}

	public void closeWindow() {
		this.setVisible(false);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonPatientsCreate;
	private javax.swing.JButton jButtonPatientsDelete;
	private javax.swing.JButton jButtonUsersCreate;
	private javax.swing.JButton jButtonUsersDelete;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboPatientsDay;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboPatientsMonth;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboPatientsYear;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox jComboUsersType;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JTable jPatientsTable;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextField jTextPatientsAddress;
	private javax.swing.JTextField jTextPatientsCNP;
	private javax.swing.JTextField jTextPatientsCardID;
	private javax.swing.JTextField jTextPatientsFullName;
	private javax.swing.JTextField jTextUsersPassword;
	private javax.swing.JTextField jTextUsersUsername;
	private javax.swing.JTable jUsersTable;

	// End of variables declaration//GEN-END:variables

	public User getSelectedUser() {
		User user = new User();
		int i = jUsersTable.getSelectedRow();
		if (i >= 0) {
			String username = (String) jUsersTable.getValueAt(i, 0);
			String password = (String) jUsersTable.getValueAt(i, 1);
			String tyep = (String) jUsersTable.getValueAt(i, 2);
			user.setUsername(username);
			user.setPassword(password);
			user.setType(tyep);
		}
		return user;
	}

	public Patient getSelectedPatient() {
		Patient patient = new Patient();
		int i = jPatientsTable.getSelectedRow();
		if (i >= 0) {
			String name = (String) jPatientsTable.getValueAt(i, 0);
			int cardID = (int) jPatientsTable.getValueAt(i, 1);
			long CNP = (long) jPatientsTable.getValueAt(i, 2);
			DateFormat df = new SimpleDateFormat(PATIENT_DATE_FORMAT, Locale.ENGLISH);
			Date dateOfBirth = null;
			try {
				dateOfBirth = df.parse((String) jPatientsTable.getValueAt(i, 3));
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			String address = (String) jPatientsTable.getValueAt(i, 4);
			patient.setName(name);
			patient.setCardID(cardID);
			patient.setCNP(CNP);
			patient.setDateOfBirth(dateOfBirth);
			patient.setAddress(address);
		}
		return patient;
	}

	public User getNewUserAccount() {
		String username = jTextUsersUsername.getText();
		String password = jTextUsersPassword.getText();
		String type = (String) jComboUsersType.getSelectedItem();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setType(type);
		return user;
	}

	public Patient getNewPatient() {
		String name = jTextPatientsFullName.getText();
		int cardID = Integer.parseInt(jTextPatientsCardID.getText());
		long CNP = Long.parseLong(jTextPatientsCNP.getText());
		DateFormat df = new SimpleDateFormat(PATIENT_DATE_FORMAT, Locale.ENGLISH);
		String day = (String) jComboPatientsDay.getSelectedItem();
		String month = (String) jComboPatientsMonth.getSelectedItem();
		String year = (String) jComboPatientsYear.getSelectedItem();
		StringBuilder dateString = new StringBuilder();
		dateString.append(day);
		dateString.append("-");
		dateString.append(month);
		dateString.append("-");
		dateString.append(year);
		Date date = null;
		try {
			date = df.parse(dateString.toString());
		} catch (ParseException ex) {
			Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
		String address = jTextPatientsAddress.getText();
		Patient patient = new Patient();
		patient.setName(name);
		patient.setCardID(cardID);
		patient.setCNP(CNP);
		patient.setDateOfBirth(date);
		patient.setAddress(address);
		return patient;
	}

	private void addPatientsTableListener(PatientsTableListener a) {
		jPatientsTable.getModel().addTableModelListener(a);
		jPatientsTable.getSelectionModel().addListSelectionListener(a);
	}

	private void addPatientsCreateButtonListener(PatientsCreateListener a) {
		jButtonPatientsCreate.addActionListener(a);
	}

	private void addPatientsDeleteButtonListener(PatientsDeleteListener a) {
		jButtonPatientsDelete.addActionListener(a);
	}

	private void addUsersTableListener(UsersTableListener a) {
		jUsersTable.getModel().addTableModelListener(a);
		jUsersTable.getSelectionModel().addListSelectionListener(a);
	}

	private void addUsersCreateButtonListener(UsersCreateListener a) {
		jButtonUsersCreate.addActionListener(a);
	}

	private void addUsersDeleteButtonListener(UsersDeleteListener a) {
		jButtonUsersDelete.addActionListener(a);
	}

	private static class PatientsTableListener implements TableModelListener, ListSelectionListener {

		private final AdminPanel adminPanel;
		private Patient patient;

		private PatientsTableListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				Patient newPatient = adminPanel.getSelectedPatient();
				adminPanel.getServerModel().updatePatient(patient, newPatient);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				patient = adminPanel.getSelectedPatient();
			}
		}

	}

	private static class PatientsCreateListener implements ActionListener {

		private final AdminPanel adminPanel;

		private PatientsCreateListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Patient patient = adminPanel.getNewPatient();
			adminPanel.getServerModel().addNewPatient(patient);
		}

	}

	private static class PatientsDeleteListener implements ActionListener {

		private final AdminPanel adminPanel;

		private PatientsDeleteListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Patient patient = adminPanel.getSelectedPatient();
			adminPanel.getServerModel().deletePatient(patient);
		}
	}

	private static class UsersTableListener implements TableModelListener, ListSelectionListener {

		private final AdminPanel adminPanel;
		private User user;

		private UsersTableListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				User newUser = adminPanel.getSelectedUser();
				adminPanel.getServerModel().updateUser(user, newUser);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				user = adminPanel.getSelectedUser();
			}
		}
	}

	private static class UsersCreateListener implements ActionListener {

		private final AdminPanel adminPanel;

		private UsersCreateListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			User user = adminPanel.getNewUserAccount();
			adminPanel.getServerModel().createUser(user);
		}
	}

	private static class UsersDeleteListener implements ActionListener {

		private final AdminPanel adminPanel;

		private UsersDeleteListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			User user = adminPanel.getSelectedUser();
			adminPanel.getServerModel().deleteUser(user);
		}
	}

	private class NewWindowListener extends WindowAdapter {
		private AdminPanel adminPanel;

		private NewWindowListener(AdminPanel adminPanel) {
			this.adminPanel = adminPanel;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			adminPanel.getServer().closeSocket();
		}

	}

	public void displayTables() {
		this.displayUsers(this.usersModel.readUsers());
		this.displayPatients(this.patientsModel.readPatients());
	}

	@Override
	public void update(Observable o, Object arg) {
		this.displayUsers(this.usersModel.readUsers());
		this.displayPatients(this.patientsModel.readPatients());

		isChanged = true;
		nrOfUpdateRequests = Server.nrOfClients;
	}
}
