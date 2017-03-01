
/**
 * Items that are able to be recycled.
 *
 * @author Pranav
 *
 */
public class RecyclableItem{
    private String type;
    private double costPerPound;

    /**
     * Constructor for Recyclable Items
     *
     * @param type the type of item
     * @param costPerPound the cost per pound of the item
     */
    public RecyclableItem(String type, double costPerPound){
        setType(type);
        setCost(costPerPound);
    }

    /**
     * @return the type of the item
     */
    public String getType(){
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return the costPerPound of the item
     */
    public double getCost(){
        return costPerPound;
    }

    /**
     * @param costPerPound the costPerPound to set
     */
    public void setCost(double costPerPound){
        this.costPerPound = costPerPound;
    }
}
