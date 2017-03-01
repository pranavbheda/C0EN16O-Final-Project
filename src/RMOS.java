import java.util.ArrayList;

/**
 * An RMOS allows administrators to monitor a group of Recycling Monitoring
 * Stations (RMOS). Each RMOS monitors a group of RCMs. An RMOS is represented
 * by GUI components to enable an administrator to do the following:
 *
 *
 *
 * @author Pranav
 *
 */
public class RMOS{
    private ArrayList<RCM> stations;
    // private User admin;

    public RMOS(){
        stations = new ArrayList<RCM>();
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

    /**
     * Allow the user to log in with a user name.
     */
    public void login(){

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
