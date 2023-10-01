package com.ufund;

import java.util.logging.Logger;

public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName()); 
    private InventoryDAO inventoryDAO;

    InventoryController(InventoryDAO inventoryDAO){
        this.inventoryDAO = inventoryDAO;
    }
}
