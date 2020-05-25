package com.dafttech.terra

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

/**
  * Created by LolHens on 18.04.2015.
  */
object DesktopStarter {
  private def createConfig(title: String, width: Int, height: Int): LwjglApplicationConfiguration = {
    val config = new LwjglApplicationConfiguration()
    config.title = title
    config.width = width
    config.height = height
    config.vSyncEnabled = true
    //config.useGL30 = true
    config
  }

  def main(args: Array[String]): Unit = new LwjglApplication(TerraInfinita, createConfig("TerraInfinita", 1440, 900))
}
