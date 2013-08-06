package com.dafttech.terra.game.world.inventories;

import com.dafttech.terra.game.world.entities.items.Item;

public class Stack {
    Item content;
    int amount;

    public Item getContent() {
        return content;
    }
    
    public int getAmount() {
        return amount;
    }
}
