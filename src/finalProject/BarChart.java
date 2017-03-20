package finalProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * Class to draw a box chart.
 *
 * @author Pranav, Gurneev
 *
 */
@SuppressWarnings("serial")
public class BarChart extends JPanel{
    private Map<Color, Integer> bars = new LinkedHashMap<Color, Integer>();
    String temp;
    ArrayList<Data> data = new ArrayList<Data>();

    /**
     * Constructor for BarChart. The data parameter is set o the map of bars.
     *
     * @param data the map of data to be set
     */
    public BarChart(Map<Color, Data> a, ArrayList<RecycledItem> RI){
        data.clear();
        for(Color keyColor : a.keySet()){
            Data d = a.get(keyColor);
            data.add(d);
            bars.put(keyColor, new Integer(d.count));
        }
    }

    /**
     * Draws the graph with the Graphics Object
     */
    private void drawGraph(Graphics g){
        // determine longest bar
        int max = Integer.MIN_VALUE;
        for(Integer value : bars.values()){
            max = Math.max(max, value);
        }
        // paint bars
        int width;
        if(bars.size() == 0){
            width = (getWidth() / 1) - 2;
        }else{
            width = (getWidth() / bars.size()) - 2;
        }
        int x = 1;
        int index = 0;
        for(Color color : bars.keySet()){
            int value = bars.get(color);
            int height = (int) ((getHeight() - 5) * ((double) value / max));
            g.setColor(color);
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            g.drawRect(x, getHeight() - height, width, height);
            g.drawString(data.get(index).getType(), x, getHeight() - height
                    / 2);
            g.drawString("transactions: " + data.get(index).getCount(), x,
                    getHeight() - height / 2 + 12);
            x += (width + 2);
            index++;
        }
    }

    /**
     * Draws the title and the graph
     */
    @Override
    protected void paintComponent(Graphics gp){
        super.paintComponent(gp);
        // Cast the graphics objects to Graphics2D
        Graphics2D g = (Graphics2D) gp;
        drawGraph(g);
    }

    /**
     * Sets the size for the graph
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(bars.size() * 10 + 2, 50);
    }
}