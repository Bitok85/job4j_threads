package ru.job4j.concurrent.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
class Sample {

    @GuardedBy("this")
    private final User user;

    public Sample(User user) {
        this.user = user;
    }

    protected synchronized String[] mailArr() {
        String subject =
                "Notification " + user.getName() + " to email " + user.getMail();
        String body = "Add new event " + user.getName();
        return new String[]{subject, body};
    }
}
