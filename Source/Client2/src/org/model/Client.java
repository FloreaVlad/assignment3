package org.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.protocol.Command;
import net.protocol.Response;

import org.view.LoginDialog;

public class Client {

	private static final int PORT = 8081;
	private static final String HOST = "127.0.0.1";
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private static Client client;

	private Client() {
		try {
			socket = new Socket(HOST, PORT);
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Client getInstance() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}

	public void closeConnection() throws IOException {
		socket.close();
		input.close();
		output.close();
	}

	public Response executeCommand(Command command) {
		Response response = null;
		try {
			output.writeObject(command);
			response = (Response) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String args[]) {
		LoginDialog login = new LoginDialog();
		login.openWindow();
	}
}
