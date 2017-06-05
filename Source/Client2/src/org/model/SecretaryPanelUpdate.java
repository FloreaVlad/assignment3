package org.model;

import java.util.TimerTask;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

import org.view.SecretaryPanel;

public class SecretaryPanelUpdate extends TimerTask {

	private SecretaryPanel secretaryPanel;

	public SecretaryPanelUpdate(SecretaryPanel secretaryPanel) {
		this.secretaryPanel = secretaryPanel;
	}

	@Override
	public void run() {
		Command command = new Command();
		command.setCommand(CommandType.CHECK_UPDATE);
		Response response = Client.getInstance().executeCommand(command);
		if (response.getConsultations().size() != 0) {
			secretaryPanel.displayPatients(response.getPatients());
		}
	}

}
