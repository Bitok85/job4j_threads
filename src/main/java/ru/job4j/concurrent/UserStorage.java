package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    private static final Logger LOG = LoggerFactory.getLogger(UserStorage.class.getName());
    private Map<Integer, Integer> userStores = new HashMap<>();

    public boolean add(UserStore userStore) {
        boolean rsl = false;
        if (!userStores.containsKey(userStore.getId())) {
            userStores.put(userStore.getId(), userStore.getAmount());
            rsl = true;
            LOG.info("User with {} id successfully added.", userStore.getId());
        } else {
            LOG.info("User with {} id is already exists.", userStore.getId());
        }
        return rsl;
    }

    public boolean update(int id, int amountChange) {
        boolean rsl = false;
        if (userStores.containsKey(id)) {
            int newAmount = userStores.get(id) + amountChange;
            if (newAmount >= 0) {
               userStores.put(id, newAmount);
               rsl = true;
               LOG.info(
                       "User with {} id changed amount from {} to {}.", id, userStores.get(id), newAmount
               );
            } else {
                LOG.info("Can't change for {}, because new amount goes under 0.", amountChange);
            }
        } else {
            LOG.info("User with {} id isn't exists.", id);
        }
        return rsl;
    }

    public boolean delete(int id) {
        boolean rsl = false;
        if (userStores.containsKey(id)) {
            userStores.remove(id);
            rsl = true;
            LOG.info("User with {} id removed.", id);
        } else {
            LOG.info("User with {} id isn't exists.", id);
        }
        return rsl;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (userStores.containsKey(fromId) && userStores.containsKey(toId)) {
            if (update(fromId, -amount) && update(toId, amount)) {
                rsl = true;
                LOG.info("Transaction done successfully");
            } else {
                LOG.info("Transaction denied");
            }
        }
        return rsl;
    }
}
