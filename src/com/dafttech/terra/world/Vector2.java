package com.dafttech.terra.world;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float nx, float ny) {
        x = nx;
        y = ny;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public void normalize() {
        float length = length();

        if (length != 0.0) {
            float s = 1.0f / length;

            x *= s;
            y *= s;
        }
    }

    public Vector2 add(float ax, float ay) {
        return new Vector2(x + ax, y + ay);
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(float ax, float ay) {
        return new Vector2(x - ax, y - ay);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 mul(float ax, float ay) {
        return new Vector2(x * ax, y * ay);
    }

    public Vector2 mul(Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }

    public Vector2 mul(float k) {
        return new Vector2(x * k, y * k);
    }

    public Vector2 neg() {
        return new Vector2(-x, -y);
    }
}
