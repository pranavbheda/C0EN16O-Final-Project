/**
 * File: BarChar.java
 *
 * Description: Class to draw a box chart.
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

public class BarChart extends JPanel{

    private static final long serialVersionUID = 1L;

    private final Map<Color, Integer> bars;

    private final ArrayList<Data> data;

    /**
     * Constructor for BarChart. The data parameter is set o the map of bars.
     *
     * @param data the map of data to be set
     */
    public BarChart(final Map<Color, Data> a, final ArrayList<RecycledItem> RI){
        data = new ArrayList<Data>();
        bars = new LinkedHashMap<Color, Integer>();
        data.clear();
        for(final Color keyColor : a.keySet()){
            final Data d = a.get(keyColor);
            data.add(d);
            bars.put(keyColor, new Integer(d.getCount()));
        }
    }

    /**
     * Sets the size for the graph
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(bars.size() * 10 + 2, 50);
    }

    /**
     * Draws the graph with the Graphics Object
     */
    private void drawGraph(final Graphics g){
        // determine longest bar
        int max = Integer.MIN_VALUE;
        for(final Integer value : bars.values()){
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
        for(final Color color : bars.keySet()){
            final int value = bars.get(color);
            final int height = (int) ((getHeight() - 5) * ((double) value
                    / max));
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
    protected void paintComponent(final Graphics gp){
        super.paintComponent(gp);
        final Graphics2D g = (Graphics2D) gp;
        drawGraph(g);
    }
}