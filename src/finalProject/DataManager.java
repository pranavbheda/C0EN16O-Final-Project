package finalProject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DataManager class reads sales data from a text file and loads a data
 * structure of type Hashmap
 */
public class DataManager{
    private Map<Color, Data> managedData = new LinkedHashMap<Color, Data>();
    private Map<String, Integer> values = new HashMap<String, Integer>();
    List<String> items = new ArrayList<String>();

    public void readFromRCM(RCM rcm){

        for(RecycledItem i : rcm.getItemsRecycled()){
            if(!values.containsKey(i.getType())){
                values.put(i.getType(), 1);
                items.add(i.getType());
            }else{
                values.put(i.getType(), values.get(i.getType()) + 1);
            }
        }

        for(Map.Entry<String, Integer> entry : values.entrySet()){
            managedData.put(new Color((int) (Math.random() * 0x1000000)),
                    new Data(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * @return the list of items
     */
    public List<String> getItems(){
        return items;
    }

    /**
     * @return the managed data
     */
    public Map<Color, Data> getData(){
        return managedData;
    }
}
