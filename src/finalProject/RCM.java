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

    private static final double TOTAL_RMS_MONEY = 200.00;

    private JButton addButton;

    private JTextArea history, item_D;

    private ArrayList<RecyclableItem> itemsAllowed;

    private ArrayList<RecycledItem> itemsRecycled;

    private String lastEmptied, loc, id;

    private JPanel panelAdd, panelInfo, panelTitle;

    private final JComboBox<String> rcmValues;

    private JLabel titleLabel;

    private double totalMoney, totalWeight, totalTransactions; // in pounds

    /**
     * Constructor for RCM. The total money is initialized to the default final
     * value. The method initializeGui is called to build and create the frame
     * for the user to interact with.
     *
     * @param loc the location of the RCM
     * @param id the id of the RCM
     */
    public RCM(final String loc, final String id){
        super();

        // Sets RCM Variables
        this.loc = loc;
        this.id = id;
        itemsRecycled = new ArrayList<RecycledItem>();
        totalMoney = TOTAL_RMS_MONEY;
        lastEmptied = "00:00:00";
        rcmValues = new JComboBox<String>();
        initRecyclableItems();

        // Sets GUI Variables
        initializeGUI();
    }

    @Override
    public void actionPerformed(final ActionEvent e){
        final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        final Date date = new Date();
        final Random rn = new Random();
        try{
            final String type = (String) rcmValues.getSelectedItem();
            double weight = Double.valueOf(rn.nextInt(5) + 1);
            weight = Math.round(weight + rn.nextDouble() * 100.0) / 100.0;
            final RecycledItem item = new RecycledItem(type, weight);
            final double itemValue = getValueOfItem(item);
            recycleItem(item);
            // labelValue.setText("Item Value: " + itemValue);
            history.append(type + ", Weight: " + weight + ", Value: "
                    + itemValue + ", Time: " + dateFormat.format(date)
                    + "\r\n");
        }catch(final NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }

    /**
     * Adds a new allowed Recyclable Item
     *
     * @param recyclableItem new item type of recyclable item to be added
     */
    public void addAllowedItem(final RecyclableItem recyclableItem){
        if(!itemsAllowed.contains(recyclableItem)){
            itemsAllowed.add(recyclableItem);
        }else if(itemsAllowed.contains(recyclableItem)){
            return;
        }
    }

    /***
     * Checks if the item to be recycled is allowed or not
     *
     * @param type the type of item to be checked
     * @return whether or not the item is in the list of recycable items
     */
    public boolean allowedItemExists(final String type){
        for(final RecyclableItem item : itemsAllowed){
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

        for(final RecyclableItem i : itemsAllowed){
            if(str.equals(i.getType())){
                return itemsAllowed.indexOf(i);
            }
        }
        return -1; // throw error here
    }

    /**
     * Removes all items from the RCM and resets the total weight to zero.
     */
    public void emptyRCM(){
        final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        final Date date = new Date();
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
    public double getItemPrice(final String type){
        for(final RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                return item.getCost();
            }
        }
        return 0;
    }

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
        return totalTransactions;
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
    public double getValueOfItem(final RecycledItem item){
        final int index = checkType(item.getType());
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
        final Font rcmFont = new Font("Georgia", Font.BOLD, 20);
        titleLabel.setFont(rcmFont);
        panelTitle.add(titleLabel);

        // RCM Info
        panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout());
        panelInfo.add(new JLabel("        Location: " + getLoc(),
                JLabel.CENTER));
        panelInfo.add(new JLabel("        ID: " + getId(), JLabel.CENTER));

        panelTitle.add(panelInfo);

        final JPanel itemData = new JPanel();
        // JLabel historyTitle = new JLabel("Transactions: ");
        item_D = new JTextArea(5, 45);
        final JScrollPane scrollPane = new JScrollPane(item_D);
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
        final JPanel panelHistory = new JPanel();
        history = new JTextArea(5, 45);
        final JScrollPane scrollPane1 = new JScrollPane(history);
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
        itemsAllowed = new ArrayList<RecyclableItem>();
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
    public void recycleItem(final RecycledItem... recyclableItems){
        for(final RecycledItem i : recyclableItems){
            final double value = getValueOfItem(i);
            // Checks if there is enough funds in the machine before adding the
            // item
            if(totalMoney > value){
                totalMoney = totalMoney - value;
                itemsRecycled.add(i);
                totalWeight += i.getWeight();
                totalTransactions++;
            }else{
                // TO DO ; giveCoupon();
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
    public boolean removeAllowedItem(final String type){
        for(final RecyclableItem i : itemsAllowed){
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
        final Font f = new Font("Andale Mono 14", Font.CENTER_BASELINE, 12);
        item_D.setFont(f);

        for(final RecyclableItem item : itemsAllowed){
            rcmValues.addItem(item.getType());
            item_D.append(String.format(item.getType() + " ----------> " + item
                    .getCost() + " $/lb" + "\r\n"));
        }
    }

    /**
     * @param id the id to be set
     */
    public void setId(final String id){
        this.id = id;
    }

    public void setItemData(){
        item_D.removeAll();
        final Font f = new Font("Andale Mono 14", Font.CENTER_BASELINE, 12);
        item_D.setFont(f);

        for(final RecyclableItem _ri : itemsAllowed){
            item_D.setText(String.format(_ri.getType() + " ----------> " + _ri
                    .getCost() + " $/lb" + "\r\n"));
        }
    }

    /**
     * Sets the new item's price with the given parameters
     *
     * @param type the type of item to be set
     * @param cost the new cost of the item
     */
    public void setItemPrice(final String type, final double cost){
        for(final RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                item.setCost(cost);
            }
        }
    }

    /**
     * @param items the ArrayList of recycled items to be set
     */
    public void setItemsRecycled(final ArrayList<RecycledItem> items){
        itemsRecycled = items;
    }

    /**
     * @param loc the location to be set
     */
    public void setLoc(final String loc){
        this.loc = loc;
    }

    /**
     * @param totalMoney the total amount of money to be set
     */
    public void setTotalMoney(final double totalMoney){
        this.totalMoney = totalMoney;
    }

    public void setTotalTransactions(){
        totalTransactions = 0;
    }

    /**
     * @param totalWeight the total weight to be set
     */
    public void setTotalWeight(final double totalWeight){
        this.totalWeight = totalWeight;
    }

    /**
     * Updates an item
     *
     * @param type the type to be updated
     * @param cost the cost to be updated
     */
    public void updateAllowedItem(final String type, final double cost){
        final RecyclableItem recyclableItem = new RecyclableItem(type, cost);
        if(!itemsAllowed.contains(recyclableItem)){
            addAllowedItem(recyclableItem);
        }else{
            for(final RecyclableItem item : itemsAllowed){
                if(item.getType() == recyclableItem.getType()){
                    item.setCost(recyclableItem.getCost());
                }
            }
        }
    }
}