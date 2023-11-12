package com.ufund.api.ufundapi.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * A class used to represent a a single need of an organization
 * @author SWEN 261 Section 3 Group 4
 */
public class Need {

    static final String STRING_FORMAT = "Need [name=%s, cost=%d, quantity= %d, type=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("cost") private int cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;

    public Need(@JsonProperty("name") String name, @JsonProperty("cost") int cost, @JsonProperty("quantity")int quantity, @JsonProperty("type") String type){
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name,cost,quantity,type);
    }   
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Need)) {
            return false;
        } else {
            Need other = (Need)obj;
            return  this.name.equals(other.getName())
                    && this.type.equals(other.getType());
        }
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

    public int decreaseQuantity(int amount){
        this.quantity = this.quantity - amount;
        return this.quantity;
    }

    public int increaseQuantity(int amount){
        this.quantity = this.quantity + amount;
        return this.quantity;
    }
}
