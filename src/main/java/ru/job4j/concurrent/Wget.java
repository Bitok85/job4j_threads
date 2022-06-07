package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Wget implements Runnable {

    private final URL url;
    private final int speed;

    public Wget(URL url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
             FileOutputStream fos = new FileOutputStream("pom_tmp.xml")) {
                 fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                 Thread.sleep(speed);
             } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread wget = new Thread(new Wget(
                    new URL("https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml"),
                    1000
            ));
            wget.start();
            wget.join();
            System.out.println(wget.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
