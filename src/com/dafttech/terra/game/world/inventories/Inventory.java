package com.dafttech.terra.game.world.inventories;

import java.util.Collection;
import java.util.Hashtable;

import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.items.Item;

public abstract class Inventory {
    Hashtable<Class<? extends Item>, Integer> amounts = new Hashtable<Class<? extends Item>, Integer>();
    
    float capacity = 100;

    /**
     *     
     * @param i
     * @param amount
     * @return Die Menge die von amount überbleibt
     */
    public int dropIn(Item item, int amount) {
        Class<? extends Item> i = item.getClass();        
        
        int rest = amount - canStore(item);
        if(rest < 0) rest = 0;
        amount = amount - rest;
        if(amount == 0) return rest;
        
        if(amounts.containsKey(i)) {
            amounts.put(i, amounts.get(i) + amount);
        }
        
        return rest;
    }

    public int canStore(Item i) {
        return (int)(i.getWeight() / getRemainingCapacity());
    }
    
    public float getRemainingCapacity() {
        float used = 0;
        
        for (Class<? extends Item> key : amounts.keySet()) {
            try {
                used += ((Item)key.newInstance()).getWeight() * amounts.get(key);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
