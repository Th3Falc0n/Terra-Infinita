package com.dafttech.terra.game.world.gen.calc;

public class PerlinNoise {
    public int seed;
    public float octaves;
    public float persistence;
    public float smoothing;

    public PerlinNoise(int seed, float octaves, float persistence) {
        this.seed = seed;
        this.octaves = octaves;
        this.persistence = persistence;
        this.smoothing = 4.0f;
    }

    public PerlinNoise(int seed, float octaves) {
        this(seed, octaves, .25f);
    }

    public PerlinNoise(int seed) {
        this(seed, 4, .25f);
    }

    public float rawNoise(float x) {
        int n = ((int) x << 13) ^ ((int) x);
        return (1.0f - ((n * (n * n * 15731 * seed + 789221 * seed) + 1376312589 * seed) & 0x7fffffff) / 1073741824.0f);
    }

    public float rawNoise2D(float x, float y) {
        return rawNoise(x + y * 57);
    }

    public float smoothNoise(float x) {
        float left = rawNoise(x - 1.0f);
        float right = rawNoise(x + 1.0f);
        return (rawNoise(x) / 2.0f) + (left / smoothing) + (right / smoothing);
    }

    public float smoothNoise2D(float x, float y) {
        float corners = rawNoise2D(x - 1, y - 1) + rawNoise2D(x - 1, y + 1) + rawNoise2D(x + 1, y - 1) + rawNoise2D(x + 1, y + 1);
        float sides = rawNoise2D(x, y - 1) + rawNoise2D(x, y + 1) + rawNoise2D(x - 1, y) + rawNoise2D(x + 1, y);
        float center = rawNoise2D(x, y);
        return (center / 4.0f) + (sides / 8.0f) + (corners / 16.0f);
    }

    public float linearInterpolate(float a, float b, float x) {
        return a * (1 - x) + b * x;
    }

    public float cosineInterpolate(float a, float b, float x) {
        float f = (1.0f - (float) Math.cos(x * Math.PI)) / 2.0f;
        return a * (1 - f) + b * f;
    }

    public float interpolateNoise(float x) {
        return cosineInterpolate(smoothNoise((float) Math.floor(x)), smoothNoise((float) Math.floor(x) + 1.0f), (float) (x - Math.floor(x)));
    }

    public float interpolateNoise2D(float x, float y) {
        float a = cosineInterpolate(smoothNoise2D((float) Math.floor(x), (float) Math.floor(y)),
                smoothNoise2D((float) Math.floor(x) + 1.0f, (float) Math.floor(y)), x - (float) Math.floor(x));
        float b = cosineInterpolate(smoothNoise2D((float) Math.floor(x), (float) Math.floor(y) + 1),
                smoothNoise2D((float) Math.floor(x) + 1, (float) Math.floor(y) + 1), x - (float) Math.floor(x));
        return cosineInterpolate(a, b, y - (float) Math.floor(y));
    }

    public float perlinNoise(float x) {
        float total = 0, frequency, amplitude;
        for (int i = 0; i < octaves; i++) {
            frequency = (float) Math.pow(2.0, i);
            amplitude = (float) Math.pow(persistence, i);
            total += interpolateNoise(x * frequency) * amplitude;
        }
        return total;
    }

    public float perlinNoise2D(float x, float y) {
        float total = 0, frequency, amplitude;
        for (int i = 0; i < octaves; i++) {
            frequency = (float) Math.pow(2.0, i);
            amplitude = (float) Math.pow(persistence, i);
            total += interpolateNoise2D(x * frequency, y * frequency) * amplitude;
        }
        return total;
    }
}