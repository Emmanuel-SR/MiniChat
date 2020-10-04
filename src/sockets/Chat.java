package sockets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame implements Observer, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String screenName;

	private JTextArea enteredText = new JTextArea(10, 32);
	private JTextField typedText = new JTextField(32);

	public Chat(String screenName) {
		this.screenName = screenName;

		Servidor s = new Servidor(5000);
		s.addObserver(this);
		Thread t = new Thread(s);
		t.start();

		initGUI();
	}

	private void initGUI() {
		// create GUI stuff
		enteredText.setEditable(false);
		enteredText.setBackground(Color.LIGHT_GRAY);
		typedText.addActionListener(this);

		Container content = getContentPane();
		content.add(new JScrollPane(enteredText), BorderLayout.CENTER);
		content.add(typedText, BorderLayout.SOUTH);

		// display the window, with focus on typing box
		setTitle("Chat Client 1.0: [" + this.screenName + "]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		typedText.requestFocusInWindow();
		setVisible(true);
	}

	// process TextField after user hits Enter
	public void actionPerformed(ActionEvent e) {
		String msg = "[" + screenName + "]: " + typedText.getText() + "\n";
		typedText.setText("");
		typedText.requestFocusInWindow();
		this.enteredText.append(msg);

		Client c = new Client(6000, msg);
		Thread t = new Thread(c);
		t.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.enteredText.append((String) arg);
	}

	public static void main(String[] args) {
		new Chat("Client 1");
	}
}
