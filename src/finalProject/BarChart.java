import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * Class to draw a box chart.
 *
 * @author Pranav
 *
 */
@SuppressWarnings("serial")
public class BarChart extends JPanel{
    private Map<Color, Integer> bars = new LinkedHashMap<Color, Integer>();

    /**
     * Constructor for BarChart. The data parameter is sett o the map of bars.
     *
     * @param data the map of data to be set
     */
    public BarChart(Map<Color, Data> a){
        for(Color keyColor : a.keySet()){
            Data data = a.get(keyColor);
            bars.put(keyColor, new Integer(data.count));
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
        int width = (getWidth() / bars.size()) - 2;
        int x = 1;
        for(Color color : bars.keySet()){
            int value = bars.get(color);
            int height = (int) ((getHeight() - 5) * ((double) value / max));
            g.setColor(color);
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            g.drawRect(x, getHeight() - height, width, height);
            x += (width + 2);
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