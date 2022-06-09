package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private static final int MILLS_PAUSE = 1000;

    private final String url;
    private final int speed;
    private final String dest;

    public Wget(String url, int speed, String dest) {
        this.url = url;
        this.speed = speed;
        this.dest = dest;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream out = new FileOutputStream(dest)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < MILLS_PAUSE) {
                        Thread.sleep(MILLS_PAUSE - (endTime - startTime));
                    }
                    bytesWrited = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            } else {
                e.printStackTrace();
            }

        }
    }

    private static void validateArgs(String[] arr) {
        if (arr.length != 3) {
            throw new IllegalArgumentException("You have to enter 3 params: \n"
                    + "1. file url \n"
                    + "2. speed of downloading per second \n"
                    + "3. writing path \n");
        }
    }

    public static void main(String[] args) {
        validateArgs(args);
        try {
            Thread wget = new Thread(new Wget(args[0], Integer.parseInt(args[1]), args[2]));
            wget.start();
            wget.join();
            System.out.println(wget.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
