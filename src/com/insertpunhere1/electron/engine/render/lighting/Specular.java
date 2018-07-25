package com.insertpunhere1.electron.engine.render.lighting;

public class Specular {
    private float dampener, reflectivity;

    public Specular(float dampener, float reflectivity) {
        this.dampener = dampener;

        this.reflectivity = reflectivity;
    }

    public float getDampener() {
        return dampener;
    }

    public void setDampener(float dampener) {
        this.dampener = dampener;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
