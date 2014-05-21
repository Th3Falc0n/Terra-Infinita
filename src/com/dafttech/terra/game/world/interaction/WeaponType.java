package com.dafttech.terra.game.world.interaction;

public class WeaponType {
    Skill repeatable, main1, main2, sub1, sub2;
    boolean providesFullSet = false;
    boolean allowsSubWeapon = true;

    public Skill getSkillForWeaponSkillSlotID(int id) {
        switch (id) {
        case 0:
            return repeatable;
        case 1:
            return main1;
        case 2:
            return main2;
        case 3:
            return sub1;
        case 4:
            return sub2;
        default:
            return null;
        }
    }
}
