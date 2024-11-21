package br.com.fiap.interfacegrafica;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(470, 190, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Usuário");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setBounds(190, 111, 61, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Verdana", Font.BOLD, 12));
		lblSenha.setBounds(195, 163, 46, 14);
		frame.getContentPane().add(lblSenha);

		textField = new JTextField();
		textField.setBounds(163, 132, 105, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(163, 182, 105, 20);
		frame.getContentPane().add(passwordField);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1
				.setIcon(new ImageIcon(Login.class.getResource("/resources/logo-tokio-marine-seguradora-1024.png")));
		lblNewLabel_1.setBounds(67, -54, 305, 223);
		frame.getContentPane().add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Logar");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(178, 213, 76, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(Login.class.getResource("/resources/shutdown-1.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(393, 220, 31, 30);
		frame.getContentPane().add(btnNewButton_1);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Logar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			String senha = passwordField.getText();
			frame.dispose();
			
			try {
				new Main();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		}
	}
}
