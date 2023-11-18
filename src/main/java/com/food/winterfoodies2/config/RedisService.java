package com.food.winterfoodies2.config;

import redis.clients.jedis.Jedis;

public class RedisService {
    private final Jedis jedis;

    public RedisService(String host, int port, String password) {
        this.jedis = new Jedis(host, port);
        jedis.auth(password);
    }

    public void flushDb() {
        jedis.flushDB();
    }
}