package com.insertpunhere1.electron.engine.render;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {
    private int textureID;

    private int width, height;

    public Texture(String file) {
        textureID = generate((decode(file)));
    }

    private ByteBuffer decode(String file) {
        try {
            PNGDecoder decoder = new PNGDecoder(new FileInputStream(new File(file)));

            width = decoder.getWidth();
            height = decoder.getHeight();

            ByteBuffer buffer = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);

            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

            buffer.flip();

            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int generate(ByteBuffer data) {
        int textureID = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        return textureID;
    }

    void start() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    void stop() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
