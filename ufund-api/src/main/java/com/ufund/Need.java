package com.ufund;

public class Need {
    private int cost;
    private int quantity;
    private String type;

    Need(int cost, int quantity, String type){
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
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
