/**
 * File: RecycledItem.java
 *
 * Description: Class for items that have been recycled.
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

public class RecycledItem{

    private String type;
    private double weight; // in pounds

    /**
     * Constructor for RecycledItem
     *
     * @param type the type to be set
     * @param weight the weight to be set
     */
    public RecycledItem(final String type, final double weight){
        setType(type);
        setWeight(weight);
    }

    /**
     * @return the type of the item
     */
    public String getType(){
        return type;
    }

    /**
     * @return the weight of the item
     */
    public double getWeight(){
        return weight;
    }

    /**
     * @param type the type to be set
     */
    public void setType(final String type){
        this.type = type;
    }

    /**
     * @param weight the weight to be set
     */
    public void setWeight(final double weight){
        this.weight = weight;
    }
}
