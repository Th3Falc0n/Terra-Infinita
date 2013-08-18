package com.dafttech.terra.game.world.inventories;

public class ItemOutputSlot extends ItemSlot {
    @Override
    public Stack dropStack(Stack s) {
        return s;
    }
}
