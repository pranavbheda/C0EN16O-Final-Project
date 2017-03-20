package finalProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

/**
 * An RMOS allows administrators to monitor a group of Recycling Monitoring
 * Stations (RMOS). Each RMOS monitors a group of RCMs. An RMOS is represented
 * by GUI components to enable an administrator to do the following:
 *
 *
 *
 * @author Pranav, Gurneev
 *
 */
@SuppressWarnings("serial")
public class RMOS extends JPanel implements ActionListener{
    private ArrayList<RCM> stations;
    // private User admin;

    boolean rcm1 = true;
    /* GUI Variables ----------------------------- */

    JLabel userLabel, passLabel, rcmID, empty_time;
    JButton rcm1Total, rcm2Total, newPrice_btn, restock_btn, addBtn, removeBtn,
            rcm1Graph, rcm2Graph, RCM_btn;

    // panel to show recycle type
    JPanel changePanel;
    JLabel cp_type, cp_priceLabel, cp_newPriceLabel;
    JTextField cp_currPrice, cp_newPrice, tWeight, tMoney, empty_tf, item_add,
            item_addCost, item_addweight, RCM_ID;
    JButton cp_add, cp_remove, goButton, empty_btn, mostUsed, logout;

    JComboBox<String> cb = new JComboBox<String>();
    JComboBox<String> cb_rcms = new JComboBox<String>();

    JTextField usernameTF, passwordTF;
    JButton submitButton;
    JFrame jframe;

    /* Hash Map for Password---------------------- */

    protected String _password = "password";
    Map<String, String> rcmData = new HashMap<String, String>();

    /* Constructor ------------------------------- */

    public RMOS(){
        stations = new ArrayList<RCM>();
        login();
    }

    public void addRCM(RCM... rcm){
        for(RCM r : rcm){
            stations.add(r);
        }
    }

    public void getStats(){
        for(RCM r : stations){
            System.out.println(r.getId() + " " + r.getLoc() + " " + MyUtil
                    .formatDouble(r.getTotalWeight()) + " " + r
                            .getTotalMoney());
        }
    }

    public void updateStats(){
        for(RCM r : stations){
            r.setTotalWeight(r.getTotalWeight());
            r.setTotalMoney(r.getTotalMoney());
        }
    }

    public ArrayList<RCM> getStations(){
        return stations;
    }

    /**
     * Allow the user to log in with a user name.
     */
    public void login(){
        initializeLoginGUI();

    }

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

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == submitButton){
            if(passwordTF.getText().equals(_password)){
                // login
                initializeRMOSGUI();
            }else{
                // login failed
            }
        }
        if(e.getSource() == goButton){
            String s = (String) cb.getSelectedItem();
            s = s.toLowerCase();
            // System.out.println(stations.get(0).getItemPrice(s));

            if(rcm1){
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(0)
                        .getItemPrice(s)));
            }else if(!rcm1){
                cp_currPrice.setText(MyUtil.formatDouble(stations.get(1)
                        .getItemPrice(s)));
            }

            changePanel.revalidate();
        }

        if(e.getSource() == RCM_btn){
            String s = (String) cb_rcms.getSelectedItem();
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

        if(e.getSource() == rcm1Total){
            rcm1 = true;
            rcmID.setText("RCM 1");
            tWeight.setText(MyUtil.formatDouble(stations.get(0)
                    .getTotalWeight()));
            tMoney.setText(MyUtil.formatDouble(stations.get(0)
                    .getTotalMoney()));
            updateStats();
            changePanel.revalidate();
        }

        if(e.getSource() == rcm2Total){
            rcm1 = false;
            rcmID.setText("RCM 2");
            tWeight.setText(MyUtil.formatDouble(stations.get(1)
                    .getTotalWeight()));
            tMoney.setText(MyUtil.formatDouble(stations.get(1)
                    .getTotalMoney()));
            updateStats();
            changePanel.revalidate();
        }

        if(e.getSource() == newPrice_btn){
            /*
             * updatePrice(stations.get(0), (String) cb.getSelectedItem(),
             * Double.parseDouble(cp_newPrice.getText()));
             * cp_currPrice.setText(cp_newPrice.getText());
             * changePanel.revalidate();
             */

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

        if(e.getSource() == addBtn){

            if(rcm1){
                String s = item_add.getText();
                s = s.toLowerCase();
                System.out.println(s);

                if(!stations.get(0).allowedItemExists(s)){
                    System.out.println("False");
                    RecyclableItem i = new RecyclableItem(s, Double.parseDouble(
                            item_addCost.getText()));
                    stations.get(0).addAllowedItem(i);
                }

                cb.removeAllItems();
                for(RecyclableItem item : stations.get(0).getItemsAllowed()){
                    cb.addItem(item.getType());
                }

                changePanel.revalidate();
                stations.get(0).setCB();
            }else if(!rcm1){
                cb.removeAllItems();
                RecyclableItem i = new RecyclableItem(item_add.getText(), Double
                        .parseDouble(item_addCost.getText()));
                stations.get(1).addAllowedItem(i);
                // stations.get(1).updateAllowedItem(item_add.getText(),
                // Double.parseDouble(item_addCost.getText()));

                for(RecyclableItem item : stations.get(1).getItemsAllowed()){
                    cb.addItem(item.getType());
                }

                changePanel.revalidate();
                stations.get(1).setCB();
            }
        }

        if(e.getSource() == mostUsed){
            JOptionPane.showMessageDialog(null, "Most Used RCM: "
                    + mostUsedRCM());
        }

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
        if(e.getSource() == rcm1Graph){
            if(rcm1){
                /*
                 * JFrame frame = new JFrame(); frame.getContentPane().add(new
                 * PieChart()); frame.setSize(500, 500); frame.setVisible(true);
                 */
                graphData(stations.get(0));
            }else if(!rcm1){
                graphData(stations.get(1));
            }

        }
        if(e.getSource() == logout){
            Window w = SwingUtilities.getWindowAncestor(this);
            w.dispose();
        }
    }

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

        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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
        RCM_btn = new JButton("Get Information");
        RCM_btn.addActionListener(this);

        JPanel rcmPanel = new JPanel();
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

        empty_btn = new JButton("Empty RCM");
        restock_btn = new JButton("Restock RCM");

        JPanel tp_button = new JPanel();

        tp_button.add(empty_btn);
        tp_button.add(restock_btn);

        JPanel otherOptions = new JPanel();
        JLabel options = new JLabel(
                "--------------------------- Other Options ---------------------------");
        options.setFont(fontRCM);
        options.setForeground(Color.BLUE);
        otherOptions.add(options);

        JPanel graphPanel = new JPanel();
        rcm1Graph = new JButton("Graph Data");
        mostUsed = new JButton("Get Most Used");
        // rcm2Graph = new JButton("Graph RCM 2");
        graphPanel.add(rcm1Graph);
        graphPanel.add(mostUsed);
        // graphPanel.add(rcm2Graph);
        rcm1Graph.addActionListener(this);
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

        removeBtn = new JButton("Remove Item");
        removeBtn.addActionListener(this);
        recyclableTypes.add(removeBtn);

        changePanel.add(recyclableTypes);

        JPanel r_item = new JPanel();
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

        JPanel addPanel = new JPanel();
        JLabel itemType = new JLabel("Item Type: ");
        item_add = new JTextField(10);
        JLabel itemCost = new JLabel("Item Cost: ");
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

    public String mostUsedRCM(){
        if(stations.get(0).getTotalTransactions() >= stations.get(1)
                .getTotalTransactions()){
            return "RCM 1";
        }else{
            return "RCM 2";
        }
    }

    public void setCB(RCM rcm){
        cb.removeAllItems();
        for(RecyclableItem item : rcm.getItemsAllowed()){
            cb.addItem(item.getType());
        }
    }

    public void setRCM_CB(){
        cb_rcms.removeAllItems();
        cb_rcms.addItem("RCM 1");
        cb_rcms.addItem("RCM 2");
        /*
         * for(RCM r : stations){ cb.addItem(r.getId()); }
         */

    }

    /**
     * Change/add new types of recyclable items.
     */
    public void updatePrice(RCM rcm, String type, double newPrice){
        rcm.updateAllowedItem(type, newPrice);
    }

}
