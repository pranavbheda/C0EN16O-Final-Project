package finalProject;

public class Data{
    String type;
    int count;

    /**
     * Constructor for Data
     *
     * @param type
     * @param count
     */
    public Data(String type, int count){
        this.type = type;
        this.count = count;
    }

    /**
     * @return the count of the data
     */
    public int getCount(){
        return count;
    }

    /**
     * @return the type of data
     */
    public String getType(){
        return type;
    }
}