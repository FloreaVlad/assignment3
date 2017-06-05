package org.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.protocol.Command;
import net.protocol.CommandType;
import net.protocol.Response;

public class ServerClient implements Runnable {
	private ServerModel serverModel;
	private Socket clientSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public ServerClient(Socket clientSocket, ServerModel serverModel) {
		this.clientSocket = clientSocket;
		this.serverModel = serverModel;
		try {
			this.clientSocket.setKeepAlive(true);
			input = new ObjectInputStream(clientSocket.getInputStream());
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			output.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!clientSocket.isClosed()) {
			try {
				Command command = (Command) input.readObject();
				Response response = serverModel.processCommand(command);
				output.writeObject(response);
				while (command.getCommand() != CommandType.FINISH) {
					command = (Command) input.readObject();
					response = serverModel.processCommand(command);
					output.writeObject(response);
				}
				clientSocket.close();
				input.close();
				output.close();
				Server.nrOfClients--;
			} catch (Exception ex) {
				System.out.println("Closed client: " + clientSocket.toString());
			}
		}
	}
}
