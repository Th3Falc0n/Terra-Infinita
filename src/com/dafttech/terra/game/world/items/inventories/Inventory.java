package com.dafttech.terra.game.world.items.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.persistence.GameObject;
import com.dafttech.terra.game.world.items.persistence.Prototype;

public class Inventory {
    HashMap<Prototype, List<Stack>> stacks = new HashMap<Prototype, List<Stack>>();

    public void add(Stack stack) {
        Prototype proto = stack.type;

        if (stacks.containsKey(proto)) {
            for (Stack s : stacks.get(proto)) {
                int am = ((Item) proto.toGameObject()).maxStackSize() - s.amount;
                if (am <= stack.amount) {
                    stack.amount -= am;
                    s.amount += am;
                } else {
                    am = stack.amount;
                    stack.amount -= am;
                    s.amount += am;
                }
            }
            if (stack.amount > 0) {
                stacks.get(proto).add(stack);
            }
        } else {
            stacks.put(proto, new ArrayList<Stack>());
            stacks.get(proto).add(stack);
        }
    }
    
    public List<Stack> getList() {
        LinkedList<Stack> lst = new LinkedList<Stack>();
        
        for(Prototype p : stacks.keySet()) {
            lst.addAll(stacks.get(p));
        }
        
        return lst;
    }

    public boolean remove(Stack stack) {
        if (!(stacks.containsKey(stack.type) && stacks.get(stack.type).contains(stack))) return false;
        stacks.get(stack.type).remove(stack);
        return true;
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
        int a = 0;
        if (stacks.containsKey(type)) {
            for (Stack s : stacks.get(type)) {
                a += s.amount;
            }
        }
        return a;
    }
}
