package com.dafttech.terra.game.world.entities.items;

public interface IItemSlot {
    public boolean dropStack(Stack stack);
    public Stack getContent();
}
