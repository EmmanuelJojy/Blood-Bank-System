import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Dashboard implements ActionListener {
	JPanel p;
	private ResultSet res;
	private String query;
	static String sbg;

	public Dashboard() {
		boolean status = false;
		String name = "";
		sbg = "   ";

		query = "SELECT * FROM BIO WHERE USERID = '" + Main.uid + "'";
		res = DB.query(query);
		try {
			if (res.next()) {
				status = true;
				name = ", " + res.getString("NAME");
				sbg = res.getString("BG");
			} else
				status = false;
		} catch (SQLException e) {
			System.out.println("#DB Query Error - " + e);
			System.exit(0);
		}

		p = new JPanel();
		p.setSize(1366, 720);
		p.setLayout(null);

		JLabel head = new JLabel("Hello" + name, JLabel.LEFT);
		head.setBounds(43, 40, 600, 40);
		head.setFont(new Font("SansSerif", Font.PLAIN, 28));
		p.add(head);

		JLabel bg = new JLabel(sbg, JLabel.CENTER);
		bg.setBounds(1090, 40, 66, 40);
		bg.setFont(new Font("SansSerif", Font.PLAIN, 28));
		bg.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		bg.setForeground(Color.RED);
		p.add(bg);

		JButton reload = new JButton("\u27F3");
		reload.setBounds(1165, 40, 55, 40);
		reload.setFont(new Font("SansSerif", Font.PLAIN, 24));
		reload.addActionListener(this);
		p.add(reload);

		JButton logout = new JButton("Log Out");
		logout.setBounds(1230, 40, 100, 40);
		logout.addActionListener(this);
		p.add(logout);

		JPanel transfers = new JPanel();
		transfers.setBounds(43, 105, 300, 75);
		transfers.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Blood Transfers \u21CC"));
		transfers.setLayout(null);
		p.add(transfers);

		JButton donate = new JButton("Donate");
		donate.setBounds(20, 25, 120, 35);
		donate.setEnabled(status);
		donate.addActionListener(this);
		transfers.add(donate);

		JButton request = new JButton("Request");
		request.setBounds(160, 25, 120, 35);
		request.setEnabled(status);
		request.addActionListener(this);
		transfers.add(request);

		JPanel reports = new JPanel();
		reports.setBounds(43, 200, 300, 75);
		reports.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Reports \u03A3"));
		reports.setLayout(null);
		p.add(reports);

		JButton report = new JButton("Report");
		report.setBounds(20, 25, 120, 35);
		report.setEnabled(status);
		report.addActionListener(this);
		reports.add(report);

		JPanel acc = new JPanel();
		acc.setBounds(43, 295, 300, 75);
		acc.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Accounts \u2713"));
		acc.setLayout(null);
		p.add(acc);

		JButton update = new JButton("Update");
		update.setBounds(20, 25, 120, 35);
		update.addActionListener(this);
		acc.add(update);

		JButton withdraw = new JButton("Withdraw");
		withdraw.setBounds(160, 25, 120, 35);
		withdraw.addActionListener(this);
		acc.add(withdraw);

		if (!status) {
			head.setText("Please update your details.");
			head.setForeground(Color.RED);
			head.setFont(new Font("SansSerif", Font.PLAIN, 20));
			bg.setVisible(false);
		}

		JLabel icon = new JLabel(new ImageIcon("dashboard.png"));
		icon.setBounds(427, 105, 901, 507);
		icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p.add(icon);
	}

	public String validateDonation() {
		try {
			query = "SELECT TRANSDATE FROM TRANSACTIONS WHERE USERID = '" + Main.uid
					+ "' AND TRANSTYPE = 'D' ORDER BY TRANSDATE DESC";
			res = DB.query(query);
			if (res.next()) {
				String d = res.getString("TRANSDATE").substring(0, 10);
				LocalDate next = LocalDate.parse(d).plusDays(90);
				LocalDate today = LocalDate.now();
				if (next.isAfter(today)) {
					return d + " " + next.toString();
				}
			}
		} catch (SQLException sqle) {
			System.out.println("# " + sqle);
		}
		return "all clear!";
	}

	public boolean validateRequest() {
		try {
			query = "SELECT QTY FROM BANK WHERE BG = '" + sbg + "';";
			res = DB.query(query);
			if (res.next()) {
				int qty = Integer.parseInt(res.getString("QTY"));
				if (qty > 0) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			System.out.println("# " + sqle);
		}
		return false;
	}

	public JScrollPane report() {
		String header[] = { "ID", "DATE", "TYPE", "BENEFICIARY", "GROUP", "HOSPITAL", "AUTHORIZER" };
		String data[][] = null;
		try {
			query = "SELECT COUNT(*) AS COUNT FROM USER_VIEW";
			res = DB.query(query);
			res.next();
			int n = Integer.parseInt(res.getString("COUNT"));
			data = new String[n][7];

			query = "SELECT * FROM USER_VIEW ORDER BY TRANSDATE DESC";
			res = DB.query(query);
			String temp;
			for (int i = 0; res.next(); i++) {
				data[i][0] = res.getString("TRANSID");
				data[i][1] = res.getString("TRANSDATE");
				temp = res.getString("TRANSTYPE");
				data[i][2] = temp.equals("D") ? "DONATE" : "REQUEST";
				data[i][3] = res.getString("B_NAME");
				data[i][4] = res.getString("BG");
				data[i][5] = res.getString("H_NAME");
				data[i][6] = res.getString("AUTHORISER");
			}
		} catch (SQLException sqle) {
			System.out.println("# " + sqle);
			System.exit(0);
		}

		JTable t = new JTable(data, header);
		t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		t.getTableHeader().setPreferredSize(new Dimension(900, 30));
		t.setRowHeight(30);

		JScrollPane scroll = new JScrollPane(t);
		scroll.setFont(new Font("SansSerif", Font.PLAIN, 20));
		scroll.setPreferredSize(new Dimension(900, 250));
		return scroll;

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Donate")) {
			// Donation WorkFlow
			// -----------------
			String msg;
			String stat = validateDonation();
			if (stat.equals("all clear!")) {
				query = "SELECT NAME FROM HOSPITAL";
				res = DB.query(query);

				msg = "You confirm to donate 1 US Pint Blood.\nChoose hospital for donation.\n\n";
				ArrayList<ArrayList<String>> data = DB.fetchList("HOSPITAL", "", "HSP", "NAME");
				Object hid[] = data.get(0).toArray();
				Object hlist[] = data.get(1).toArray();

				Object dhname = (String) JOptionPane.showInputDialog(GUI.f, msg, "Confirmation for Donation",
						JOptionPane.QUESTION_MESSAGE, null, hlist, hlist[0]);
				if (dhname != null) {
					int i = 0;
					for (i = 0; i < hlist.length; i++) {
						if (hlist[i] == dhname) {
							break;
						}
					}
					query = "CALL DONATE('" + Main.uid + "', '" + sbg + "', '" + hid[i] + "')";
					DB.executeProcedure(query);
					GUI.dashboard();
				}
			} else {
				String last[] = stat.trim().split(" ");
				msg = "You can only make a donation every 90 days." + "\nLast Donation: " + last[0] + "\nNext Donation: " + last[1] + "\n\n";
				JOptionPane.showMessageDialog(GUI.f, msg, "Premature Donation", JOptionPane.ERROR_MESSAGE);
			}
			// -----------------

		} else if (e.getActionCommand().equals("Request")) {
			// Request WorkFlow
			// ----------------
			GUI.request();
			// ----------------

		} else if (e.getActionCommand().equals("Update")) {
			// Request WorkFlow
			// ----------------
			GUI.update();
			// ----------------

		} else if (e.getActionCommand().equals("Withdraw")) {

			String msg = "Are you sure you want to withdraw from the Blood Bank program?"
					+ "\nThis will delete your profile and logs from the Active Database."
					+ "\n\nWithdrawal is irreversible.\n ";
			int n = JOptionPane.showConfirmDialog(GUI.f, msg, "Withdraw Confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (n == JOptionPane.YES_OPTION) {
				query = "DELETE FROM USERS WHERE USERID = '" + Main.uid + "'";
				DB.update(query);
				GUI.login();

			}
		} else if (e.getActionCommand().equals("Log Out")) {

			GUI.login();

		} else if (e.getActionCommand().equals("\u27F3")) {

			GUI.dashboard();

		} else if (e.getActionCommand().equals("Report")) {
			JDialog d = new JDialog(GUI.f, "Report Generation", true);
			d.setLayout(new BorderLayout());
			JLabel head = new JLabel("Transaction Report", JLabel.CENTER);
			head.setFont(new Font("SansSerif", Font.PLAIN, 28));
			head.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			d.add(head, BorderLayout.NORTH);
			d.add(report(), BorderLayout.CENTER);
			d.pack();
			d.setVisible(true);
			d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}
}
