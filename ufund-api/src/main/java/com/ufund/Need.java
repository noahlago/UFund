package com.ufund;
/*
 * A class used to represent a a single need of an organization
 * @author SWEN 261 Section 3 Group 4
 */
public class Need {
    private String name;
    private int cost;
    private int quantity;
    private String type;

    Need(String name, int cost, int quantity, String type){
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getType() {
        return type;
    }

}
