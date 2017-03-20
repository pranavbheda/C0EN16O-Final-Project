/**
 * File: DataManager.java
 *
 * Description: DataManager class reads sales data from a text file and loads a
 * data structure of type Hashmap
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataManager{
    private final List<String> items;
    private final Map<Color, Data> managedData;
    private final Map<String, Integer> values;

    public DataManager(){
        managedData = new LinkedHashMap<Color, Data>();
        values = new HashMap<String, Integer>();
        items = new ArrayList<String>();
    }

    /**
     * @return the managed data
     */
    public Map<Color, Data> getData(){
        return managedData;
    }

    /**
     * @return the list of items
     */
    public List<String> getItems(){
        return items;
    }

    /**
     * Reads the data from teh RCM and writes it to the the map of ManagedData
     */
    public void readFromRCM(final RCM rcm){

        for(final RecycledItem i : rcm.getItemsRecycled()){
            if(!values.containsKey(i.getType())){
                values.put(i.getType(), 1);
                items.add(i.getType());
            }else{
                values.put(i.getType(), values.get(i.getType()) + 1);
            }
        }
        for(final Map.Entry<String, Integer> entry : values.entrySet()){
            managedData.put(new Color((int) (Math.random() * 0x1000000)),
                    new Data(entry.getKey(), entry.getValue()));
        }
    }
}
