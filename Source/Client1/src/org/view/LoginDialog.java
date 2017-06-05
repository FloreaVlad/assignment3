/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

import org.model.*;

public class LoginDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 2L;

	public LoginDialog() {
		initComponents();
		start();
		addListeners();
	}


	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jTextUsername = new javax.swing.JTextField();
		jTextPassword = new javax.swing.JTextField();
		jOKButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		jLabel1.setText("Login:");

		jLabel2.setText("Username:");

		jLabel3.setText("Password:");

		jOKButton.setText("OK");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(95, 95, 95).addComponent(jLabel1))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(32, 32, 32)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(jOKButton)
																				.addGroup(
																						layout.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																								.addGroup(
																										layout.createSequentialGroup()
																												.addComponent(jLabel3)
																												.addGap(21, 21, 21)
																												.addComponent(jTextPassword))
																								.addGroup(
																										layout.createSequentialGroup()
																												.addComponent(jLabel2)
																												.addPreferredGap(
																														javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																												.addComponent(
																														jTextUsername,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														111,
																														javax.swing.GroupLayout.PREFERRED_SIZE))))))
								.addContainerGap(44, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(31, 31, 31)
						.addComponent(jLabel1)
						.addGap(34, 34, 34)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel2)
										.addComponent(jTextUsername, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel3)
										.addComponent(jTextPassword, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30).addComponent(jOKButton).addContainerGap(43, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void start() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("System".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private void addListeners() {
		this.addOKButtonListener(new OKButtonListener(this));
	}

	public void openWindow() {
		this.setVisible(true);
	}

	public void closeWindow() {
		this.setVisible(false);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JButton jOKButton;
	private javax.swing.JTextField jTextPassword;
	private javax.swing.JTextField jTextUsername;

	// End of variables declaration//GEN-END:variables

	private void addOKButtonListener(ActionListener a) {
		jOKButton.addActionListener(a);
	}

	private static class OKButtonListener implements ActionListener {

		private LoginDialog login;

		OKButtonListener(LoginDialog login) {
			this.login = login;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = login.getUsername();
			String password = login.getPassword();
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);

			// server check login !!
			Command command = new Command();
			command.setCommand(CommandType.LOGIN);
			command.addUser(user);
			Response response = Client.getInstance().executeCommand(command);
			try {
				if (response.getUsers() != null) {
					if (response.getUsers().get(0).getType().equalsIgnoreCase("doctor")) {
						// open doctor panel
						Doctor doctor = new Doctor();
						doctor.setID(response.getUsers().get(0).getID());
						DoctorPanel doctorPanel = new DoctorPanel(doctor);
						login.closeWindow();
						doctorPanel.openWindow();
						doctorPanel.displayTables();
					} else if (response.getUsers().get(0).getType().equalsIgnoreCase("secretary")) {
						// open secretary panel
						SecretaryPanel secretaryPanel = new SecretaryPanel();
						login.closeWindow();
						secretaryPanel.openWindow();
						secretaryPanel.displayTables();
					}
				}
			} catch (Exception ex) {
				System.out.println("Login refused by server...\n" + ex.getMessage());
			}
		}
	}

	private String getUsername() {
		return jTextUsername.getText();
	}

	private String getPassword() {
		return jTextPassword.getText();
	}

}
