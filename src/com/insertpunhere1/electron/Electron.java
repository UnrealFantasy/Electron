package com.insertpunhere1.electron;

import com.insertpunhere1.electron.engine.render.core.modeling.Texture;
import com.insertpunhere1.electron.engine.render.core.modeling.Wavefront;
import com.insertpunhere1.electron.engine.render.entities.Camera;
import com.insertpunhere1.electron.engine.render.entities.Entity;
import com.insertpunhere1.electron.engine.render.entities.Object;
import com.insertpunhere1.electron.engine.render.core.Mouse;
import com.insertpunhere1.electron.engine.render.core.Shader;
import com.insertpunhere1.electron.engine.render.core.Window;
import com.insertpunhere1.electron.engine.render.terrain.Terrain;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Electron {
    public static void main(String[] args) {
        Window window = new Window(1440, 900, "Electron");

        window.create();

        Mouse mouse = new Mouse();

        mouse.initialize(window);

        Shader shader = new Shader("./assets/shaders/vertex.txt", "./assets/shaders/fragment.txt");

        shader.initialize(window);

        Terrain terrain = new Terrain(new Texture("./assets/textures/grass.png"), 0, 0);

        Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

        while (!GLFW.glfwWindowShouldClose(window.getHandle())) {
            window.clear();

            camera.load(shader);

            terrain.render(shader);

            window.update();

            camera.update(window, mouse);

            mouse.update();
        }

        window.close();
    }
}