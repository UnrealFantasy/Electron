package com.insertpunhere1.electron.engine.render.core;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width, height;

    private String title;

    private long handle;

    private static final long NULL = 0;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;

        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("GLFW failed to initialize.");

        GLFW.glfwDefaultWindowHints();

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        handle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);

        if (handle == NULL)
            throw new IllegalStateException("GLFW window failed to create.");

        GLFW.glfwMakeContextCurrent(handle);
        GLFW.glfwShowWindow(handle);

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void clear() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        GLFW.glfwSwapBuffers(handle);
        GLFW.glfwPollEvents();
    }

    public void close() {
        GLFW.glfwDestroyWindow(handle);
        GLFW.glfwTerminate();
    }

    public boolean key(int key) {
        return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_PRESS;
    }

    int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public long getHandle() {
        return handle;
    }

    void setHandle(long handle) {
        this.handle = handle;
    }
}
