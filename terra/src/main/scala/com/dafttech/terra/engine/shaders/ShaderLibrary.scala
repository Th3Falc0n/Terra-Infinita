package com.dafttech.terra.engine.shaders

import java.io.IOException

import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.dafttech.terra.resources.Resource

object ShaderLibrary {
  private var library = Map.empty[String, ShaderProgram]

  @throws(classOf[IOException])
  def loadShader(name: String, vertex: String, fragment: String): Unit = {
    ShaderProgram.pedantic = false

    import monix.execution.Scheduler.Implicits.global
    val vert = Resource.fromClasspath("/com/dafttech/terra/engine/shaders/" + vertex + ".vert").through(fs2.text.utf8Decode).compile.string.runSyncUnsafe()
    val frag = Resource.fromClasspath("/com/dafttech/terra/engine/shaders/" + fragment + ".frag").through(fs2.text.utf8Decode).compile.string.runSyncUnsafe()

    library = library + (name -> new ShaderProgram(vert, frag))
    getShader(name).enableVertexAttribute(ShaderProgram.COLOR_ATTRIBUTE)
    getShader(name).enableVertexAttribute(ShaderProgram.TEXCOORD_ATTRIBUTE)

    if (!getShader(name).isCompiled)
      throw new IllegalStateException("Uncompiled Shader " + name + ": " + getShader(name).getLog)
  }

  def getShader(name: String): ShaderProgram = library(name)
}