package finalProject;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class to Test the project
 * 
 * @author Pranav, Gurneev
 *
 */
public class Tester{

    public static void main(String[] args){
        // Run the GUI codes on Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
            	//new Display("Final Project");
            	
            	new DisplayNew("final project");
            	
            	/*
                RMOS rmos = new RMOS();
                RCM rcm1 = new RCM("San Jose", "001");
                RCM rcm2 = new RCM("San Fran", "002");
                rmos.addRCM(rcm1);
                rmos.addRCM(rcm2);
                rmos.getStats();
                */
                
                /*
                JFrame frame = new JFrame("Bar Chart");
                DataManager datamanager = new DataManager();
                datamanager.readFromRCM(rcm1);

                BarChart chart = new BarChart(datamanager.getData());
                chart.setSize(500, 700);

                frame.setSize(600, 800);
                frame.getContentPane().add(chart);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                */
            }
        });
    }
}