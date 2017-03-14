package finalProject;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    
    private String[] recyItems = {"Plastic", "Aluminum", "Paper"};
    
    /* GUI Variables ----------------------------- */
    
    JLabel userLabel, passLabel, rcmID;
    JButton rcm1Total, rcm2Total;
    
    // panel to show recycle type
    JPanel changePanel;
    JLabel cp_type, cp_priceLabel, cp_newPriceLabel;
    JTextField cp_currPrice, cp_newPrice, tWeight, tMoney;
    JButton cp_add, cp_remove, goButton;
	final JComboBox<String> cb = new JComboBox<String>(recyItems);
    
    JTextField usernameTF, passwordTF;
    JButton submitButton;
    JFrame jframe;
    
    /* Hash Map for Password---------------------- */
    
   protected String _password = "password";
   Map <String, String> rcmData = new HashMap<String, String>();
   
   
   
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

    /**
     * Allow the user to log in with a user name.
     */
    public void login(){
    	initializeLoginGUI();
    	
    }
    
    public void initializeLoginGUI(){
    	JPanel panel1, panel2;
    	super.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    	super.setBorder(BorderFactory.createLineBorder(Color.black));

    	
    	panel1 = new JPanel();
    	userLabel = new JLabel("Username: ", JLabel.LEFT);
    	usernameTF = new JTextField(10);
    	passLabel = new JLabel("Password: ", JLabel.LEFT);
    	passwordTF  = new JTextField(10);
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
    
    public void actionPerformed(ActionEvent e){
		if (e.getSource() == submitButton){
			if(passwordTF.getText().equals(_password)){
				// login
				initializeRMOSGUI();
			}
			else {
				// login failed
			}
		}
		if(e.getSource() == goButton){
			String s = (String) cb.getSelectedItem();
			switch (s){
			case "Plastic":
				cp_type.setText("Plastic");
				break;
			case "Aluminum":
				cp_type.setText("Aluminum");
				break;
			case "Paper":
				cp_type.setText("Paper");
				break;
			}
			
			changePanel.revalidate();
				
		}
		
		if(e.getSource() == rcm1Total){
			rcmID.setText("RCM 1");
			tWeight.setText(MyUtil.formatDouble(stations.get(0).getTotalWeight()));
			tMoney.setText(MyUtil.formatDouble(stations.get(0).getTotalMoney()));
			updateStats();
			changePanel.revalidate();
		}
		
		if(e.getSource() == rcm2Total){
			rcmID.setText("RCM 2");
			tWeight.setText(MyUtil.formatDouble(stations.get(1).getTotalWeight()));
			tMoney.setText(MyUtil.formatDouble(stations.get(1).getTotalMoney()));
			updateStats();
			changePanel.revalidate();
		}
		
		
	}
	
		
    
    public void initializeRMOSGUI(){
    	super.removeAll();
    	super.revalidate();
    	
    	JPanel totalPanel = new JPanel();
    	totalPanel.setLayout(new GridLayout(3,0));
    	
    	JPanel tp_labels = new JPanel();
    	rcmID = new JLabel("RCM 1");
    	JLabel totalWeight = new JLabel("Total Weight: ", JLabel.LEFT);
    	tWeight = new JTextField(MyUtil.formatDouble(stations.get(0).getTotalWeight()));
    	tWeight.setEnabled(false);
    	JLabel totalMoney = new JLabel("Total Money: ", JLabel.LEFT);
    	tMoney = new JTextField(MyUtil.formatDouble(stations.get(0).getTotalMoney()));
    	tMoney.setEnabled(false);
    	
    	tp_labels.add(rcmID);
    	tp_labels.add(totalWeight);
    	tp_labels.add(tWeight);
    	tp_labels.add(totalMoney);
    	tp_labels.add(tMoney);
    	rcm1Total = new JButton("RCM1 Totals");
    	rcm2Total = new JButton("RCM2 Totals");

    	
    	JPanel tp_button = new JPanel();
    	tp_button.add(rcm1Total);
    	tp_button.add(rcm2Total);
    	
    	totalPanel.add(tp_labels);
    	totalPanel.add(tp_button);
    	rcm1Total.addActionListener(this);
    	rcm2Total.addActionListener(this);
    	
    	totalPanel.setSize(500, 100);
    	
    	super.add(totalPanel);
    	
    	
    	changePanel = new JPanel();
    	changePanel.setLayout(new FlowLayout());
    	changePanel.setVisible(true);
    	
    	JPanel recyclableTypes = new JPanel();
    	
    	JLabel cPDescription = new JLabel("Recyclable Types: ");
    	
    	recyclableTypes.add(cPDescription); 
    	
    	cb.setVisible(true);
    	recyclableTypes.add(cb);
    	
    	goButton = new JButton("VIEW");
    	recyclableTypes.add(goButton);
    	goButton.addActionListener(this);
    	
    	changePanel.add(recyclableTypes);
    	
    	JPanel r_item = new JPanel();
    	cp_type = new JLabel("Plastic: ");
    	
    	cp_priceLabel = new JLabel("Current Price: ");
    	cp_currPrice = new JTextField("100");
    	cp_currPrice.setEnabled(false);
    	
    	cp_newPriceLabel = new JLabel("New Price: ");
    	cp_newPrice = new JTextField(10);
 	
    	r_item.add(cp_type); r_item.add(cp_priceLabel); r_item.add(cp_currPrice);
    	r_item.add(cp_newPriceLabel); r_item.add(cp_newPrice);
    	
    	changePanel.add(r_item);
    	super.add(changePanel);
  
    	
    	
    }
    

    /**
     * Change/add new types of recyclable items.
     */
    public void removeType(){

    }

    /**
     * Change/add new types of recyclable items.
     */
    public void addType(){

    }
    
    /**
     * Change/add new types of recyclable items.
     */
    public void updatePrice(RCM rcm, String type, double newPrice){
        rcm.updateAllowedItem(type, newPrice);
    }
    
    
    

    /**
     * Check and display the amount of money in a specific RCM.
     */
    public void display(){

    }

    /**
     * Check and display the current (and available) capacity (by weight or
     * volume) of an RCM. This indicates whether an RCM is full and needs to be
     * emptied.
     */
    public void getAvailability(){

    }
}