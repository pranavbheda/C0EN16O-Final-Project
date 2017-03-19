package finalProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class DisplayNew extends JFrame implements ActionListener {

	RMOS rmos = new RMOS();
	Graph g = new Graph();
	RCM rcm1 = new RCM("San Jose", "1");
	RCM rcm2 = new RCM("San Fran", "2");
	
	public DisplayNew(String titleText) {
		super(titleText);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		
		rmos.addRCM(rcm1);	
		rmos.addRCM(rcm2);
		
		
		
		JPanel rmosPanel = new JPanel();
		rmosPanel.add(rmos);
		
		JPanel rcmPanel = new JPanel();
		rcmPanel.setLayout(new GridLayout(1,2));
		rcmPanel.add(rcm1); rcmPanel.add(rcm2);
		
		super.add(rmosPanel); super.add(rcmPanel);
		

		
	

		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
        setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
