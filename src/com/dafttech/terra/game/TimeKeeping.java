package com.dafttech.terra.game;

public class TimeKeeping {

    
    static long lastTime, currentTime;
    static String lastMsg;
    
    public static void timeKeeping(String msg) {
        long temp = currentTime;
        currentTime = System.currentTimeMillis();
        if(currentTime - lastTime > 25) {
            System.out.println("Time from " + lastMsg + " to " + msg + ": " + (currentTime - lastTime));
        }
        lastMsg = msg;
        lastTime = temp;
    }
}
