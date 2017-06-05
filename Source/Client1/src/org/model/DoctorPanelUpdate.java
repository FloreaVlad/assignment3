package org.model;

import java.util.TimerTask;

import javax.swing.JOptionPane;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

import org.view.DoctorPanel;

public class DoctorPanelUpdate extends TimerTask {

	private DoctorPanel doctorPanel;

	public DoctorPanelUpdate(DoctorPanel doctorPanel) {
		this.doctorPanel = doctorPanel;
	}

	@Override
	public void run() {
		Command command = new Command();
		command.setCommand(CommandType.CHECK_UPDATE);
		Response response = Client.getInstance().executeCommand(command);
		if (response.getConsultations().size() != 0 && response.getInfo().equals("new patient")) {
			command = new Command();
			command.setCommand(CommandType.GET_PATIENTS_BY_DOCTOR);
			command.addDoctor(doctorPanel.getDoctor());
			doctorPanel.eraseConsultationsTable();
			doctorPanel.displayPatients(Client.getInstance().executeCommand(command).getPatients());
		} else if (response.getConsultations().size() != 0 && response.getInfo().equals("patient update")) {
			command = new Command();
			command.setCommand(CommandType.GET_PATIENTS_BY_DOCTOR);
			command.addDoctor(doctorPanel.getDoctor());
			doctorPanel.eraseConsultationsTable();
			doctorPanel.displayPatients(Client.getInstance().executeCommand(command).getPatients());
		} else if (response.getConsultations().size() != 0 && response.getInfo().equals("patient deleted")) {
			command = new Command();
			command.setCommand(CommandType.GET_PATIENTS_BY_DOCTOR);
			command.addDoctor(doctorPanel.getDoctor());
			doctorPanel.eraseConsultationsTable();
			doctorPanel.displayPatients(Client.getInstance().executeCommand(command).getPatients());
		}
		if (response.getConsultations().size() != 0 && response.getInfo().length() > 0) {
			command = new Command();
			command.setCommand(CommandType.GET_PATIENTS_BY_DOCTOR);
			command.addDoctor(doctorPanel.getDoctor());
			doctorPanel.eraseConsultationsTable();
			doctorPanel.displayPatients(Client.getInstance().executeCommand(command).getPatients());
			JOptionPane.showMessageDialog(doctorPanel, response.getInfo(), "Update", JOptionPane.PLAIN_MESSAGE);
		}
	}

}
