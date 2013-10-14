package com.dafttech.terra.game.world.items.inventories;

import java.util.ArrayList;
import java.util.List;

import com.dafttech.terra.game.world.items.Item;

public abstract class Inventory {
    List<Item> items = new ArrayList<Item>();

    float capacity = 100;

    /**
     * 
     * @param i
     * @param amount
     * @return Die Menge die von amount überbleibt
     */
    public Inventory add(Inventory inventory) {
        inventory.items = add(inventory.items);
        return inventory;
    }

    public List<Item> add(List<Item> items) {
        List<Item> remainingItems = new ArrayList<Item>();
        for (Item item : items) {
            if (!add(item)) remainingItems.add(item);
        }
        return remainingItems;
    }

    public boolean add(Item item) {
        if (isSpaceFor(item)) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean isSpaceFor(Item item) {
        float weight = 0;
        for (Item inventoryItem : items) {
            weight += inventoryItem.getWeight();
            if (!inventoryItem.canBeStackedWith(item) || !item.canBeStackedWith(inventoryItem)) return false;
        }
        if (weight + item.getWeight() > capacity) return false;
        return true;
    }

    public float getRemainingCapacity() {
        float weight = 0;
        for (Item inventoryItem : items) {
            weight += inventoryItem.getWeight();
        }
        return capacity - weight;
    }
}
