package com.dafttech.terra

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration, UnsafeLwjglGraphics}

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
    config
  }

  private def lwjglApplication(listener: ApplicationListener, config: LwjglApplicationConfiguration) = {
    new LwjglApplication(listener, config, new UnsafeLwjglGraphics(config))
  }

  def main(args: Array[String]): Unit = lwjglApplication(TerraInfinita, createConfig("TerraInfinita", 1440, 900))
}
