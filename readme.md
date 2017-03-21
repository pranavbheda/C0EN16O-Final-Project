Pranav Bheda and Gurneev Sareen
COEN 160 Object Oriented Programming and Analysis
Final Project

Description: EcoRe is a company that wants to promote and market products for recycling of material.
The company wants to design and manufacture, an EcoRecycle system, consisting of recycling machines and recycling stations which monitor the recycling machines.

# RCM


## Description

A Recycling machine (RCM) is similar to a vending machine and is designed to accept recyclable items that are aluminum and glass products where a user is paid a small amount of money for
each type of item. The recycling machine is equipped with an interface to display the items accepted by the machine, the amount paid for each item and slot(s) to accept the items and return the money to the user. These machines are installed in offices, schools, hospitals and large buildings.
Each Recycling Machine has a machine id, location, a list of items that it can accept, price paid for each item, capacity (in weight) and the total weight of items currently in the machine.

## Display and Functionality 

 - Each RCM displays its location, id, types of items it accepts and the money returned for each item. Note: Each RCM when created starts with no items in it and a specific amount of money (for example, $100).
 - Recycling an item: The RCM GUI allows a user to drop an item to be recycled in the designated receptacle. The user interface shows the type of item, weight of item and money given to the user. The amount of money in the machine is adjusted after the price of the recycled item is given to the user. If there is no money in the machine, a coupon is given to the user (the coupon is redeemable in the designated stores). The total weight of the items in the machine is adjusted. Note: As a developer, choose and set a price for each of the item types, by their weight. For example, $1.00 for 2lbs of glass.
 - When the user has many items to recycle, he/she may indicate the start of the session (with a button click, for example, drop the items and indicate the end of a session (with a button click, for example). Money/coupon will be returned for the value of all items returned within that session.
 - RCM does not allow an item if it is full (capacity to hold the items has been reached).
 - Emptying the RCM: RCM allows an automatic (not through the GUI) emptying of the machine when the command is issued by its monitoring station (RMOS).
 - Stocking money in the RCM: RCM allows an automatic (not through the GUI) restocking of the machine with money (same fixed amount every time) when the command is issued by its monitoring station (RMOS).




# RMOS 

## Description 

Recycling Monitoring Station (RMOS) is a software program that runs on a workstation that is connected to monitor typically ten different recycling machines within a radius of
one mile. It is used to activate each recycling machine in the group (it monitors) to accept items; and 

RMOS keeps track of the status of each individual recycling machine. The status includes the current weight of recycled items in the machine and amount of money in the machine. RMOS updates the capabilities of the RCMs – this may include changing or adding new types of recyclable items and changing the price for each item. RMOS collects statistical information about usage of the RCMs it monitors. This may include the no. of times the machine was emptied in a specific duration (in no. of hours), weight of items collected in a specific duration, no. of items collected by type (aluminum, glass and so on).


## Display and Functionality 

 - Allow the user to log in with a user name. Change/add new types of recyclable items.
 - Change the price of an item.
 - Check and display the amount of money in a specific RCM.
 - Check and display the current (and available) capacity (by weight or volume) of an RCM. This indicates whether an RCM is full and needs to be emptied.
