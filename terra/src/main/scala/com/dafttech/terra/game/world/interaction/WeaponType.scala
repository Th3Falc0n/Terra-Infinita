package com.dafttech.terra.game.world.interaction

class WeaponType {
  private[interaction] val repeatable: Skill = null
  private[interaction] val main1: Skill = null
  private[interaction] val main2: Skill = null
  private[interaction] val sub1: Skill = null
  private[interaction] val sub2: Skill = null
  private[interaction] val providesFullSet: Boolean = false
  private[interaction] val allowsSubWeapon: Boolean = true

  def getSkillForWeaponSkillSlotID(id: Int): Skill = id match {
    case 0 => repeatable
    case 1 => main1
    case 2 => main2
    case 3 => sub1
    case 4 => sub2
  }
}