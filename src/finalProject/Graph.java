package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Graph extends JPanel implements ActionListener {

	JButton rcm1, rcm2;
	JLabel label;
	
	public Graph(){
        setSize(800, 100);
        super.setLayout(new BorderLayout());
    	super.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	JPanel t = new JPanel();
    	JLabel title = new JLabel("GRAPHS");
    	Font font = new Font("Georgia", Font.BOLD, 20);
    	title.setFont(font);
    	t.add(title);
    	
    	JPanel jp = new JPanel();
		label = new JLabel("Pick a graph to display");
		rcm1 = new JButton("View RCM 1");
		rcm2 = new JButton("View RCM 2");
		jp.add(label); jp.add(rcm1); jp.add(rcm2);
		
		rcm1.addActionListener(this);
		rcm2.addActionListener(this);
		
		
		super.add(t, BorderLayout.NORTH);
		super.add(jp, BorderLayout.CENTER); 
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
