package com.dafttech.terra.game.world.inventories;

import java.util.Hashtable;

import com.dafttech.terra.game.world.entities.items.Item;

public abstract class Inventory {
    Hashtable<Item, Integer> amounts = new Hashtable<Item, Integer>();

    public boolean dropIn(Item i, int amount) {
        if (!canStore(i, amount)) return false;
        if (amounts.containsKey(i)) {
            amounts.put(i, amounts.get(i) + amount);

            return true;
        } else {
            amounts.put(i, amount);
            assignToNewSlot(i);

            return true;
        }
    }

    public abstract boolean canStore(Item i, int amount);

    public abstract void assignToNewSlot(Item i);

    public abstract void assignToSlot(Item i, int id);
}
