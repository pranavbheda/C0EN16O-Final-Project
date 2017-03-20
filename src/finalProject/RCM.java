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

/**
 * A Recycling Machine (RCM) is similar to a vending machine and is designed to
 * accept recyclable items that are aluminum and glass products where a user is
 * paid a small amount of money for each type of item. The recycling machine is
 * equipped with an interface to display the items accepted by the machine, the
 * amount paid for each item and slot(s) to accept the items and return the
 * money to the user. These machines are installed in offices, schools,
 * hospitals and large buildings. Each Recycling Machine has a machine id,
 * location, a list of items that it can accept, price paid for each item,
 * capacity (in weight) and the total weight of items currently in the machine.
 *
 * @author Pranav
 *
 */
public class RCM extends JPanel implements ActionListener{

    // Serial Warning
    private static final long serialVersionUID = 1L;

    /* RCM Variables ------------------------------------------------------- */

    JComboBox<String> rcmValues = new JComboBox<String>();

    private final double TOTAL_RMS_MONEY = 200.00;

    private String loc, id;

    private double totalMoney, totalWeight, totalTransactions; // in pounds

    String lastEmptied = "00:00:00";

    // Allowed items to be recycled
    private ArrayList<RecyclableItem> itemsAllowed;

    // Items that have been recycled
    public ArrayList<RecycledItem> itemsRecycled;

    /* GUI Variables ------------------------------------------------------- */

    private JLabel titleLabel;
    private JPanel panelAdd, panelInfo, panelTitle;
    private JButton addButton;
    JTextArea history, item_D;

    /* Constructors -------------------------------------------------------- */

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

        // Sets RCM Variables
        this.loc = loc;
        this.id = id;
        itemsRecycled = new ArrayList<RecycledItem>();
        totalMoney = TOTAL_RMS_MONEY;
        initRecyclableItems();

        // Sets GUI Variables
        initializeGUI();
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

    /* RCM Functions ------------------------------------------------------- */

    /**
     *
     * @param str
     * @return
     */
    public int checkType(String str){
        str = str.replace(" ", "");
        str = str.toLowerCase();

        for(RecyclableItem i : itemsAllowed){
            if(str.equals(i.getType())){
                return itemsAllowed.indexOf(i);
            }
        }
        return -1; // throw error here
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
     * The RCM GUI allows a user to drop an item to be recycled in the
     * designated receptacle. The user interface shows the type of item, weight
     * of item and money given to the user. The amount of money in the machine
     * is adjusted after the price of the recycled item is given to the user. If
     * there is no money in the machine, a coupon is given to the user. The
     * total weight of the items in the machine is adjusted.
     *
     * For example, $1.00 for 2lbs of glass.
     *
     * @param recyclableItems
     */
    public void recycleItem(RecycledItem... recyclableItems){
        for(RecycledItem i : recyclableItems){
            double value = getValueOfItem(i);
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
     * Restocks the RCM with same amount of money every time.
     */
    public void restock(){
        totalMoney = TOTAL_RMS_MONEY;
    }

    /* GUI ---------------------------------------------------------------- */

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
        // setItemData();

        // Recyclable Type
        /*
         * panelType = new JPanel(); labelType = new JLabel("Type: ",
         * JLabel.LEFT); panelType.add(labelType); idType = new JTextField(10);
         * panelType.add(idType);
         *
         * // Item Weight panelWeight = new JPanel(); labelWeight = new JLabel(
         * "Weight: ", JLabel.CENTER); panelWeight.add(labelWeight); idWeight =
         * new JTextField(10); panelWeight.add(idWeight);
         *
         * // Add Button and Item Vallue /* panelAdd = new JPanel(); addButton =
         * new JButton("Recycle Item"); panelAdd.add(addButton); labelValue =
         * new JLabel(); panelAdd.add(labelValue);
         *
         */

        panelAdd = new JPanel();
        rcmValues.setVisible(true);
        setCB();
        addButton = new JButton("Recycle Item");
        panelAdd.add(rcmValues);
        panelAdd.add(addButton);

        addButton.addActionListener(this);

        // Text Area
        JPanel panelHistory = new JPanel();
        // JLabel historyTitle = new JLabel("Transactions: ");
        history = new JTextArea(5, 45);
        JScrollPane scrollPane1 = new JScrollPane(history);
        scrollPane1.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        history.setEditable(false);
        panelHistory.add(scrollPane1);

        // panelHistory.add(historyTitle);

        super.add(panelTitle);

        super.add(itemData);

        // super.add(panelType);
        // super.add(panelWeight);

        super.add(panelAdd);
        super.add(panelHistory);

        // show the frame in the center of the screen

        setVisible(true);
    }

    /* Action Listener ----------------------------------------------------- */

    @Override
    public void actionPerformed(ActionEvent e){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        Random rn = new Random();
        try{
            String type = (String) rcmValues.getSelectedItem();
            double weight = Double.valueOf(rn.nextInt(5) + 1);
            weight = Math.round(weight + rn.nextDouble() * 100.0) / 100.0;
            RecycledItem item = new RecycledItem(type, weight);
            double itemValue = getValueOfItem(item);
            recycleItem(item);
            // labelValue.setText("Item Value: " + itemValue);
            history.append(type + ", Weight: " + weight + ", Value: "
                    + itemValue + ", Time: " + dateFormat.format(date)
                    + "\r\n");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }

    }

    /* Getter and Setters -------------------------------------------------- */

    public void setItemData(){
        item_D.removeAll();
        Font f = new Font("Andale Mono 14", Font.CENTER_BASELINE, 12);
        item_D.setFont(f);

        for(RecyclableItem _ri : itemsAllowed){
            item_D.setText(String.format(_ri.getType() + " ----------> " + _ri
                    .getCost() + " $/lb" + "\r\n"));
        }
    }

    public double getTotalTransactions(){
        return totalTransactions;
    }

    public void setCB(){
        rcmValues.removeAllItems();
        item_D.setText("");
        Font f = new Font("Andale Mono 14", Font.CENTER_BASELINE, 12);
        item_D.setFont(f);

        for(RecyclableItem item : itemsAllowed){
            rcmValues.addItem(item.getType());
            item_D.append(String.format(item.getType() + " ----------> " + item
                    .getCost() + " $/lb" + "\r\n"));
        }
    }

    /**
     * @return the location of the rcm
     */
    public String getLoc(){
        return loc;
    }

    /**
     * @param loc the location to be set
     */
    public void setLoc(String loc){
        this.loc = loc;
    }

    /**
     * @return id of the rcm
     */
    public String getId(){
        return id;
    }

    /**
     * @param id the id to be set
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * @return the ArrayList of recycled items
     */
    public ArrayList<RecycledItem> getItemsRecycled(){
        return itemsRecycled;
    }

    public ArrayList<RecyclableItem> getItemsAllowed(){
        return itemsAllowed;
    }

    /**
     * @param items the ArrayList of recycled items to be set
     */
    public void setItemsRecycled(ArrayList<RecycledItem> items){
        itemsRecycled = items;
    }

    /**
     * @return the total money in the RCM
     */
    public double getTotalMoney(){
        return totalMoney;
    }

    /**
     * @param totalMoney the total amount of money to be set
     */
    public void setTotalMoney(double totalMoney){
        this.totalMoney = totalMoney;
    }

    /**
     * @return the total weight of items in the RCM
     */
    public double getTotalWeight(){
        return totalWeight;
    }

    public String getLastEmptied(){
        return lastEmptied;
    }

    public void setTotalTransactions(){
        totalTransactions = 0;
    }

    /**
     * @param totalWeight the total weight to be set
     */
    public void setTotalWeight(double totalWeight){
        this.totalWeight = totalWeight;
    }

    public boolean allowedItemExists(String type){
        for(RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                return true;
            }
        }
        return false;
    }

    public void addAllowedItem(RecyclableItem recyclableItem){
        if(!itemsAllowed.contains(recyclableItem)){
            itemsAllowed.add(recyclableItem);
        }else if(itemsAllowed.contains(recyclableItem)){
            return;
        }
    }

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

    public double getItemPrice(String type){
        for(RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                return item.getCost();
            }
        }
        return 0;
    }

    public void setItemPrice(String type, double cost){
        for(RecyclableItem item : itemsAllowed){
            if(item.getType() == type){
                item.setCost(cost);
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

    /* Print Functions ----------------------------------------------------- */
    @Override
    public String toString(){
        return getId() + " " + getLocation() + " $" + MyUtil.formatDouble(
                getTotalMoney()) + "  " + MyUtil.formatDouble(getTotalWeight())
                + " lbs";
    }

    public void printAllItems(){
        for(RecycledItem i : itemsRecycled){
            System.out.println(i);
        }
    }

}
