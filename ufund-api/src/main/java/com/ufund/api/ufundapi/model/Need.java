package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * A class used to represent a a single need of an organization
 * @author SWEN 261 Section 3 Group 4
 */
public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    @JsonProperty("name") private String name;
    @JsonProperty("cost") private int cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;

    public Need(String name, int cost, int quantity, String type){
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
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
