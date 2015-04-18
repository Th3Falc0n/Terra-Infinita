package com.dafttech.terra;

public class FPSLogger {
    private long lastTimeNano = System.nanoTime();
    private float avgTime, avgFrames, frameTime, frameFrames;

    private boolean outputActive = true;

    public float tick() {
        long tDif = System.nanoTime() - lastTimeNano;
        lastTimeNano = System.nanoTime();

        float tDifF = tDif / 1000000000f;

        float avgSPF = 0;

        if (avgTime > 0) {
            avgSPF = (avgTime / avgFrames);
        }

        avgTime /= 1f + avgSPF;
        avgFrames /= 1f + avgSPF;

        avgTime += tDifF;
        avgFrames++;

        frameTime += tDifF;
        frameFrames++;

        if (frameTime > 0.5f) {
            if (outputActive)
                System.out.println(String.format("FPSCounter: t=%.3f F=%d F/t=%.1f avgF/t=%.1f", frameTime, (int) frameFrames, frameFrames
                        / frameTime, avgFrames / avgTime));

            frameTime = 0;
            frameFrames = 0;
        }

        return tDifF;
    }

    public void disableOutput() {
        outputActive = false;
    }
}
