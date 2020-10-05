package minichat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Observable implements Runnable {

	private int port;

	public Servidor(int port) {
		this.port = port;
	}

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream in;

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is started.");

			while (true) {

				System.out.println("Server is waiting for client request...");
				socket = serverSocket.accept();
				
				System.out.println("Client connected.");
				in = new DataInputStream(socket.getInputStream());
				
				String msg = in.readUTF();
				System.out.println(msg);
				
				this.setChanged();
				this.notifyObservers(msg);
				this.clearChanged();
				
				socket.close();
				System.out.println("Client disconected.");
			}

		} catch (IOException e) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
		}

	}

}
