package org.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.view.AdminPanel;

public class Server {

	private static final int PORT = 8081;
	private ServerSocket serverSocket;
	private static ServerModel serverModel;
	public static int nrOfClients;

	public Server() {
		nrOfClients = 0;
	}

	public void closeSocket() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(PORT);
			waitClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitClient() {
		while (!serverSocket.isClosed()) {
			try {
				Socket client = serverSocket.accept();
				nrOfClients++;
				ServerClient serverClient = new ServerClient(client, serverModel);
				new Thread(serverClient).start();
			} catch (IOException e) {
				System.out.println("Server closed: " + e.getMessage());
			}
		}
	}

	public static void main(String args[]) {
		PatientsModel patientsModel = new PatientsModel();
		UsersModel usersModel = new UsersModel();
		AdminPanel adminPanel = new AdminPanel();
		serverModel = new ServerModel(adminPanel);
		adminPanel.setPatientsModel(patientsModel);
		adminPanel.setUsersModel(usersModel);
		adminPanel.setServerModel(serverModel);

		adminPanel.openWindow();
		adminPanel.displayTables();

		Server server = new Server();
		adminPanel.setServer(server);
		server.start();
	}
}
