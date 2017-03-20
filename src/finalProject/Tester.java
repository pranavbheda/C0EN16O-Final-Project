package finalProject;

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
                new Display("final project");
            }
        });
    }
}