package com.dafttech.terra.game.world.items.inventories;

import java.util.HashMap;
import java.util.Set;
import com.dafttech.terra.game.world.items.persistence.GameObject;
import com.dafttech.terra.game.world.items.persistence.Prototype;

public class Inventory {
    HashMap<Prototype, Integer> amounts = new HashMap<Prototype, Integer>();
    
    public void add(GameObject obj, int amount) {
        if(amounts.containsKey(obj.toPrototype())) {
            amounts.put(obj.toPrototype(), amounts.get(obj) + amount);
        }
        else
        {
            amounts.put(obj.toPrototype(), amount);
        }
    }
    
    public Set<Prototype> getPrototypeSet() {
        return amounts.keySet();
    }
    
    public boolean contains(GameObject obj) {
        return contains(obj.toPrototype());
    }
    
    public boolean contains(Prototype type) {
        return getAmount(type) > 0;
    }
    
    public int getAmount(GameObject obj) {
        return getAmount(obj.toPrototype());
    }
    
    public int getAmount(Prototype type) {
        if(amounts.containsKey(type)) {
            return amounts.get(type);
        }
        return 0;
    }
}
