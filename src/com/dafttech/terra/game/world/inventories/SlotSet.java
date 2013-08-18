package com.dafttech.terra.game.world.inventories;

import java.util.ArrayList;
import java.util.List;

public class SlotSet {
    public List<ItemSlot> slots = new ArrayList<ItemSlot>();

    public SlotSet(int slotCount) {
        for (; slotCount > 0; slotCount--) {
            slots.add(new ItemSlot());
        }
    }

    public SlotSet() {

    }

    public void addSlot(ItemSlot slot) {
        slots.add(slot);
    }

    public Stack insertStack(Stack s) {
        for (ItemSlot sl : slots) {
            s = sl.dropStack(s);
            if (s == null) break;
        }
        return s;
    }
}
