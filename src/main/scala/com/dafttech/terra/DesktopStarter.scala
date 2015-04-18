package com.dafttech.terra

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

/**
 * Created by LolHens on 18.04.2015.
 */
object DesktopStarter {
  def main(args: Array[String]): Unit = new LwjglApplication(TerraInfinita.$, "TerraInfinita", 1440, 900)
}
