package ru.job4j.concurrent.pool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final String message;

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public EmailNotification(String message) {
        this.message = message;
    }

    public void emailTo(User user) {
        pool.submit(() -> {
            String[] sample = new Sample(user).mailArr();
            send(sample[0], sample[1], message);
        });
        close();
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }
}
