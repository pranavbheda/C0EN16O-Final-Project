/**
 * File: RMOS.java
 *
 * Description: An RMOS allows administrators to monitor a group of Recycling
 * Monitoring Stations (RMOS). Each RMOS monitors a group of RCMs. An RMOS is
 * represented by GUI components to enable an administrator to do the following:
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

    private static final String PASSWORD = "password";

    private static final long serialVersionUID = 1L;

    private final JComboBox<String> cb, cb_rcms;

    private JPanel changePanel;

    private JTextField cp_currPrice, cp_newPrice, tWeight, tMoney, empty_tf,
            item_add, item_addCost, usernameTF, passwordTF;

    private JLabel cp_type, cp_priceLabel, cp_newPriceLabel, userLabel,
            passLabel, rcmID, empty_time;

    private JButton goButton, emptyButton, mostUsed, logout, newPriceButton,
            restockButton, addButton, removeButton, rcm1Graph, viewKilo,
            statsButton, submitButton;

    private boolean rcm1;

    private final ArrayList<RCM> stations;

    public RMOS(){
        stations = new ArrayList<RCM>();
        initializeLoginGUI();
        rcm1 = true;
        cb = new JComboBox<String>();
        cb_rcms = new JComboBox<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e){

        int rcm = getWhichRCM();

        if(e.getSource() == submitButton){
            if(passwordTF.getText().equals(PASSWORD)){
                // login
                initializeRMOSGUI();
            }else{
                // login failed
                JOptionPane.showMessageDialog(null,
                        "Wrong Password! Try again");
            }
        }

        if(e.getSource() == goButton){
            String s = ((String) cb.getSelectedItem()).toLowerCase();
            cp_currPrice.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getItemPrice(s)));
            changePanel.revalidate();
        }

        // View RCM button
        if(e.getSource() == statsButton){
            String s = (String) cb_rcms.getSelectedItem();
            if(s == "RCM 1"){
                rcm1 = true;
            }else if(s == "RCM 2"){
                rcm1 = false;
            }
            String rcmId;
            if(rcm == 0){
                rcmId = "1";
            }else{
                rcmId = "2";
            }
            rcmID.setText("--------------------------- Info for RCM:  " + rcmId
                    + 1 + " ---------------------------");
            tWeight.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getTotalWeight()));
            tMoney.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getTotalMoney()));
            // updateStats();
            setCB(stations.get(rcm));
            empty_tf.setText(stations.get(rcm).getLastEmptied());
            changePanel.revalidate();
        }

        // change price action
        if(e.getSource() == newPriceButton){
            String s = (String) cb.getSelectedItem();
            s = s.toLowerCase();
            stations.get(rcm).updateAllowedItem(s, Double.parseDouble(
                    cp_newPrice.getText()));
            cp_currPrice.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getItemPrice(s)));
            stations.get(rcm).setCB();
            changePanel.revalidate();
        }

        // empty action
        if(e.getSource() == emptyButton){
            stations.get(rcm).emptyRCM();
            empty_tf.setText(stations.get(rcm).getLastEmptied());
            tWeight.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getTotalWeight()));
            changePanel.revalidate();
        }

        // restock action
        if(e.getSource() == restockButton){
            stations.get(rcm).restock();
            tMoney.setText(MyUtil.formatDouble(stations.get(rcm)
                    .getTotalMoney()));
            changePanel.revalidate();
        }

        // add new item button
        if(e.getSource() == addButton){
            double newCost = Double.parseDouble(item_addCost.getText());
            String newType = item_add.getText().toLowerCase();

            if(!stations.get(rcm).allowedItemExists(newType)){
                stations.get(rcm).addAllowedItem(new RecyclableItem(newType,
                        newCost));
            }
            cb.removeAllItems();
            for(RecyclableItem item : stations.get(0).getItemsAllowed()){
                cb.addItem(item.getType());
            }
            changePanel.revalidate();
            stations.get(1).setCB();
        }

        // most used action
        if(e.getSource() == mostUsed){
            JOptionPane.showMessageDialog(null, "Most Used RCM: "
                    + mostUsedRCM());
        }

        // remove item button action
        if(e.getSource() == removeButton){
            stations.get(rcm).removeAllowedItem((String) cb.getSelectedItem());
            cb.removeItem(cb.getSelectedItem());
            changePanel.revalidate();
            stations.get(rcm).setCB();

        }

        // graph button
        if(e.getSource() == rcm1Graph){
            graphData(stations.get(rcm));
        }

        // logout button
        if(e.getSource() == logout){
            Window w = SwingUtilities.getWindowAncestor(this);
            w.dispose();
        }

        // view in kilograms
        if(e.getSource() == viewKilo){
            double kilo = stations.get(rcm).getTotalWeight();
            kilo *= 0.453592;

            JOptionPane.showMessageDialog(null, "Total Weight: " + Math.round(
                    kilo * 100.0) / 100.0 + " kg");
        }
    }

    public void addRCM(RCM... rcm){
        for(RCM r : rcm){
            stations.add(r);
        }
    }

    // graph bar chart
    public void graphData(RCM r){
        JFrame frame = new JFrame("Bar Chart");
        frame.setLayout(new BorderLayout());
        DataManager datamanager = new DataManager();
        datamanager.readFromRCM(r);

        BarChart chart = new BarChart(datamanager.getData(), r
                .getItemsRecycled());
        chart.setSize(500, 700);

        frame.setSize(600, 800);
        frame.getContentPane().add(chart, BorderLayout.CENTER);

        JPanel title = new JPanel();
        JLabel t = new JLabel(r.getId());
        title.add(t);
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel yAxis = new JPanel();
        JLabel y = new JLabel("Transactions");
        yAxis.add(y);
        frame.getContentPane().add(yAxis, BorderLayout.WEST);

        JPanel xAxis = new JPanel();
        JLabel x = new JLabel("Item Types");
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

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(7, 1));

        /* RMOS TITLE ----------------------------------- */
        JPanel rmosPanel = new JPanel();
        Font fontRMOS = new Font("Georgia", Font.BOLD, 24);
        Font fontRCM = new Font("Georgia", Font.CENTER_BASELINE, 16);

        JLabel rmosLabel = new JLabel("RMOS CONTROL");
        rmosLabel.setFont(fontRMOS);

        JPanel whichRCM = new JPanel();
        JLabel rcmSelection = new JLabel("Get Information of RCM: ");
        cb_rcms.setVisible(true);
        setRCM_CB();
        statsButton = new JButton("Get Information");
        statsButton.addActionListener(this);

        JPanel rcmPanel = new JPanel();
        rcmID = new JLabel(
                "--------------------------- Info for RCM: 1 ---------------------------");
        rcmID.setFont(fontRCM);
        rcmID.setForeground(Color.BLUE);

        rmosPanel.add(rmosLabel);
        whichRCM.add(rcmSelection);
        whichRCM.add(cb_rcms);
        whichRCM.add(statsButton);
        rcmPanel.add(rcmID);

        totalPanel.add(rmosPanel);
        totalPanel.add(whichRCM);
        totalPanel.add(rcmPanel);

        /* Info Panel ------------------------------------------------ */

        JPanel tp_labels = new JPanel();
        JLabel totalWeight = new JLabel("Total Weight: ", JLabel.LEFT);
        tWeight = new JTextField(MyUtil.formatDouble(stations.get(0)
                .getTotalWeight()));
        tWeight.setEnabled(false);
        JLabel totalMoney = new JLabel("Total Money: ", JLabel.LEFT);
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

        emptyButton = new JButton("Empty RCM");
        restockButton = new JButton("Restock RCM");

        JPanel tp_button = new JPanel();

        tp_button.add(emptyButton);
        tp_button.add(restockButton);

        JPanel otherOptions = new JPanel();
        JLabel options = new JLabel(
                "--------------------------- Other Options ---------------------------");
        options.setFont(fontRCM);
        options.setForeground(Color.BLUE);
        otherOptions.add(options);

        JPanel graphPanel = new JPanel();
        rcm1Graph = new JButton("Graph Data");
        mostUsed = new JButton("Get Most Used");
        viewKilo = new JButton("View Kilograms");
        graphPanel.add(rcm1Graph);
        graphPanel.add(mostUsed);
        graphPanel.add(viewKilo);
        rcm1Graph.addActionListener(this);
        viewKilo.addActionListener(this);

        totalPanel.add(tp_labels);
        totalPanel.add(tp_button);
        totalPanel.add(otherOptions);
        totalPanel.add(graphPanel);

        mostUsed.addActionListener(this);
        emptyButton.addActionListener(this);
        restockButton.addActionListener(this);

        super.add(totalPanel);

        changePanel = new JPanel();
        changePanel.setLayout(new GridLayout(5, 1));
        changePanel.setVisible(true);

        JLabel user = new JLabel("USER: ");
        JTextField userTF = new JTextField(15);
        userTF.setText("Gurneev");
        userTF.setEnabled(false);

        JPanel logoutPanel = new JPanel();
        logout = new JButton("Log Out");
        logoutPanel.add(user);
        logoutPanel.add(userTF);
        logoutPanel.add(logout);
        changePanel.add(logoutPanel);
        logout.addActionListener(this);

        JPanel typeTitlePanel = new JPanel();
        JLabel type_heading = new JLabel(
                "--------------------------- Edit Recyclable Items ---------------------------");
        Font fontHeading = new Font("Georgia", Font.CENTER_BASELINE, 16);
        type_heading.setFont(fontHeading);

        typeTitlePanel.add(type_heading);
        changePanel.add(typeTitlePanel);

        JPanel recyclableTypes = new JPanel();
        JLabel cPDescription = new JLabel("Recyclable Types: ");
        recyclableTypes.add(cPDescription);

        cb.setVisible(true);
        setCB(stations.get(0));
        recyclableTypes.add(cb);

        goButton = new JButton("VIEW");
        recyclableTypes.add(goButton);
        goButton.addActionListener(this);

        removeButton = new JButton("Remove Item");
        removeButton.addActionListener(this);
        recyclableTypes.add(removeButton);

        changePanel.add(recyclableTypes);

        JPanel r_item = new JPanel();
        cp_type = new JLabel("Plastic: ");

        cp_priceLabel = new JLabel("Current Price: ");
        cp_currPrice = new JTextField(MyUtil.formatDouble(stations.get(0)
                .getItemPrice("plastic")));
        cp_currPrice.setEnabled(false);

        cp_newPriceLabel = new JLabel("New Price: ");
        cp_newPrice = new JTextField(10);

        newPriceButton = new JButton("Enter");
        newPriceButton.addActionListener(this);

        r_item.add(cp_type);
        r_item.add(cp_priceLabel);
        r_item.add(cp_currPrice);
        r_item.add(cp_newPriceLabel);
        r_item.add(cp_newPrice);
        r_item.add(newPriceButton);

        changePanel.add(r_item);

        JPanel addPanel = new JPanel();
        JLabel itemType = new JLabel("Item Type: ");
        item_add = new JTextField(10);
        JLabel itemCost = new JLabel("Item Cost: ");
        item_addCost = new JTextField(5);
        addButton = new JButton("Add Item");

        addPanel.add(itemType);
        addPanel.add(item_add);
        addPanel.add(itemCost);
        addPanel.add(item_addCost);
        addPanel.add(addButton);
        addButton.addActionListener(this);

        changePanel.add(addPanel);

        super.add(changePanel);

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
    public void setCB(RCM rcm){
        cb.removeAllItems();
        for(RecyclableItem item : rcm.getItemsAllowed()){
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
     * @return the value of RCM being used
     */
    private int getWhichRCM(){
        if(rcm1 == true){
            return 0;
        }else{
            return 1;
        }
    }
}