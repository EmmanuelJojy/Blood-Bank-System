import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends WindowAdapter{
	static JFrame f;
	static JLabel stat;
	private static JPanel plug;

	public GUI() {
		f = new JFrame("Blood Bank System");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setLayout(new BorderLayout());		
		f.addWindowListener(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel head = new JLabel("Blood Bank System", JLabel.CENTER);
		head.setFont(new Font("SanSerif", Font.PLAIN, 48));
		f.add(head, BorderLayout.NORTH);

		stat = new JLabel("", JLabel.RIGHT);
		stat.setForeground(Color.red);
		f.add(stat, BorderLayout.SOUTH);
		
		// First plug
		login();

		f.setVisible(true);

	}

	private static void changePlug() {
		try {
			plug.setVisible(false);
			f.remove(plug);
		} catch (NullPointerException e) {
		}
	}

	public static void login() {
		changePlug();
		new Access();
		plug = Access.p;
		plug.setBounds(0, 0, 1366, 720);
		f.add(plug, BorderLayout.CENTER);
	}

	public static void dashboard() {
		changePlug();
		plug = new Dashboard().p;
		plug.setBounds(0, 0, 1366, 720);
		f.add(plug, BorderLayout.CENTER);
	}

	public static void update() {
		changePlug();
		plug = new Update().p;
		plug.setBounds(0, 0, 1366, 720);
		f.add(plug, BorderLayout.CENTER);
	}

	public static void request() {
		changePlug();
		plug = new Request().p;
		plug.setBounds(0, 0, 1366, 720);
		f.add(plug, BorderLayout.CENTER);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.print("\n= Disconnecting DB... ");
		DB.disconnect();
		System.out.println("Complete");
	}
}
