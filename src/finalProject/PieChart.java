package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

class Slice {
   double value;
   Color color;
   public Slice(double value, Color color) {  
      this.value = value;
      this.color = color;
   }
}

@SuppressWarnings("serial")
class PieChart extends JComponent {
   Slice[] slices = { new Slice(5, Color.blue), 
   new Slice(33, Color.green),
   new Slice(20, Color.yellow), new Slice(15, Color.red) };
   
   private Map<Color, Integer> values = new LinkedHashMap<Color, Integer>();

   
   PieChart(Map<Color, Data> a, ArrayList<RecycledItem> RI) {
	   for(Color keyColor : a.keySet()){
           Data data = a.get(keyColor);
           values.put(keyColor, new Integer(data.count));
       }
	   
	   
   }
   public void paint(Graphics g) {
      drawPie((Graphics2D) g, getBounds(), slices);
   }
   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
      double total = 0.0D;
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].value;
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].value * 360 / total);
         g.setColor(slices[i].color);
         g.fillArc(area.x, area.y, area.width, area.height, 
         startAngle, arcAngle);
         g.setColor(Color.BLACK);
         //g.drawString("Hello" + area.x, startAngle , startAngle);
         curValue += slices[i].value;
      }
   }
}


