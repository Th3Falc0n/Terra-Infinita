package com.dafttech.terra.engine.shaders

import java.io.{IOException, InputStream, InputStreamReader}
import java.nio.CharBuffer
import java.util.{HashMap, Map}

import com.badlogic.gdx.graphics.glutils.ShaderProgram

object ShaderLibrary {
  var library: Map[String, ShaderProgram] = new HashMap[String, ShaderProgram]

  @throws(classOf[IOException])
  def loadShader(name: String, vertex: String, fragment: String) {
    ShaderProgram.pedantic = false
    val vertIS: InputStream = classOf[ShaderLibrary].getResourceAsStream("/com/dafttech/terra/engine/shaders/" + vertex + ".vert")
    val fragIS: InputStream = classOf[ShaderLibrary].getResourceAsStream("/com/dafttech/terra/engine/shaders/" + fragment + ".frag")
    val vertBF: CharBuffer = CharBuffer.allocate(vertIS.available)
    val fragBF: CharBuffer = CharBuffer.allocate(fragIS.available)
    new InputStreamReader(vertIS).read(vertBF)
    new InputStreamReader(fragIS).read(fragBF)
    val vert: String = new String(vertBF.array)
    val frag: String = new String(fragBF.array)
    vertIS.close
    fragIS.close
    library.put(name, new ShaderProgram(vert, frag))
    getShader(name).enableVertexAttribute(ShaderProgram.COLOR_ATTRIBUTE)
    getShader(name).enableVertexAttribute(ShaderProgram.TEXCOORD_ATTRIBUTE)
    if (!getShader(name).isCompiled) {
      throw new IllegalStateException("Uncompiled Shader " + name + ": " + getShader(name).getLog)
    }
  }

  def getShader(name: String): ShaderProgram = {
    return library.get(name)
  }
}