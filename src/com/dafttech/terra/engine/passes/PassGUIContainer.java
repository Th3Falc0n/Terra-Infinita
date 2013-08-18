package com.dafttech.terra.engine.passes;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.gui.containers.GUIContainer;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public class PassGUIContainer extends RenderingPass {
    FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    @Override
    public void applyPass(AbstractScreen screen, Entity pointOfView, World w, Object... arguments) {
        screen.batch.setShader(null);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        ((GUIContainer) arguments[0]).draw(screen);
    }

}
