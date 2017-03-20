package finalProject;
public class RecycledItem{

    private String type;
    private double weight; // in pounds

    /* Constructor --------------------------------------------------------- */

    /**
     * Constructor for RecycledItem
     *
     * @param type the type to be set
     * @param weight the weight to be set
     */
    public RecycledItem(String type, double weight){
        setType(type);
        setWeight(weight);
    }

    /* Getter and Setters -------------------------------------------------- */

    /**
     * @return the type of the item
     */
    public String getType(){
        return type;
    }

    /**
     * @param type the type to be set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return the weight of the item
     */
    public double getWeight(){
        return weight;
    }

    /**
     * @param weight the weight to be set
     */
    public void setWeight(double weight){
        this.weight = weight;
    }

    /* Print Functions ----------------------------------------------------- */

    /**
     * Prints out the object. Used for debugging
     */
    @Override
    public String toString(){
        return getType() + " " + MyUtil.formatDouble(getWeight()) + " lbs";
    }
}
