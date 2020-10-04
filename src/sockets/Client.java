package sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

	private int port;
	private String message;

	public Client(int port, String message) {
		this.port = port;
		this.message = message;
	}

	@Override
	public void run() {

		final String HOST = "127.0.0.1";

		DataOutputStream out;

		try {
			Socket sc = new Socket(HOST, port);

			out = new DataOutputStream(sc.getOutputStream());

			out.writeUTF(message);

			sc.close();

		} catch (IOException e) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
