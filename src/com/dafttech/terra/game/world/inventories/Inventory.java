package com.dafttech.terra.game.world.inventories;

import com.dafttech.terra.game.world.entities.items.Item;

public abstract class Inventory {
    public abstract boolean dropIn(Item i);
}
