/**
 * File: Display.java
 *
 * Description: Display class to view graphs
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    private final RCM rcm1, rcm2;
    private final RMOS rmos;

    public Display(final String titleText){
        super(titleText);

        rmos = new RMOS();
        rcm1 = new RCM("San Jose", "1");
        rcm2 = new RCM("San Fran", "2");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        rmos.addRCM(rcm1);
        rmos.addRCM(rcm2);

        final JPanel rmosPanel = new JPanel();
        rmosPanel.add(rmos);

        final JPanel rcmPanel = new JPanel();
        rcmPanel.setLayout(new GridLayout(1, 2));
        rcmPanel.add(rcm1);
        rcmPanel.add(rcm2);

        super.add(rmosPanel);
        super.add(rcmPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(final ActionEvent e){
    }
}
