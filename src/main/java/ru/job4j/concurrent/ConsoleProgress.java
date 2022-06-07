package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            String[] process = {"\\", "|", "/"};
            int index = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (index == 3) {
                    index = 0;
                }
                System.out.println("\r load : " + process[index]);
                Thread.sleep(500);
                index++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        try {
            progress.start();
            Thread.sleep(10000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
