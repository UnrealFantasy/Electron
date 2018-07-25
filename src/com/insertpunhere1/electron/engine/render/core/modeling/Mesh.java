package com.insertpunhere1.electron.engine.render.core.modeling;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
    private int count;

    public Mesh(float[] vertices, float[] textures, float[] normals, int[] indices) {
        count = indices.length;

        int vaoID = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoID);

        vbo(0, 3, vertices);
        vbo(1, 2, textures);
        vbo(2, 3, normals);

        ebo(indices);
    }

    public void render() {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
    }

    private void vbo(int index, int size, float[] data) {
        int vboID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    }

    private void ebo(int[] data) {
        int eboID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    }
}
