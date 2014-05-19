package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.MouseSlot;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.items.persistence.Prototype;
import com.dafttech.terra.resources.Resources;

public class ElementSkill extends GUIElement {
    private String label = "";
    private Player player;
    private int skillID = 0;

    public boolean active = false;

    public ElementSkill(Vector2 p, Player pl, String l, int sid) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
        
        label = l;
        player = pl;
        skillID = sid;
    }

    public void assignInventory(Inventory inventory) {
        assignedInventory = inventory;
    }

    public void setCooldownTime(World world, float cooldownTime) {
        this.cooldownTime = world.time + cooldownTime;
    }

    @Override
    public void onClick(int button) {
        
    }

    @Override
    public void draw(AbstractScreen screen) {
        screen.batch.setColor(active ? Color.YELLOW : Color.WHITE);
        super.draw(screen);
        Vector2 p = getScreenPosition();

        screen.batch.begin();

        if (player.getSkillForID(skillID) != null) {
            player.getSkillForID(skillID).drawInventory(p, screen);

            Resources.GUI_FONT.setColor(active ? Color.YELLOW : Color.WHITE);
            
            Resources.GUI_FONT.draw(screen.batch, label, p.x, 6 + p.y);
        }

        screen.batch.end();
    }

    public void assignType(Prototype at) {
        assignedType = at;
    }

    public void assignItem(Item item) {
        assignedType = item.toPrototype();
    }

    public void assignPair(Item item, Inventory inv) {
        assignItem(item);
        assignInventory(inv);
    }
}
