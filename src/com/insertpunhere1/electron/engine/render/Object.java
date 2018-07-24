package com.insertpunhere1.electron.engine.render;

public class Object {
    private Mesh mesh;

    private Texture texture;

    public Object(Mesh mesh, Texture texture) {
        this.mesh = mesh;

        this.texture = texture;
    }

    void render(Shader shader) {
        texture.start();

        mesh.render();

        texture.stop();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
