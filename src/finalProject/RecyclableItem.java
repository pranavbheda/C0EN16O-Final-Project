/**
 * File: RecyclableItem.java Description: Items that are able to be recycled.
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

public class RecyclableItem{

    private double costPerPound;
    private String type;

    /**
     * Constructor for Recyclable Items
     *
     * @param type the type of item
     * @param costPerPound the cost per pound of the item
     */
    public RecyclableItem(final String type, final double costPerPound){
        setType(type);
        setCost(costPerPound);
    }

    /**
     * @return the costPerPound of the item
     */
    public double getCost(){
        return costPerPound;
    }

    /**
     * @return the type of the item
     */
    public String getType(){
        return type;
    }

    /**
     * @param costPerPound the costPerPound to set
     */
    public void setCost(final double costPerPound){
        this.costPerPound = costPerPound;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type){
        this.type = type;
    }
}
