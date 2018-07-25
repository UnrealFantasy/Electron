package com.insertpunhere1.electron.engine.render.core;

import com.insertpunhere1.electron.engine.render.core.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Mouse {
    private final Vector2d previous, current;

    private final Vector2f displacement;

    private boolean entered = false, left = false, right = false;

    public Mouse() {
        previous = new Vector2d(-1, -1);
        current = new Vector2d(0, 0);

        displacement = new Vector2f(0, 0);
    }

    public void initialize(Window window) {
        GLFW.glfwSetCursorPosCallback(window.getHandle(), (windowHandle, xpos, ypos) -> {
            current.x = xpos;
            current.y = ypos;
        });

        GLFW.glfwSetCursorEnterCallback(window.getHandle(), (windowHandle, entered) -> {
            this.entered = entered;
        });

        GLFW.glfwSetMouseButtonCallback(window.getHandle(), (windowHandle, button, action, mode) -> {
            left = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            right = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
    }

    public void update() {
        displacement.x = 0;
        displacement.y = 0;

        if (previous.x > 0 && previous.y > 0) {
            double deltax = current.x - previous.x;
            double deltay = current.y - previous.y;

            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;

            if (rotateX) {
                displacement.y = (float) deltax;
            }

            if (rotateY) {
                displacement.x = (float) deltay;
            }
        }

        previous.x = current.x;
        previous.y = current.y;
    }

    public Vector2f getDisplacement() {
        return displacement;
    }

    public boolean getLeft() {
        return left;
    }


    public boolean getRight() {
        return right;
    }

    public boolean isEntered() {
        return entered;
    }
}