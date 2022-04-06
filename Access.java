import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Access {

	static JPanel p, min, register;
	static Login login;
	static JLabel status;

	public Access() {
		p = new JPanel();
		p.setLayout(null);

		JLabel title = new JLabel("Authentication", JLabel.CENTER);
		title.setFont(new Font("SansSerif", Font.PLAIN, 38));
		title.setBounds(533, 20, 300, 35);
		p.add(title);

		min = new JPanel();
		min.setLayout(null);

		// register = new Register().p;

		login = new Login();
		min.add(login.p);

		JLabel icon = new JLabel(new ImageIcon("cover.jpg"));
		icon.setBounds(30, 105, 900, 495);
		icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p.add(icon);

		/*
		 * status = new JLabel("User Authentication Service");
		 * status.setBounds(30, 550, 450, 50);
		 * status.setFont(new Font("Consolas", Font.PLAIN, 14));
		 * min.add(status);
		 */

		min.setBounds(0, 45, 1366, 720);
		p.add(min);

	}

	public static void showLogin() {
		register.setVisible(false);
		min.remove(register);
		register = null;

		login = new Login();
		login.p.setVisible(true);
		min.add(login.p);
	}

	public static void showRegister() {
		login.p.setVisible(false);
		min.remove(login.p);
		login = null;

		register = new Register().p;
		register.setVisible(true);
		min.add(register);
	}
}

class Register implements ActionListener {

	JPanel p;
	JLabel head, l1, l2, l2_re;
	JTextField name;
	JPasswordField pass, pass_re;
	JButton b, s;

	public Register() {

		p = new JPanel();
		p.setLayout(null);
		p.setBounds(964, 60, 300, 510);
		// p.setBorder(BorderFactory.createLineBorder(Color.black));

		head = new JLabel("New Users");
		head.setFont(new Font("Calibri", Font.BOLD, 32));
		head.setBounds(15, 5, 300, 45);

		l1 = new JLabel("User Name");
		l1.setFont(new Font("Calibri", Font.PLAIN, 28));
		l1.setBounds(15, 65, 200, 50);
		name = new JTextField(15);
		name.setBounds(15, 115, 250, 35);

		l2 = new JLabel("Password");
		l2.setFont(new Font("Calibri", Font.PLAIN, 28));
		l2.setBounds(15, 160, 200, 50);
		pass = new JPasswordField(15);
		pass.setBounds(15, 215, 250, 35);

		l2_re = new JLabel("Re-enter Password");
		l2_re.setFont(new Font("Calibri", Font.PLAIN, 28));
		l2_re.setBounds(15, 260, 300, 50);
		pass_re = new JPasswordField(15);
		pass_re.setBounds(15, 315, 250, 35);

		b = new JButton("Register");
		b.setFont(new Font("Calibri", Font.PLAIN, 26));
		b.setBounds(15, 380, 140, 35);
		b.addActionListener(this);

		s = new JButton("Login");
		s.setFont(new Font("Calibri", Font.PLAIN, 24));
		s.setBounds(170, 380, 120, 35);
		s.addActionListener(this);

		p.add(head);
		p.add(l1);
		p.add(name);
		p.add(l2);
		p.add(pass);
		p.add(l2_re);
		p.add(pass_re);
		p.add(b);
		p.add(s);
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getActionCommand() == "Register") {
			ResultSet res;
			String uid, pas, par, query;
			uid = name.getText().toLowerCase();
			pas = new String(pass.getPassword());
			par = new String(pass_re.getPassword());
			if (uid.equals("") || pas.equals("") || par.equals("")) {
				showError("FIELDS CANNOT BE EMPTY");
				return;
			}
			if (Character.isDigit(uid.charAt(0))) {
				showError("USER ID SHOULD START WITH ALPHABET");
				return;
			}
			if (!par.equals(pas)) {
				showError("PASSWORDS DO NOT MATCH");
				return;
			}
			try {
				query = "SELECT * FROM USERS WHERE USERID = '" + uid + "'";
				res = DB.query(query);
				if (res.next()) {
					showError("USER NAME NOT AVAILABLE");
					return;
				}
				query = "INSERT INTO USERS VALUES('" + uid + "', '" + pas + "', 'P')";
				DB.update(query);
			} catch (SQLException e) {
				System.out.println("#DB Error - " + e);
				System.exit(0);
			}
			p.setVisible(false);
			GUI.stat.setText("Login with Creadential");
			GUI.stat.setForeground(Color.GREEN);
			Access.showLogin();
			Access.login.p.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		} else {
			Access.showLogin();
		}
	}

	public void showError(String msg) {
		GUI.stat.setText("ERROR: " + msg.toUpperCase());
		p.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	}
}

class Login implements ActionListener, DocumentListener {

	JPanel p;
	JLabel head, l1, l2;
	JTextField name;
	JPasswordField pass;
	JButton b, s;
	JCheckBox serialize;

	public Login() {
		p = new JPanel();
		p.setLayout(null);
		p.setBounds(980, 120, 300, 350);
		// p.setBorder(BorderFactory.createLineBorder(Color.black));

		head = new JLabel("Sign-in");
		head.setFont(new Font("Calibri", Font.BOLD, 32));
		head.setBounds(15, 5, 300, 45);

		l1 = new JLabel("User Name");
		l1.setFont(new Font("Calibri", Font.PLAIN, 28));
		l1.setBounds(15, 55, 200, 50);
		name = new JTextField(15);
		name.setBounds(15, 105, 250, 35);
		name.getDocument().addDocumentListener(this);

		l2 = new JLabel("Password");
		l2.setFont(new Font("Calibri", Font.PLAIN, 28));
		l2.setBounds(15, 150, 200, 50);
		pass = new JPasswordField(15);
		pass.setBounds(15, 200, 250, 35);
		pass.getDocument().addDocumentListener(this);

		b = new JButton("Login");
		b.setFont(new Font("Calibri", Font.PLAIN, 24));
		b.setBounds(15, 260, 120, 35);
		b.addActionListener(this);

		s = new JButton("Register");
		s.setFont(new Font("Calibri", Font.PLAIN, 24));
		s.setBounds(150, 260, 120, 35);
		s.addActionListener(this);

		serialize = new JCheckBox("Remember my credential.");
		serialize.setFont(new Font("SansSerif", Font.PLAIN, 16));
		serialize.setBounds(15, 310, 240, 35);
		p.add(serialize);

		User user = User.DeSerialize();
		if (user != null) {
			name.setText(user.userid);
			pass.setText(user.password);
			name.setForeground(Color.WHITE);
			pass.setForeground(Color.WHITE);
			name.setBackground(Color.DARK_GRAY);
			pass.setBackground(Color.DARK_GRAY);
		}

		p.add(head);
		p.add(l1);
		p.add(name);
		p.add(l2);
		p.add(pass);
		p.add(b);
		p.add(s);
	}

	public void showError(String msg) {
		GUI.stat.setText("ERROR: " + msg.toUpperCase());
		GUI.stat.setForeground(Color.RED);
		p.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	}

	private void changeMode() {
		name.setForeground(Color.BLACK);
		pass.setForeground(Color.BLACK);
		name.setBackground(Color.WHITE);
		pass.setBackground(Color.WHITE);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getActionCommand() == "Login") {
			String uid, pas, query;
			uid = name.getText().toLowerCase();
			pas = new String(pass.getPassword());
			if (uid.equals("") || pas.equals("")) {
				showError("FIELDS CANNOT BE EMPTY");
				return;
			}
			query = "CALL VALIDATE_USER('" + uid + "', '" + pas + "')";
			String stat = DB.executeFunction(query);
			if (stat.equals("VALID")) {
				Main.uid = uid;
				query = "CALL GENERATE_VIEW('" + Main.uid + "')";
				DB.executeProcedure(query);
				if (serialize.isSelected()) {
					new User(uid, pas).serialize();
				}
				GUI.dashboard();
			} else {
				showError(stat);
				return;
			}
		} else {
			Access.showRegister();
		}

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changeMode();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeMode();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changeMode();
	}
}
