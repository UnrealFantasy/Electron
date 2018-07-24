package com.insertpunhere1.electron;

import com.insertpunhere1.electron.engine.render.*;
import com.insertpunhere1.electron.engine.render.Object;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Electron {
    public static void main(String[] args) throws Exception {
        Window window = new Window(1270, 700, "Electron");

        window.create();

        Mouse mouse = new Mouse();

        mouse.initialize(window);

        Shader shader = new Shader("./assets/shaders/vertex.txt", "./assets/shaders/fragment.txt");

        shader.initialize(window);

        Entity entity = new Entity(new Vector3f(0, 0, -4), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), new Object( Wavefront.load("./assets/models/primitive/sphere.obj"), new Texture("./assets/textures/default.png")));

        Camera camera = new Camera(new Vector3f(0, 0,0 ), new Vector3f(0, 0, 0));

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        while(!GLFW.glfwWindowShouldClose(window.getHandle())) {
            window.clear();

            camera.load(shader);

            entity.render(shader);

            window.update();

            camera.update(window, mouse);

            mouse.update();
        }

        window.close();
    }
}