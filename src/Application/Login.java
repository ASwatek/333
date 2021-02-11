package Application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import CRUD.Read;
import CRUD.Validation;
import Views.MechanicView;
import Views.SecretaryView;
import Views.View;

public class Login {
	private View viewToReturn;
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel message;

	public Login() {
		frame = new JFrame("Chop Shop");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setLayout(new GridLayout(4, 1));
		JPanel usernamePanel = new JPanel();
		JLabel usernameLabel = new JLabel("username: ");
		usernameField = new JTextField(20);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);

		JPanel passwordPanel = new JPanel();
		JLabel passwordLabel = new JLabel("password: ");
		passwordField = new JPasswordField(20);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		JPanel messagePanel = new JPanel();
		message = new JLabel();
		messagePanel.add(message);

		JPanel buttonPanel = new JPanel();
		JButton LoginButton = new JButton("Login");
		buttonPanel.add(LoginButton);

		LoginButton.addActionListener(new LoginListener());

		frame.add(usernamePanel);
		frame.add(passwordPanel);
		frame.add(messagePanel);
		frame.add(buttonPanel);
		frame.setVisible(true);
	}

	private void finishLogin(String alias, String password) {
		System.out.println("Logging in : "+ alias);
		byte[] salt = Read.getSalt(alias);
		String hashedPassword = Validation.hashPassword(salt, password);
		
		int loginValue = Read.AttemptLogin(alias, hashedPassword);
		System.out.println(loginValue);
		switch (loginValue) {
		case 0:
			viewToReturn = new SecretaryView();
			break;
		case 1:
			viewToReturn = new MechanicView();
			break;
		default:
			// pop up about invalid login
			viewToReturn = null;
		}
		if(viewToReturn != null){
			frame.dispose();
			System.out.println("Login complete");
		}
		else{
			message.setText("invalid login");
		}
	}
	
	public View getEmployeeView(){
		return viewToReturn;
	}

	private class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			char[] passwordArray = passwordField.getPassword();
			StringBuilder builder = new StringBuilder();
			for(char c: passwordArray){
				builder.append(c);
			}
			finishLogin(usernameField.getText(), builder.toString());
		}
	}

}
