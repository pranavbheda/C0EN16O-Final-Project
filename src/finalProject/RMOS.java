/**
 *
 * File: RMOS.java Description: An RMOS allows administrators to monitor a group
 * of Recycling Monitoring Stations (RMOS). Each RMOS monitors a group of RCMs.
 * An RMOS is represented by GUI components to enable an administrator to do the
 * following:
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RMOS extends JPanel implements ActionListener{

    private static final String _password = "password";

    private static final long serialVersionUID = 1L;

    private final JComboBox<String> cb, cb_rcms;

    private JPanel changePanel;

    private JTextField cp_currPrice, cp_newPrice, tWeight, tMoney, empty_tf,
            item_add, item_addCost, usernameTF, passwordTF;

    private JLabel cp_type, cp_priceLabel, cp_newPriceLabel, userLabel,
            passLabel, rcmID, empty_time;

    private JButton goButton, empty_btn, mostUsed, logout, newPrice_btn,
            restock_btn, addBtn, removeBtn, rcm1Graph, viewKilo, RCM_btn,
            submitButton;

    private boolean rcm1;

    private final ArrayList<RCM> stations;

    public RMOS(){
        stations = new ArrayList<RCM>();
        login();
        rcm1 = true;
        cb = new JComboBox<String>();
        cb_rcms = new JComboBox<String>();
    }

    @Override
    public void actionPerformed(final ActionEvent e){

        if(e.getSource() == submitButton){
            if(passwordTF.getText().equals(_password)){
                // login
                initializeRMOSGUI();
            }else{
                // login failed
                JOptionPane.showMessageDialog(null,
                        "Wrong Password! Try again :) ");
            }
        }

        if(e.getSource() == goButton){
            String s = (String) cb.getSelectedItem();
            s = s.toLowerCase();

            if(rcm1){
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(0)
                        .getItemPrice(s)));
            }else if(!rcm1){
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(1)
                        .getItemPrice(s)));
            }

            changePanel.revalidate();
        }

        // View RCM button
        if(e.getSource() == RCM_btn){
            final String s = (String) cb_rcms.getSelectedItem();
            if(s == "RCM 1"){
                rcm1 = true;
                rcmID.setText(
                        "--------------------------- Info for RCM: 1 ---------------------------");
                tWeight.setText(MyUtil.formatDouble(stations.get(0)
                        .getTotalWeight()));
                tMoney.setText(MyUtil.formatDouble(stations.get(0)
                        .getTotalMoney()));
                updateStats();
                setCB(stations.get(0));
                empty_tf.setText(stations.get(0).getLastEmptied());
                changePanel.revalidate();
            }else if(s == "RCM 2"){
                rcm1 = false;
                rcmID.setText(
                        "--------------------------- Info for RCM: 2 ---------------------------");
                tWeight.setText(MyUtil.formatDouble(stations.get(1)
                        .getTotalWeight()));
                tMoney.setText(MyUtil.formatDouble(stations.get(1)
                        .getTotalMoney()));
                updateStats();
                setCB(stations.get(1));
                empty_tf.setText(stations.get(1).getLastEmptied());

                changePanel.revalidate();
            }
        }

        // change price action
        if(e.getSource() == newPrice_btn){
            if(rcm1){
                String s = (String) cb.getSelectedItem();
                s = s.toLowerCase();
                stations.get(0).setItemPrice(s, Double.parseDouble(cp_newPrice
                        .getText()));
                System.out.println(stations.get(0).getItemPrice(s));
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(0)
                        .getItemPrice(s)));
                stations.get(0).setCB();
            }else if(!rcm1){
                String s = (String) cb.getSelectedItem();
                s = s.toLowerCase();
                stations.get(1).updateAllowedItem(s, Double.parseDouble(
                        cp_newPrice.getText()));
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(1)
                        .getItemPrice(s)));
                stations.get(1).setCB();
            }

            changePanel.revalidate();
        }

        // empty action
        if(e.getSource() == empty_btn){

            if(rcm1){
                stations.get(0).emptyRCM();
                stations.get(0).setTotalTransactions();
                empty_tf.setText(stations.get(0).getLastEmptied());
                tWeight.setText(MyUtil.formatDouble(stations.get(0)
                        .getTotalWeight()));

                changePanel.revalidate();
            }else if(!rcm1){
                stations.get(1).emptyRCM();
                stations.get(1).setTotalTransactions();
                empty_tf.setText(stations.get(1).getLastEmptied());
                tWeight.setText(MyUtil.formatDouble(stations.get(1)
                        .getTotalWeight()));
                changePanel.revalidate();
            }
        }

        // restock action
        if(e.getSource() == restock_btn){
            if(rcm1){
                stations.get(0).restock();
                tMoney.setText(MyUtil.formatDouble(stations.get(0)
                        .getTotalMoney()));
                changePanel.revalidate();
            }else if(!rcm1){
                stations.get(1).restock();
                tMoney.setText(MyUtil.formatDouble(stations.get(1)
                        .getTotalMoney()));
                changePanel.revalidate();
            }
        }

        // add new item button
        if(e.getSource() == addBtn){

            if(rcm1){
                String s = item_add.getText();
                s = s.toLowerCase();
                System.out.println(s);

                if(!stations.get(0).allowedItemExists(s)){
                    System.out.println("False");
                    final RecyclableItem i = new RecyclableItem(s, Double
                            .parseDouble(item_addCost.getText()));
                    stations.get(0).addAllowedItem(i);
                }

                cb.removeAllItems();
                for(final RecyclableItem item : stations.get(0)
                        .getItemsAllowed()){
                    cb.addItem(item.getType());
                }

                changePanel.revalidate();
                stations.get(0).setCB();
            }else if(!rcm1){
                cb.removeAllItems();
                final RecyclableItem i = new RecyclableItem(item_add.getText(),
                        Double.parseDouble(item_addCost.getText()));
                stations.get(1).addAllowedItem(i);
                for(final RecyclableItem item : stations.get(1)
                        .getItemsAllowed()){
                    cb.addItem(item.getType());
                }

                changePanel.revalidate();
                stations.get(1).setCB();
            }
        }

        // most used action
        if(e.getSource() == mostUsed){
            JOptionPane.showMessageDialog(null, "Most Used RCM: "
                    + mostUsedRCM());
        }

        // remove item button action
        if(e.getSource() == removeBtn){
            if(rcm1){
                stations.get(0).removeAllowedItem((String) cb
                        .getSelectedItem());
                cb.removeItem(cb.getSelectedItem());
                changePanel.revalidate();
                stations.get(0).setCB();
            }else if(!rcm1){
                stations.get(1).removeAllowedItem((String) cb
                        .getSelectedItem());
                cb.removeItem(cb.getSelectedItem());
                changePanel.revalidate();
                stations.get(1).setCB();
            }
        }

        // graph button
        if(e.getSource() == rcm1Graph){
            if(rcm1){
                graphData(stations.get(0));
            }else if(!rcm1){
                graphData(stations.get(1));
            }

        }

        // logout button
        if(e.getSource() == logout){
            final Window w = SwingUtilities.getWindowAncestor(this);
            w.dispose();
        }

        // view in kilograms
        if(e.getSource() == viewKilo){
            double kilo;
            if(rcm1){
                kilo = stations.get(0).getTotalWeight();
            }else{
                kilo = stations.get(1).getTotalMoney();
            }

            kilo = 0.453592 * kilo;
            JOptionPane.showMessageDialog(null, "Total Weight: " + Math.round(
                    kilo * 100.0) / 100.0 + " kg");
        }
    }

    public void addRCM(final RCM... rcm){
        for(final RCM r : rcm){
            stations.add(r);
        }
    }

    public ArrayList<RCM> getStations(){
        return stations;
    }

    public void getStats(){
        for(final RCM r : stations){
            System.out.println(r.getId() + " " + r.getLoc() + " " + MyUtil
                    .formatDouble(r.getTotalWeight()) + " " + r
                            .getTotalMoney());
        }
    }

    // graph bar chart
    public void graphData(final RCM r){
        final JFrame frame = new JFrame("Bar Chart");
        frame.setLayout(new BorderLayout());
        final DataManager datamanager = new DataManager();
        datamanager.readFromRCM(r);

        final BarChart chart = new BarChart(datamanager.getData(), r
                .getItemsRecycled());
        chart.setSize(500, 700);

        frame.setSize(600, 800);
        frame.getContentPane().add(chart, BorderLayout.CENTER);

        final JPanel title = new JPanel();
        final JLabel t = new JLabel(r.getId());
        title.add(t);
        frame.getContentPane().add(title, BorderLayout.NORTH);

        final JPanel yAxis = new JPanel();
        final JLabel y = new JLabel("Transactions");
        yAxis.add(y);
        frame.getContentPane().add(yAxis, BorderLayout.WEST);

        final JPanel xAxis = new JPanel();
        final JLabel x = new JLabel("Item Types");
        xAxis.add(x);
        frame.getContentPane().add(xAxis, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Login GUI
    public void initializeLoginGUI(){
        JPanel panel1, panel2;
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.setBorder(BorderFactory.createLineBorder(Color.black));

        panel1 = new JPanel();
        userLabel = new JLabel("Username: ", JLabel.LEFT);
        usernameTF = new JTextField(10);
        passLabel = new JLabel("Password: ", JLabel.LEFT);
        passwordTF = new JTextField(10);
        panel1.add(userLabel);
        panel1.add(usernameTF);
        panel1.add(passLabel);
        panel1.add(passwordTF);

        panel2 = new JPanel();
        submitButton = new JButton("Submit");
        panel2.add(submitButton);

        submitButton.addActionListener(this);

        super.add(panel1);
        super.add(panel2);
    }

    // real RMOS gui
    public void initializeRMOSGUI(){
        super.removeAll();
        super.revalidate();
        super.setLayout(new GridLayout(1, 2));

        final JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(7, 1));

        /* RMOS TITLE ----------------------------------- */
        final JPanel rmosPanel = new JPanel();
        final Font fontRMOS = new Font("Georgia", Font.BOLD, 24);
        final Font fontRCM = new Font("Georgia", Font.CENTER_BASELINE, 16);

        final JLabel rmosLabel = new JLabel("RMOS CONTROL");
        rmosLabel.setFont(fontRMOS);

        final JPanel whichRCM = new JPanel();
        final JLabel rcmSelection = new JLabel("Get Information of RCM: ");
        cb_rcms.setVisible(true);
        setRCM_CB();
        RCM_btn = new JButton("Get Information");
        RCM_btn.addActionListener(this);

        final JPanel rcmPanel = new JPanel();
        rcmID = new JLabel(
                "--------------------------- Info for RCM: 1 ---------------------------");
        rcmID.setFont(fontRCM);
        rcmID.setForeground(Color.BLUE);

        rmosPanel.add(rmosLabel);
        whichRCM.add(rcmSelection);
        whichRCM.add(cb_rcms);
        whichRCM.add(RCM_btn);
        rcmPanel.add(rcmID);

        totalPanel.add(rmosPanel);
        totalPanel.add(whichRCM);
        totalPanel.add(rcmPanel);

        /* Info Panel ------------------------------------------------ */

        final JPanel tp_labels = new JPanel();
        final JLabel totalWeight = new JLabel("Total Weight: ", JLabel.LEFT);
        tWeight = new JTextField(MyUtil.formatDouble(stations.get(0)
                .getTotalWeight()));
        tWeight.setEnabled(false);
        final JLabel totalMoney = new JLabel("Total Money: ", JLabel.LEFT);
        tMoney = new JTextField(MyUtil.formatDouble(stations.get(0)
                .getTotalMoney()));
        tMoney.setEnabled(false);

        empty_time = new JLabel("Last Emptied: ");
        empty_tf = new JTextField(stations.get(0).getLastEmptied());
        empty_tf.setEnabled(false);

        tp_labels.add(totalWeight);
        tp_labels.add(tWeight);
        tp_labels.add(totalMoney);
        tp_labels.add(tMoney);
        tp_labels.add(empty_time);
        tp_labels.add(empty_tf);

        empty_btn = new JButton("Empty RCM");
        restock_btn = new JButton("Restock RCM");

        final JPanel tp_button = new JPanel();

        tp_button.add(empty_btn);
        tp_button.add(restock_btn);

        final JPanel otherOptions = new JPanel();
        final JLabel options = new JLabel(
                "--------------------------- Other Options ---------------------------");
        options.setFont(fontRCM);
        options.setForeground(Color.BLUE);
        otherOptions.add(options);

        final JPanel graphPanel = new JPanel();
        rcm1Graph = new JButton("Graph Data");
        mostUsed = new JButton("Get Most Used");
        viewKilo = new JButton("View Kilograms");
        // rcm2Graph = new JButton("Graph RCM 2");
        graphPanel.add(rcm1Graph);
        graphPanel.add(mostUsed);
        graphPanel.add(viewKilo);
        // graphPanel.add(rcm2Graph);
        rcm1Graph.addActionListener(this);
        viewKilo.addActionListener(this);
        // rcm2Graph.addActionListener(this);

        totalPanel.add(tp_labels);
        totalPanel.add(tp_button);
        totalPanel.add(otherOptions);
        totalPanel.add(graphPanel);

        // rcm1Total.addActionListener(this);
        // rcm2Total.addActionListener(this);
        mostUsed.addActionListener(this);
        empty_btn.addActionListener(this);
        restock_btn.addActionListener(this);

        super.add(totalPanel);

        changePanel = new JPanel();
        changePanel.setLayout(new GridLayout(5, 1));
        changePanel.setVisible(true);

        final JLabel user = new JLabel("USER: ");
        final JTextField userTF = new JTextField(15);
        userTF.setText("Gurneev");
        userTF.setEnabled(false);

        final JPanel logoutPanel = new JPanel();
        logout = new JButton("Log Out");
        logoutPanel.add(user);
        logoutPanel.add(userTF);
        logoutPanel.add(logout);
        changePanel.add(logoutPanel);
        logout.addActionListener(this);

        final JPanel typeTitlePanel = new JPanel();
        final JLabel type_heading = new JLabel(
                "--------------------------- Edit Recyclable Items ---------------------------");
        final Font fontHeading = new Font("Georgia", Font.CENTER_BASELINE, 16);
        type_heading.setFont(fontHeading);

        typeTitlePanel.add(type_heading);
        changePanel.add(typeTitlePanel);

        final JPanel recyclableTypes = new JPanel();
        final JLabel cPDescription = new JLabel("Recyclable Types: ");
        recyclableTypes.add(cPDescription);

        cb.setVisible(true);
        setCB(stations.get(0));
        recyclableTypes.add(cb);

        goButton = new JButton("VIEW");
        recyclableTypes.add(goButton);
        goButton.addActionListener(this);

        removeBtn = new JButton("Remove Item");
        removeBtn.addActionListener(this);
        recyclableTypes.add(removeBtn);

        changePanel.add(recyclableTypes);

        final JPanel r_item = new JPanel();
        cp_type = new JLabel("Plastic: ");

        cp_priceLabel = new JLabel("Current Price: ");
        cp_currPrice = new JTextField(MyUtil.formatDouble(stations.get(0)
                .getItemPrice("plastic")));
        cp_currPrice.setEnabled(false);

        cp_newPriceLabel = new JLabel("New Price: ");
        cp_newPrice = new JTextField(10);

        newPrice_btn = new JButton("Enter");
        newPrice_btn.addActionListener(this);

        r_item.add(cp_type);
        r_item.add(cp_priceLabel);
        r_item.add(cp_currPrice);
        r_item.add(cp_newPriceLabel);
        r_item.add(cp_newPrice);
        r_item.add(newPrice_btn);

        changePanel.add(r_item);

        final JPanel addPanel = new JPanel();
        final JLabel itemType = new JLabel("Item Type: ");
        item_add = new JTextField(10);
        final JLabel itemCost = new JLabel("Item Cost: ");
        item_addCost = new JTextField(5);
        addBtn = new JButton("Add Item");

        addPanel.add(itemType);
        addPanel.add(item_add);
        addPanel.add(itemCost);
        addPanel.add(item_addCost);
        addPanel.add(addBtn);
        addBtn.addActionListener(this);

        changePanel.add(addPanel);

        super.add(changePanel);

    }

    /**
     * Allow the user to log in with a user name.
     */
    public void login(){
        initializeLoginGUI();

    }

    // method to get most used RCM
    public String mostUsedRCM(){
        if(stations.get(0).getTotalTransactions() >= stations.get(1)
                .getTotalTransactions()){
            return "RCM 1";
        }else{
            return "RCM 2";
        }
    }

    // set values of combo box in rcm
    public void setCB(final RCM rcm){
        cb.removeAllItems();
        for(final RecyclableItem item : rcm.getItemsAllowed()){
            cb.addItem(item.getType());
        }
    }

    // set values of combo box in rmos
    public void setRCM_CB(){
        cb_rcms.removeAllItems();
        cb_rcms.addItem("RCM 1");
        cb_rcms.addItem("RCM 2");
    }

    /**
     * Change/add new types of recyclable items.
     */
    public void updatePrice(final RCM rcm, final String type,
            final double newPrice){
        rcm.updateAllowedItem(type, newPrice);
    }

    public void updateStats(){
        for(final RCM r : stations){
            r.setTotalWeight(r.getTotalWeight());
            r.setTotalMoney(r.getTotalMoney());
        }
    }

}
