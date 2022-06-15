package ru.job4j.userstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    private static final Logger LOG = LoggerFactory.getLogger(UserStorage.class.getName());
    private Map<Integer, User> users = new HashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public boolean delete(User user) {
       return users.remove(user.getId(), user);
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl;
        if (users.get(fromId) != null && users.get(toId) != null) {
            if (users.get(fromId).getAmount() >= amount) {
                int newAmountFrom = users.get(fromId).getAmount() - amount;
                int newAmountTo = users.get(toId).getAmount() + amount;
                users.get(fromId).setAmount(newAmountFrom);
                users.get(toId).setAmount(newAmountTo);
                rsl = true;
            } else {
                LOG.info("Not enough money");
                rsl = false;
            }
        } else {
            LOG.info("One of users doesn't exist");
            rsl = false;
        }
        return rsl;
    }
}
