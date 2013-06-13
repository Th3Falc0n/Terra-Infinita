package com.dafttech.terra.graphics.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderLibrary {
    public static Map<String, ShaderProgram> library = new HashMap<String, ShaderProgram>();
    
    public static void loadShader(String name, String vertex, String fragment) throws IOException {
        ShaderProgram.pedantic = false;
        
        InputStream vertIS = ShaderLibrary.class.getResourceAsStream("/com/dafttech/terra/graphics/shaders/" + vertex + ".vert");
        InputStream fragIS = ShaderLibrary.class.getResourceAsStream("/com/dafttech/terra/graphics/shaders/" + fragment + ".frag");

        CharBuffer vertBF = CharBuffer.allocate(vertIS.available());
        CharBuffer fragBF = CharBuffer.allocate(fragIS.available());
        
        new InputStreamReader(vertIS).read(vertBF);
        new InputStreamReader(fragIS).read(fragBF);
        
        String vert = new String(vertBF.array());
        String frag = new String(fragBF.array());
        
        vertIS.close();
        fragIS.close();
        
        library.put(name, new ShaderProgram(vert, frag));
        
        getShader(name).enableVertexAttribute(ShaderProgram.COLOR_ATTRIBUTE);
        getShader(name).enableVertexAttribute(ShaderProgram.TEXCOORD_ATTRIBUTE);
        
        if(!getShader(name).isCompiled()) {
            throw new IllegalStateException("Uncompiled Shader " + name + ": " + getShader(name).getLog());
        }
    }
    
    public static ShaderProgram getShader(String name) {
        return library.get(name);
    }
}
