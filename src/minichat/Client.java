package minichat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

	private String host;
	private int port;
	private String message;

	public Client(String host, int port, String message) {
		this.host = host;
		this.port = port;
		this.message = message;
	}

	@Override
	public void run() {

		DataOutputStream out;

		try {
			Socket socket = new Socket(host, port);

			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(message);

			out.close();

			socket.close();

		} catch (IOException e) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
