package ru.job4j.userstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    private static final Logger LOG = LoggerFactory.getLogger(UserStorage.class.getName());
    private Map<Integer, User> users = new HashMap<>();

    public boolean add(User user) {
        boolean rsl = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            rsl = true;
            LOG.info("User with {} id successfully added.", user.getId());
        } else {
            LOG.info("User with {} id is already exists.", user.getId());
        }
        return rsl;
    }

    public boolean update(User user, int amountChange) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            int newAmount = users.get(user.getId()).getAmount() + amountChange;
            if (newAmount >= 0) {
               users.get(user.getId()).setAmount(newAmount);
               rsl = true;
               LOG.info(
                       "User with {} id changed amount from {} to {}.", user.getId(), users.get(user.getId()).getAmount(), newAmount
               );
            } else {
                LOG.info("Can't change for {}, because new amount goes under 0.", amountChange);
            }
        } else {
            LOG.info("User with {} id isn't exists.", user.getId());
        }
        return rsl;
    }

    public boolean delete(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            rsl = true;
            LOG.info("User with {} id removed.", user.getId());
        } else {
            LOG.info("User with {} id isn't exists.", user.getId());
        }
        return rsl;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (users.containsKey(fromId) && users.containsKey(toId)) {
            if (update(users.get(fromId), -amount) && update(users.get(toId), amount)) {
                rsl = true;
                LOG.info("Transaction done successfully");
            } else {
                LOG.info("Transaction denied");
            }
        }
        return rsl;
    }
}
