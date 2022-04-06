import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Request implements ActionListener, ItemListener {
    JPanel p;
    private String query;
    private ResultSet res;

    private JButton submit;
    private JTextField name, ad1, ad2, pin, age, ph, ref;
    // private JPasswordField pass;
    private JLabel avail;
    private ArrayList<JComponent> all = new ArrayList<JComponent>();
    private JComboBox<String> bg, dist, st;
    private JComboBox<Object> hsp;
    private JCheckBox self;

    private static String bgs[] = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" },
            dists[] = { "Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasargod", "Kollam", "Kottayam", "Kozhikode",
                    "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvanathapuram", "Thrissur", "Wayanad" },
            sts[] = { "Kerala" };
    Object[] hspName, hspId;

    private int ignore = 0;

    public Request() {
        p = new JPanel();
        p.setSize(1366, 720);
        p.setLayout(null);

        ArrayList<ArrayList<String>> data = DB.fetchList("HOSPITAL", "", "HSP", "NAME");
        hspId = data.get(0).toArray();
        hspName = data.get(1).toArray();

        self = new JCheckBox(" Set request for self/personal requirement.");
        self.setBounds(43, 40, 600, 30);
        self.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(self);
        self.addItemListener(this);

        JLabel basic = new JLabel("Basic Details", JLabel.LEFT);
        basic.setBounds(43, 90, 400, 40);
        basic.setFont(new Font("SansSerif", Font.PLAIN, 28));
        p.add(basic);

        JLabel lname = new JLabel("Name");
        lname.setBounds(63, 150, 100, 40);
        lname.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lname);

        name = new JTextField();
        name.setBounds(170, 155, 200, 30);
        all.add(name);
        p.add(name);

        JLabel lph = new JLabel("Phone");
        lph.setBounds(63, 200, 100, 40);
        lph.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lph);

        ph = new JTextField();
        ph.setBounds(170, 205, 200, 30);
        all.add(ph);
        p.add(ph);

        JLabel lage = new JLabel("Age");
        lage.setBounds(450, 150, 100, 40);
        lage.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lage);

        age = new JTextField();
        age.setBounds(547, 155, 50, 30);
        all.add(age);
        p.add(age);

        JLabel lbg = new JLabel("Group");
        lbg.setBounds(63, 250, 100, 40);
        lbg.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lbg);

        bg = new JComboBox<String>(bgs);
        bg.setBounds(170, 255, 200, 30);
        bg.addItemListener(this);
        all.add(bg);
        p.add(bg);

        avail = new JLabel("", JLabel.CENTER);
        avail.setBounds(450, 250, 150, 40);
        avail.setBorder(BorderFactory.createLineBorder(Color.RED));
        avail.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(avail);

        JLabel address = new JLabel("Residential Details", JLabel.LEFT);
        address.setBounds(43, 310, 400, 40);
        address.setFont(new Font("SansSerif", Font.PLAIN, 28));
        p.add(address);

        JLabel lad1 = new JLabel("Address Line 1");
        lad1.setBounds(63, 370, 250, 40);
        lad1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lad1);

        ad1 = new JTextField();
        ad1.setBounds(300, 375, 483, 30);
        all.add(ad1);
        p.add(ad1);

        JLabel lad2 = new JLabel("Address Line 2");
        lad2.setBounds(63, 420, 250, 40);
        lad2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lad2);

        ad2 = new JTextField();
        ad2.setBounds(300, 425, 483, 30);
        all.add(ad2);
        p.add(ad2);

        JLabel ldist = new JLabel("District");
        ldist.setBounds(63, 470, 100, 40);
        ldist.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(ldist);

        dist = new JComboBox<String>(dists);
        dist.setBounds(170, 475, 100, 30);
        all.add(dist);
        p.add(dist);

        JLabel lst = new JLabel("State");
        lst.setBounds(320, 470, 100, 40);
        lst.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lst);

        st = new JComboBox<String>(sts);
        st.setBounds(427, 475, 100, 30);
        all.add(st);
        p.add(st);

        JLabel lpin = new JLabel("Pin Code");
        lpin.setBounds(577, 470, 100, 40);
        lpin.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lpin);

        pin = new JTextField("XXXXXX");
        pin.setBounds(684, 475, 100, 30);
        all.add(pin);
        p.add(pin);

        JButton back = new JButton("BACK");
        back.setBounds(43, 555, 100, 30);
        p.add(back);
        back.addActionListener(this);

        submit = new JButton("SUBMIT");
        submit.setBounds(160, 555, 100, 30);
        p.add(submit);
        submit.addActionListener(this);

        JLabel auth = new JLabel("Authorization", JLabel.LEFT);
        auth.setBounds(880, 90, 400, 40);
        auth.setFont(new Font("SansSerif", Font.PLAIN, 28));
        p.add(auth);

        JLabel lhsp = new JLabel("Hospital Name");
        lhsp.setBounds(900, 150, 140, 40);
        lhsp.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lhsp);

        hsp = new JComboBox<Object>(hspName);
        hsp.setBounds(1070, 155, 200, 30);
        p.add(hsp);

        // JLabel lpass = new JLabel("HSP Passkey");
        // lpass.setBounds(900, 200, 140, 40);
        // lpass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // p.add(lpass);

        // pass = new JPasswordField();
        // pass.setBounds(1070, 205, 200, 30);
        // p.add(pass);

        JLabel lref = new JLabel("HSP Refferal");
        lref.setBounds(900, 200, 140, 40);
        lref.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lref);

        ref = new JTextField("Dr. ");
        ref.setBounds(1070, 205, 200, 30);
        p.add(ref);

        isAvailable();
    }

    public boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int isAvailable() {
        int qty = 0;
        String dbg = (String) bg.getSelectedItem();
        query = "SELECT QTY FROM BANK WHERE BG = '" + dbg + "'";
        res = DB.query(query);
        try {
            res.next();
            qty = Integer.parseInt(res.getString("QTY"));
            if (qty > 0) {
                submit.setEnabled(true);
                avail.setForeground(Color.GREEN);
                avail.setText("Available");
            } else {
                submit.setEnabled(false);
                avail.setForeground(Color.RED);
                avail.setText("Not Available");
            }
        } catch (SQLException sqle) {
            System.out.println("# " + sqle);
        }

        return qty;
    }

    public void restore(boolean enabled) {
        String dname = "", dbg = "", dad1 = "", dad2 = "", dpin = "XXXXXX", ddist = "", dstate = "", dage = "",
                dph = "";
        if (enabled) {
            try {
                query = "SELECT * FROM BIO WHERE USERID = '" + Main.uid + "'";
                res = DB.query(query);
                res.next();
                dname = res.getString("NAME");
                dbg = res.getString("BG").trim();
                dage = res.getString("AGE");

                query = "SELECT * FROM ADDRESS WHERE ADDRID = '" + Main.uid + "'";
                res = DB.query(query);

                res.next();
                dad1 = res.getString("AD1");
                dad2 = res.getString("AD2");
                dph = res.getString("PHONE");
                ddist = res.getString("DISTRICT");
                dstate = res.getString("STATE");
                dpin = res.getString("PINCODE");

            } catch (SQLException e) {
                System.out.println("#DB Query Error - " + e);
                System.exit(0);
            }
        }
        // query = "SELECT * FROM BIO WHERE UID = '" + Main.uid + "';";
        // res = DB.query(query);
        // try {
        // res.next();
        // dname = res.getString("NAME");
        // dbg = res.getString("BG");
        // dad1 = res.getString("AD1");
        // dad2 = res.getString("AD2");
        // dpin = res.getString("PIN");
        // ddist = res.getString("DIST");
        // dstate = res.getString("STATE");
        // dage = res.getString("AGE");
        // dph = res.getString("PH");
        // } catch (SQLException sqle) {
        // System.out.println("# " + sqle);
        // System.exit(0);
        // }
        // }

        name.setText(dname);
        ad1.setText(dad1);
        ad2.setText(dad2);
        pin.setText(dpin);
        age.setText(dage);
        ph.setText(dph);

        if (enabled) {
            int index = 0;
            for (int i = 0; i < bgs.length; i++) {
                if (bgs[i].equals(dbg)) {
                    index = i;
                    break;
                }
            }
            bg.setSelectedIndex(index);

            for (int i = 0; i < dists.length; i++) {
                if (dists[i].equals(ddist)) {
                    index = i;
                    break;
                }
            }
            dist.setSelectedIndex(index);

            for (int i = 0; i < sts.length; i++) {
                if (sts[i].equals(dstate)) {
                    index = i;
                    break;
                }
            }
            st.setSelectedIndex(index);
        } else {
            bg.setSelectedIndex(0);
            dist.setSelectedIndex(0);
            st.setSelectedIndex(0);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ((JButton) e.getSource() == submit) {
            boolean flag = false;
            String dname = name.getText(), dad1 = ad1.getText(), dad2 = ad2.getText(), dpin = pin.getText(),
                    dage = age.getText(), dph = ph.getText(), ddist = (String) dist.getSelectedItem(),
                    dstate = (String) st.getSelectedItem(), dbg = (String) bg.getSelectedItem(),
                    dhsp = (String) hsp.getSelectedItem(),
                    /* dpass = new String(pass.getPassword()), */ dref = ref.getText();
            if (dname.equals("")) {
                flag = true;
                name.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dad1.equals("")) {
                flag = true;
                ad1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dad2.equals("")) {
                flag = true;
                ad2.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dpin.equals("") || dpin.length() != 6 || !isNumber(dpin)) {
                flag = true;
                pin.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dstate.equals("")) {
                flag = true;
                st.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dage.equals("") || !isNumber(dage)) {
                flag = true;
                age.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dph.equals("") || dph.length() != 10 || !isNumber(dph)) {
                flag = true;
                ph.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (dhsp.equals("")) {
                flag = true;
                hsp.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            /*
             * if (dpass.equals("")) {
             * flag = true;
             * pass.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
             * }
             */
            if (dref.equals("") || dref.equals("Dr. ")) {
                flag = true;
                ref.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }

            if (flag) {
                return;
            }

            // Authorization
            int i = 0;
            for (i = 0; i < hspName.length; i++) {
                if (hspName[i] == dhsp) {
                    break;
                }
            }

            if (!self.isSelected()) {
                Formatter formatter = new Formatter();
                formatter.format("'%s','%s',%s,'%s','%s',%s,'%s','%s',%s", dname, dbg,
                        dage, dad1, dad2, dph, ddist, dstate, dpin);
                query = "CALL INSERT_ANONYMOUS(" + formatter + ")";
                String currentAnyId = DB.executeFunction(query);
                formatter.close();

                formatter = new Formatter();
                formatter.format("'%s', '%s', '%s', '%s', '%s'", Main.uid, currentAnyId, dbg, hspId[i], dref);
                query = "CALL REQUEST(" + formatter + ")";
                DB.executeProcedure(query);
                formatter.close();
            } else {
                Formatter formatter = new Formatter();
                formatter.format("'%s', '%s', '%s', '%s', '%s'", Main.uid, Main.uid, dbg, hspId[i], dref);
                query = "CALL REQUEST(" + formatter + ")";
                DB.executeProcedure(query);
                formatter.close();
            }

            GUI.dashboard();

            // if (flag) {
            // String msg = "Your request could not be authorized." + "\nRequest
            // Authorization Failure." + "\n\n";
            // JOptionPane.showMessageDialog(GUI.f, msg, "Authorization Failure",
            // JOptionPane.ERROR_MESSAGE);
            // } else {
            // query = "INSERT INTO REQUEST (NAME, BG, AD1, AD2, PIN, DIST, STATE, AGE, PH,
            // HSP, DOC) VALUES ('"
            // + dname + "', '" + dbg + "', '" + dad1 + "', '" + dad2 + "', '" + dpin + "',
            // '" + ddist + "', '"
            // + dstate + "', '" + dage + "', '" + dph + "', '" + dhsp + "', '" + dref +
            // "');";
            // DB.update(query);

            // int qty = isAvailable() - 1;
            // query = "UPDATE BANK SET QTY = '" + qty + "' WHERE GRP = '" + dbg + "';";
            // DB.update(query);

            // query = "INSERT INTO REPORTS (UID, BG, TYPE) VALUES ('" + Main.uid + "', '" +
            // dbg + "', 'REQUEST');";
            // DB.update(query);

            // String msg = "Your request has been sanctioned for " + dbg
            // + ". Check Report for more details.\n\nRequest Authorizer:\n" + dref + "\n"
            // /* + hspname */
            // + ".\n\n";
            // JOptionPane.showMessageDialog(GUI.f, msg, "Request Authorized",
            // JOptionPane.INFORMATION_MESSAGE);
            // GUI.dashboard();

            // }
        } else
            GUI.dashboard();
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            boolean enabled = self.isSelected();
            restore(enabled);
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i) instanceof JTextField) {
                    JTextField temp = (JTextField) all.get(i);
                    temp.setEditable(!enabled);
                } else {
                    all.get(i).setEnabled(!enabled);
                }
            }
        } else {
            ignore = (ignore + 1) % 2;
            if (ignore == 1) {
                isAvailable();
            }
        }
    }
}
