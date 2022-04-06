import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Update implements ActionListener, ItemListener {
    JPanel p;
    private ResultSet res;
    private String query;

    private JButton submit;
    private JTextField name, ad1, ad2, pin, age, ph;
    private JComboBox<String> bg, dist, st;
    private boolean status = false;

    public Update() {
        p = new JPanel();
        p.setSize(1366, 720);
        p.setLayout(null);

        String dname = "", dbg = "", dad1 = "", dad2 = "", dpin = "", ddist = "", dstate = "", dage = "", dph = "";

        query = "SELECT * FROM BIO WHERE USERID = '" + Main.uid + "'";
        res = DB.query(query);
        try {
            if (res.next()) {
                status = true;
                dname = res.getString("NAME");
                dbg = res.getString("BG").trim();
                dage = res.getString("AGE");

                query = "SELECT * FROM ADDRESS WHERE ADDRID = '" + Main.uid + "'";
                res = DB.query(query);

                if (res.next()) {
                    dad1 = res.getString("AD1");
                    dad2 = res.getString("AD2");
                    dph = res.getString("PHONE");
                    ddist = res.getString("DISTRICT");
                    dstate = res.getString("STATE");
                    dpin = res.getString("PINCODE");
                }
            } else
                status = false;
        } catch (SQLException e) {
            System.out.println("#DB Query Error - " + e);
            System.exit(0);
        }

        JLabel basic = new JLabel("Basic Details", JLabel.LEFT);
        basic.setBounds(43, 40, 400, 40);
        basic.setFont(new Font("SansSerif", Font.PLAIN, 28));
        p.add(basic);

        JLabel lname = new JLabel("Name");
        lname.setBounds(63, 100, 100, 40);
        lname.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lname);

        name = new JTextField();
        name.setBounds(170, 105, 200, 30);
        name.setText(dname);
        p.add(name);

        JLabel lph = new JLabel("Phone");
        lph.setBounds(63, 150, 100, 40);
        lph.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lph);

        ph = new JTextField();
        ph.setBounds(170, 155, 200, 30);
        ph.setText(dph);
        p.add(ph);

        JLabel lage = new JLabel("Age");
        lage.setBounds(450, 150, 100, 40);
        lage.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lage);

        age = new JTextField();
        age.setBounds(547, 155, 50, 30);
        age.setText(dage);
        p.add(age);

        JLabel lbg = new JLabel("Group");
        lbg.setBounds(63, 200, 100, 40);
        lbg.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lbg);

        String bgs[] = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        int index = 0;
        bg = new JComboBox<String>(bgs);
        bg.setBounds(170, 205, 200, 30);
        if (!dbg.equals("")) {
            bg.setEnabled(false);
            bg.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            for (int i = 0; i < bgs.length; i++) {
                if (bgs[i].equals(dbg)) {
                    index = i;
                    break;
                }
            }
        }
        bg.setSelectedIndex(index);
        p.add(bg);
        if (status) {
            bg.setVisible(false);
            JLabel show = new JLabel(dbg);
            show.setBounds(170, 205, 200, 30);
            show.setFont(new Font("SansSerif", Font.PLAIN, 20));
            p.add(show);
        }

        JLabel address = new JLabel("Residential Details", JLabel.LEFT);
        address.setBounds(43, 260, 400, 40);
        address.setFont(new Font("SansSerif", Font.PLAIN, 28));
        p.add(address);

        JLabel lad1 = new JLabel("Address Line 1");
        lad1.setBounds(63, 320, 250, 40);
        lad1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lad1);

        ad1 = new JTextField();
        ad1.setBounds(300, 325, 483, 30);
        ad1.setText(dad1);
        p.add(ad1);

        JLabel lad2 = new JLabel("Address Line 2");
        lad2.setBounds(63, 370, 250, 40);
        lad2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lad2);

        ad2 = new JTextField();
        ad2.setBounds(300, 375, 483, 30);
        ad2.setText(dad2);
        p.add(ad2);

        JLabel ldist = new JLabel("District");
        ldist.setBounds(63, 420, 100, 40);
        ldist.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(ldist);

        String dists[] = { "Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasargod", "Kollam", "Kottayam", "Kozhikode",
                "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvanathapuram", "Thrissur", "Wayanad" };
        dist = new JComboBox<String>(dists);
        dist.setBounds(170, 425, 100, 30);
        if (!ddist.equals("")) {
            for (int i = 0; i < dists.length; i++) {
                if (dists[i].equals(ddist)) {
                    index = i;
                    break;
                }
            }
        }
        dist.setSelectedIndex(index);
        p.add(dist);

        JLabel lst = new JLabel("State");
        lst.setBounds(320, 420, 100, 40);
        lst.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lst);

        String sts[] = { "Kerala" };
        st = new JComboBox<String>(sts);
        st.setBounds(427, 425, 100, 30);
        if (!dstate.equals("")) {
            for (int i = 0; i < sts.length; i++) {
                if (sts[i].equals(dstate)) {
                    index = i;
                    break;
                }
            }
        }
        st.setSelectedIndex(index);
        p.add(st);

        JLabel lpin = new JLabel("Pin Code");
        lpin.setBounds(577, 420, 100, 40);
        lpin.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p.add(lpin);

        pin = new JTextField("XXXXXX");
        pin.setBounds(684, 425, 100, 30);
        pin.setText(dpin);
        p.add(pin);

        if (!status) {
            JCheckBox conf = new JCheckBox(
                    "I undersand, once updated Blood Group cannot be changed at any circumstances.");
            conf.setBounds(43, 490, 600, 30);
            conf.setFont(new Font("SansSerif", Font.PLAIN, 16));
            p.add(conf);
            conf.addItemListener(this);
        }

        JButton back = new JButton("BACK");
        back.setBounds(43, 555, 100, 30);
        p.add(back);
        back.addActionListener(this);

        submit = new JButton("SUBMIT");
        submit.setBounds(160, 555, 100, 30);
        if (!status)
            submit.setEnabled(false);
        p.add(submit);
        submit.addActionListener(this);

        JLabel icon = new JLabel(new ImageIcon("update.png"));
        icon.setBounds(820, 100, 450, 450);
        p.add(icon);

    }

    public boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ((JButton) e.getSource() == submit) {
            boolean flag = false;
            String dname = name.getText(), dad1 = ad1.getText(), dad2 = ad2.getText(), dpin = pin.getText(),
                    dage = age.getText(), dph = ph.getText(), ddist = (String) dist.getSelectedItem(),
                    dstate = (String) st.getSelectedItem(), dbg = (String) bg.getSelectedItem();
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
            if (!flag) {
                if (status) {
                    // Ineffecient approach
                    // Change to UPDATE
                    query = "DELETE FROM BIO WHERE USERID = '" + Main.uid + "'";
                    DB.update(query);
                }
                Formatter formatter = new Formatter();
                formatter.format("'%s','%s','%s',%s,'%s','%s',%s,'%s','%s',%s", Main.uid, dname, dbg,
                        dage, dad1, dad2, dph, ddist, dstate, dpin);
                query = "CALL INSERT_BIO_ADDRESS(" + formatter + ")";
                formatter.close();
                DB.executeProcedure(query);
                GUI.dashboard();
            }
        } else
            GUI.dashboard();
    }

    public void itemStateChanged(ItemEvent e) {
        submit.setEnabled(!submit.isEnabled());
    }
}
