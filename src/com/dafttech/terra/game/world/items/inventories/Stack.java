package com.dafttech.terra.game.world.items.inventories;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.persistence.Prototype;

public class Stack {
    public Prototype type;
    public int amount;
    
    public Stack(Item i, int a) {
        type = i.toPrototype();
        amount = a;
    }
    
    public Stack(Prototype p, int a) {
        type = p;
        amount = a;
    }

    public boolean use(EntityLiving causer, Vector2 position) {
        Item uit = (Item)type.toGameObject();
        
        if(amount >= uit.getUsedItemNum(causer, position)) {
            if(uit.use(causer, position)) {
                amount -= uit.getUsedItemNum(causer, position);
                return true;
            }
        }
        return false;
    }
}
