package chat.client;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientFrame extends JFrame {
	
	JTextField msgField ;
	JTextArea display ;
	JList<String> users ;
	private DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM) ;
	
	ChatClientImpl client ;
	
	public ChatClientFrame(String[] args) {
		super("Not connected") ;
		
		String user = JOptionPane.showInputDialog("Yooo people ! What's your name ?") ;
		
		buildUI() ;
		addUIListeners() ;
		
		client = new ChatClientImpl(user, args, this) ;

		setVisible(true) ;
		msgField.grabFocus() ;
	}

	private void buildUI() {
		
		getContentPane().setLayout(new BorderLayout()) ;
		
		display = new JTextArea() ;
		display.setEditable(false) ;
		add(new JScrollPane(display), BorderLayout.CENTER) ;
		
		msgField =  new JTextField() ;
		add(msgField, BorderLayout.SOUTH) ;
		
		users = new JList<String>() ;
		users.setPrototypeCellValue("Greg Ze Best") ;
		add(users, BorderLayout.EAST) ;
		
		setSize(400, 250) ;
		
	}
	
	private void sendMsg(){
		client.sendToAll(msgField.getText()) ;
		msgField.setText("") ;
	}
	
	private void addUIListeners(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.unRegister() ;
				System.exit(0) ;
			}
		}) ;
		
		msgField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMsg() ;
				}
			}
		});
		
	}
	
	public void appendMsg(String msg) {
		display.append(dateFormat.format(new Date()) + " : " + msg) ;
		msgField.grabFocus() ;
	}
	
	public static void main(String[] args) {
		new ChatClientFrame(args) ;
	}

}
