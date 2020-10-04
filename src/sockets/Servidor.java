package sockets;

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

		ServerSocket servidor = null;
		Socket sc = null;
		DataInputStream in;

		try {
			servidor = new ServerSocket(port);
			System.out.println("Servidor inciado");

			while (true) {

				System.out.println("Client connected.");
				sc = servidor.accept(); // waiting
				in = new DataInputStream(sc.getInputStream());

				String msg = in.readUTF(); // waiting

				System.out.println(msg);
				
				this.setChanged();
				this.notifyObservers(msg);
				this.clearChanged();

				sc.close();

				System.out.println("Client disconected.");
			}

		} catch (IOException e) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
		}

	}

}
