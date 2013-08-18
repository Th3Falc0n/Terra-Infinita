package com.dafttech.terra.game.world.inventories;


public class ItemSlot {
    Stack stack;

    public Stack dropStack(Stack s) {
        if (stack == null) {
            stack = s;
            return null;
        }

        if (stack.getClass().equals(s.getContent())) {
            stack.amount += s.amount;
            s.amount = stack.amount - stack.getContent().getMaxStackSize();

            if (s.amount > 0) {
                return s;
            }
            return null;
        }

        return s;
    }

    public Stack getStack() {
        return stack;
    }
}
