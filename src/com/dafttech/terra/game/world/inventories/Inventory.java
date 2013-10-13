package com.dafttech.terra.game.world.inventories;

import java.util.Hashtable;

import com.dafttech.terra.game.world.entities.EntityItem;

public abstract class Inventory {
    Hashtable<EntityItem, Integer> amounts = new Hashtable<EntityItem, Integer>();

    public boolean dropIn(EntityItem i, int amount) {
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

    public abstract boolean canStore(EntityItem i, int amount);

    public abstract void assignToNewSlot(EntityItem i);

    public abstract void assignToSlot(EntityItem i, int id);
}
