package com.insertpunhere1.electron.engine.render;

import com.insertpunhere1.electron.engine.utility.IO;

import org.joml.*;

import org.lwjgl.opengl.GL20;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class Shader {
    private int programID;

    private Map<String, Integer> uniforms = new HashMap<>();

    public Shader(String vertex_path, String fragment_path) {
        programID = GL20.glCreateProgram();

        try {
            GL20.glAttachShader(programID, generate(IO.string(vertex_path), GL20.GL_VERTEX_SHADER));
            GL20.glAttachShader(programID, generate(IO.string(fragment_path), GL20.GL_FRAGMENT_SHADER));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
    }

    void start() {
        GL20.glUseProgram(programID);
    }

    void stop() {
        GL20.glUseProgram(0);
    }

    public void initialize(Window window) {
        add("projection_matrix");
        add("transformation_matrix");
        add("view_matrix");

        start();

        set("projection_matrix", new Matrix4f().perspective(70, (float) window.getWidth() / (float) window.getHeight(), 0.01f, 4096f));

        stop();
    }

    private void add(String name) {
        int location = GL20.glGetUniformLocation(programID, name);

        if (location < 0)
            System.err.println("Uniform variable was not found in either shader.");
        else
            uniforms.put(name, location);
    }

    public void set(String name, int value) {
        GL20.glUniform1i(uniforms.get(name), value);
    }

    public void set(String name, float value) {
        GL20.glUniform1f(uniforms.get(name), value);
    }

    public void set(String name, Vector2i value) {
        GL20.glUniform2i(uniforms.get(name), value.x, value.y);
    }

    public void set(String name, Vector2f value) {
        GL20.glUniform2f(uniforms.get(name), value.x, value.y);
    }

    public void set(String name, Vector3i value) {
        GL20.glUniform3i(uniforms.get(name), value.x, value.y, value.z);
    }

    public void set(String name, Vector3f value) {
        GL20.glUniform3f(uniforms.get(name), value.x, value.y, value.z);
    }

    public void set(String name, Vector4i value) {
        GL20.glUniform4i(uniforms.get(name), value.x, value.y, value.z, value.w);
    }

    public void set(String name, Vector4f value) {
        GL20.glUniform4f(uniforms.get(name), value.x, value.y, value.z, value.w);
    }

    public void set(String name, Matrix3f value) {
        float[] array = new float[9];

        value.get(array);

        GL20.glUniformMatrix3fv(uniforms.get(name), false, array);
    }

    void set(String name, Matrix4f value) {
        float[] array = new float[16];

        value.get(array);

        GL20.glUniformMatrix4fv(uniforms.get(name), false, array);
    }

    private int generate(String source, int type) {
        int shaderID = GL20.glCreateShader(type);

        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);

        System.err.println(GL20.glGetShaderInfoLog(shaderID));

        return shaderID;
    }
}