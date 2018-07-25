package com.insertpunhere1.electron.engine.render.entities;

import com.insertpunhere1.electron.engine.render.core.Mouse;
import com.insertpunhere1.electron.engine.render.core.Shader;
import com.insertpunhere1.electron.engine.render.core.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position, rotation;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update(Window window, Mouse mouse) {
        if(mouse.isEntered() && mouse.getLeft())
            rotate(mouse.getDisplacement().x * 0.25f, mouse.getDisplacement().y * 0.25f, 0);

        if (window.key(GLFW.GLFW_KEY_W)) {
            update(0, 0, -0.05f);
        }

        if (window.key(GLFW.GLFW_KEY_S)) {
            update(0, 0, 0.05f);
        }

        if (window.key(GLFW.GLFW_KEY_A)) {
            update(-0.05f, 0, 0);
        }

        if (window.key(GLFW.GLFW_KEY_D)) {
            update(0.05f, 0, 0);
        }

        if (window.key(GLFW.GLFW_KEY_SPACE)) {
            update(0, 0.05f, 0);
        }

        if (window.key(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            update(0, -0.05f, 0);
        }
    }

    void update(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }

        if (offsetX != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }

        position.y += offsetY;
    }

    private void rotate(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public void load(Shader shader) {
        Matrix4f view = new Matrix4f().identity()
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))

                .translate(new Vector3f(-position.x, -position.y, -position.z));

        shader.start();

        shader.set("view_matrix", view);

        shader.stop();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
