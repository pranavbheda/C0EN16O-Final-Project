/**
 * File: RCM.java
 *
 * Description: A Recycling Machine (RCM) is similar to a vending machine and is
 * designed to accept recyclable items that are aluminum and glass products
 * where a user is paid a small amount of money for each type of item. The
 * recycling machine is equipped with an interface to display the items accepted
 * by the machine, the amount paid for each item and slot(s) to accept the items
 * and return the money to the user. These machines are installed in offices,
 * schools, hospitals and large buildings. Each Recycling Machine has a machine
 * id, location, a list of items that it can accept, price paid for each item,
 * capacity (in weight) and the total weight of items currently in the machine.
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class RCM extends JPanel implements ActionListener{

    // Serial Warning
    private static final long serialVersionUID = 1L;

    private static double TOTAL_RMS_MONEY = 200.00;

    private JButton addButton;

    private JTextArea history, item_D;

    private final String id, loc;

    private final ArrayList<RecyclableItem> itemsAllowed;

    private final ArrayList<RecycledItem> itemsRecycled;

    private String lastEmptied;

    private JPanel panelAdd, panelInfo, panelTitle;

    private final JComboBox<String> rcmValues;

    private JLabel titleLabel;

    private double totalMoney, totalWeight;

    /**
     * Constructor for RCM. The total money is initialized to the default final
     * value. The method initializeGui is called to build and create the frame
     * for the user to interact with.
     *
     * @param loc the location of the RCM
     * @param id the id of the RCM
     */
    public RCM(String loc, String id){
        super();
        this.loc = loc;
        this.id = id;
        itemsAllowed = new ArrayList<RecyclableItem>();
        itemsRecycled = new ArrayList<RecycledItem>();
        totalMoney = TOTAL_RMS_MONEY;
        lastEmptied = "00:00:00";
        rcmValues = new JComboBox<String>();
        initRecyclableItems();
        initializeGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Random rn = new Random();
        try{
            String type = (String) rcmValues.getSelectedItem();
            double weight = Double.valueOf(rn.nextInt(5) + 1);
            weight = Math.round(weight + rn.nextDouble() * 100.0) / 100.0;
            RecycledItem item = new RecycledItem(type, weight);
            double itemValue = getValueOfItem(item);
            recycleItem(item);
            history.append(type + ", Weight: " + weight + ", Value: "
                    + itemValue + ", Time: " + dateFormat.format(new Date())
                    + "\r\n");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }

    /**
     * Adds a new allowed Recyclable Item
     *
     * @param recyclableItem new item type of recyclable item to be added
     */
    public void addAllowedItem(RecyclableItem recyclableItem){
        if(!itemsAllowed.contains(recyclableItem)){
            itemsAllowed.add(recyclableItem);
        }
    }

    /***
     * Checks if the item to be recycled is allowed or not
     *
     * @param type the type of item to be checked
     * @return whether or not the item is in the list of recyclable items
     */
    public boolean allowedItemExists(String type){
        for(RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                return true;
            }
        }
        return false;
    }

    /**
     * @param str the string to be checked
     * @return the type of the item
     */
    public int checkType(String str){
        str = str.replace(" ", "");
        str = str.toLowerCase();

        for(RecyclableItem i : itemsAllowed){
            if(str.equals(i.getType())){
                return itemsAllowed.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * Removes all items from the RCM and resets the total weight to zero.
     */
    public void emptyRCM(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        itemsRecycled.removeAll(itemsRecycled);
        setTotalWeight(0.0);
        lastEmptied = dateFormat.format(date);
    }

    /**
     * @return id of the rcm
     */
    public String getId(){
        return id;
    }

    /**
     * Returns the items price
     *
     * @param type the type to be returned
     * @return the price of the given item
     */
    public double getItemPrice(String type){
        for(RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                return item.getCost();
            }
        }
        return -1;
    }

    /**
     * @return the list of items allowed
     */
    public ArrayList<RecyclableItem> getItemsAllowed(){
        return itemsAllowed;
    }

    /**
     * @return the ArrayList of recycled items
     */
    public ArrayList<RecycledItem> getItemsRecycled(){
        return itemsRecycled;
    }

    public String getLastEmptied(){
        return lastEmptied;
    }

    /**
     * @return the location of the rcm
     */
    public String getLoc(){
        return loc;
    }

    /**
     * @return the total money in the RCM
     */
    public double getTotalMoney(){
        return totalMoney;
    }

    public double getTotalTransactions(){
        return itemsRecycled.size();
    }

    /**
     * @return the total weight of items in the RCM
     */
    public double getTotalWeight(){
        return totalWeight;
    }

    /**
     * Gets the value of the recyclable item by using the following function:
     * Weight * COST_material
     *
     * @return the value of the item
     */
    public double getValueOfItem(RecycledItem item){
        int index = checkType(item.getType());
        if(index == -1){ // throw error here
            return -1;
        }
        return Math.round(item.getWeight() * itemsAllowed.get(index).getCost()
                * 100.0) / 100.0;
    }

    /**
     * Each RCM displays its location, id, types of items it accepts and the
     * money returned for each item.
     */
    public void initializeGUI(){
        setSize(800, 100);
        super.setLayout(new GridLayout(5, 1));
        super.setBorder(BorderFactory.createLineBorder(Color.black));

        // RCM TITLE
        panelTitle = new JPanel();
        titleLabel = new JLabel("RCM: " + getId());
        Font rcmFont = new Font("Georgia", Font.BOLD, 20);
        titleLabel.setFont(rcmFont);
        panelTitle.add(titleLabel);

        // RCM Info
        panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout());
        panelInfo.add(new JLabel("        Location: " + getLoc(),
                JLabel.CENTER));
        panelInfo.add(new JLabel("        ID: " + getId(), JLabel.CENTER));

        panelTitle.add(panelInfo);

        JPanel itemData = new JPanel();
        // JLabel historyTitle = new JLabel("Transactions: ");
        item_D = new JTextArea(5, 45);
        JScrollPane scrollPane = new JScrollPane(item_D);
        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        item_D.setEditable(false);
        itemData.add(scrollPane);

        panelAdd = new JPanel();
        rcmValues.setVisible(true);
        setCB();
        addButton = new JButton("Recycle Item");
        panelAdd.add(rcmValues);
        panelAdd.add(addButton);

        addButton.addActionListener(this);

        // Text Area
        JPanel panelHistory = new JPanel();
        history = new JTextArea(5, 45);
        JScrollPane scrollPane1 = new JScrollPane(history);
        scrollPane1.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        history.setEditable(false);
        panelHistory.add(scrollPane1);
        super.add(panelTitle);
        super.add(itemData);
        super.add(panelAdd);
        super.add(panelHistory);

        setVisible(true);
    }

    /**
     * Initializes an ArrayList of recyclable items. Default types and costs are
     * created and then are added to the list.
     */
    public void initRecyclableItems(){
        itemsAllowed.add(new RecyclableItem("plastic", 1.2));
        itemsAllowed.add(new RecyclableItem("aluminium", 2.2));
        itemsAllowed.add(new RecyclableItem("glass", 1.8));
    }

    /**
     * The RCM GUI allows a user to drop an item to be recycled in the
     * designated receptacle. The user interface shows the type of item, weight
     * of item and money given to the user. The amount of money in the machine
     * is adjusted after the price of the recycled item is given to the user. If
     * there is no money in the machine, a coupon is given to the user. The
     * total weight of the items in the machine is adjusted. For example, $1.00
     * for 2lbs of glass.
     *
     * @param recyclableItems
     */
    public void recycleItem(RecycledItem... recyclableItems){
        for(RecycledItem i : recyclableItems){
            double value = getValueOfItem(i);
            if(totalMoney > value){
                totalMoney = totalMoney - value;
                itemsRecycled.add(i);
                totalWeight += i.getWeight();
            }
        }
    }

    /**
     * Removes an object from the list of recyclable items by first searching if
     * the item exists. If the item exists in the arraylist, it is deleted.
     *
     * @param type the type to be deleted.
     * @return whether or not the item has been deleted from the arraylist.
     */
    public boolean removeAllowedItem(String type){
        for(RecyclableItem i : itemsAllowed){
            if(i.getType() == type){
                itemsAllowed.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Restocks the RCM with same amount of money every time.
     */
    public void restock(){
        totalMoney = TOTAL_RMS_MONEY;
    }

    public void setCB(){
        rcmValues.removeAllItems();
        item_D.setText("");
        Font f = new Font("Andale Mono 14", Font.CENTER_BASELINE, 12);
        item_D.setFont(f);

        for(RecyclableItem item : itemsAllowed){
            rcmValues.addItem(item.getType());
            item_D.append(String.format(item.getType() + "\t\t$" + item
                    .getCost() + "/lb" + "\r\n"));
        }
    }

    /**
     * @param totalWeight the total weight to be set
     */
    public void setTotalWeight(double totalWeight){
        this.totalWeight = totalWeight;
    }

    /**
     * Updates an item
     *
     * @param type the type to be updated
     * @param cost the cost to be updated
     */
    public void updateAllowedItem(String type, double cost){
        RecyclableItem recyclableItem = new RecyclableItem(type, cost);
        if(!itemsAllowed.contains(recyclableItem)){
            addAllowedItem(recyclableItem);
        }else{
            for(RecyclableItem item : itemsAllowed){
                if(item.getType() == recyclableItem.getType()){
                    item.setCost(recyclableItem.getCost());
                }
            }
        }
    }
}