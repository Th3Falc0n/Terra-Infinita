package com.dafttech.terra.world.entities.items;

public interface IItemSlot {
    public boolean dropStack(Stack stack);
    public Stack getContent();
}
