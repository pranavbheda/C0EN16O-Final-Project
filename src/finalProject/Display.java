package finalProject;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JFrame implements ActionListener{

    private RMOS rmos = new RMOS();
    private RCM rcm1 = new RCM("San Jose", "1");
    private RCM rcm2 = new RCM("San Fran", "2");

    public Display(String titleText){
        super(titleText);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        rmos.addRCM(rcm1);
        rmos.addRCM(rcm2);

        JPanel rmosPanel = new JPanel();
        rmosPanel.add(rmos);

        JPanel rcmPanel = new JPanel();
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
    public void actionPerformed(ActionEvent e){
    }

}
