package com.insertpunhere1.electron.engine.render.entities;

import com.insertpunhere1.electron.engine.render.core.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {
    private Vector3f position, rotation, scale;

    private Object object;

    public Entity(Vector3f position, Vector3f rotation, Vector3f scale, Object object) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.object = object;
    }

    public void render(Shader shader) {
        shader.start();

        shader.set("transformation_matrix", transform());

        object.render();

        shader.stop();
    }

    private Matrix4f transform() {
        return new Matrix4f().identity()
                .translate(position)

                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))

                .scale(scale);
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
