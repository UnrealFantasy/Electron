package com.insertpunhere1.electron.engine.render.terrain;

import com.insertpunhere1.electron.engine.render.core.Shader;
import com.insertpunhere1.electron.engine.render.core.modeling.Mesh;
import com.insertpunhere1.electron.engine.render.core.modeling.Texture;

import com.insertpunhere1.electron.engine.render.entities.Entity;
import org.joml.SimplexNoise;
import org.joml.Vector3f;

public class Terrain {
    private static final float SIZE = 1024;
    private static final int VERTICES = 512;

    private float x, z;

    private Texture texture;

    private com.insertpunhere1.electron.engine.render.entities.Object object;

    public Terrain(Texture texture, int offsetX, int offsetZ) {
        x = offsetX * VERTICES;
        z = offsetZ * VERTICES;

        object = new com.insertpunhere1.electron.engine.render.entities.Object(generate(), texture);
    }

    public void render(Shader shader) {
        Entity entity = new Entity(new Vector3f(x, 0, z), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), object);

        entity.render(shader);
    }

    private Mesh generate() {
        int count = VERTICES * VERTICES;

        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textures = new float[count * 2];

        int[] indices = new int[6 * (VERTICES - 1) * (VERTICES - 1)];

        int vertices_pointer = 0;

        for (int x = 0; x < VERTICES; x++) {
            for (int z = 0; z < VERTICES; z++) {
                vertices[vertices_pointer * 3] = (float) z / ((float) VERTICES - 1) * SIZE;
                vertices[vertices_pointer * 3 + 1] = SimplexNoise.noise(x, z);
                vertices[vertices_pointer * 3 + 2] = (float) x / ((float) VERTICES - 1) * SIZE;

                normals[vertices_pointer * 3] = 0;
                normals[vertices_pointer * 3 + 1] = 1;
                normals[vertices_pointer * 3 + 2] = 0;

                textures[vertices_pointer * 2] = (float) z / ((float) VERTICES - 1);
                textures[vertices_pointer * 2 + 1] = (float) x / ((float) VERTICES - 1);

                vertices_pointer++;
            }
        }

        int indices_pointer = 0;

        for (int x = 0; x < VERTICES - 1; x++) {
            for (int z = 0; z < VERTICES - 1; z++) {

                int tl = (x * VERTICES) + z;
                int tr = tl + 1;
                int bl = ((x + 1) * VERTICES) + z;
                int br = bl + 1;

                indices[indices_pointer++] = tl;
                indices[indices_pointer++] = bl;
                indices[indices_pointer++] = tr;
                indices[indices_pointer++] = tr;
                indices[indices_pointer++] = bl;
                indices[indices_pointer++] = br;
            }
        }

        return new Mesh(vertices, textures, normals, indices);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}