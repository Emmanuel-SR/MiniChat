package minichat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PORT = 5000;

	private String nickname;
	private String host;

	private JTextArea enteredText = new JTextArea(10, 32);
	private JTextField typedText = new JTextField(32);

	public Chat(String nickname, String host) {
		this.nickname = nickname;
		this.host = host;

		Servidor s = new Servidor(PORT);
		
		s.addObserver((o, arg) -> this.enteredText.append((String) arg));
		
		Thread t = new Thread(s);
		t.start();

		initGUI();
	}

	private void initGUI() {

		enteredText.setEditable(false);
		enteredText.setBackground(Color.LIGHT_GRAY);
		typedText.addActionListener(this);

		Container content = getContentPane();
		content.add(new JScrollPane(enteredText), BorderLayout.CENTER);
		content.add(typedText, BorderLayout.SOUTH);

		setTitle("MiniChat Client v1.0: [" + this.nickname + "]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		typedText.requestFocusInWindow();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String msg = "[" + nickname + "]: " + typedText.getText() + "\n";
		typedText.setText("");
		typedText.requestFocusInWindow();
		this.enteredText.append(msg);

		Client c = new Client(host, PORT, msg);
		Thread t = new Thread(c);
		t.start();
	}


	public static void main(String[] args) {
		new Chat(args[0], args[1]);
	}
}
