package finalProject;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;

class Data{
    String type;
    int count;

    public Data(String type, int count){
        this.type = type;
        this.count = count;
    }

    public int getCount(){
        return count;
    }
}

/**
 * DataManager class reads sales data from a text file and loads a data
 * structure of type Hashmap
 */
class DataManager{
    private Map<Color, Data> sales = new LinkedHashMap<Color, Data>();

    public void readFromRCM(RCM rcm){

        int plastic = 0;
        int aluminium = 0;
        int glass = 0;
        for(RecycledItem item : rcm.itemsRecycled){
            if(item.getType().equals("plastic")){
                plastic++;
            }
            if(item.getType().equals("glass")){
                glass++;
            }
            if(item.getType().equals("aluminium")){
                aluminium++;
            }
        }
        sales.put(new Color(255, 0, 0), new Data("plastic", plastic));
        sales.put(new Color(0, 255, 0), new Data("glass", glass));
        sales.put(new Color(0, 0, 255), new Data("aluminium", aluminium));
    }

    public Map<Color, Data> getData(){
        return sales;
    }
}

/**
 * Runs the graph
 *
 * @author Pranav
 *
 */
class Lab7{
    public static void main(String[] args){
        JFrame frame = new JFrame("Bar Chart");
        DataManager datamanager = new DataManager();
        datamanager.readFromRCM(null);

        BarChart chart = new BarChart(datamanager.getData());
        chart.setSize(500, 700);

        frame.setSize(600, 800);
        frame.getContentPane().add(chart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}