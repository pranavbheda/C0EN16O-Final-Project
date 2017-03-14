package finalProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class Display extends JFrame implements ActionListener {

	RMOS rmos = new RMOS();
	Graph g = new Graph();
	RCM rcm1 = new RCM("San Jose", "001");
	RCM rcm2 = new RCM("San Fran", "002");

	
	public Display(String titleText) {
		super(titleText);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,2,10,30));
		
		rmos.addRCM(rcm1);
		rmos.addRCM(rcm2);

		

		// add RMOS Panel
		super.getContentPane().add(rmos);
		super.getContentPane().add(g);
		super.getContentPane().add(rcm1);
		super.getContentPane().add(rcm2);
		
		
		
		
		// ADD RCM Panels
		
		
		
		
		// full screen
		setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setUndecorated(true);
		setVisible(true);

   
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}