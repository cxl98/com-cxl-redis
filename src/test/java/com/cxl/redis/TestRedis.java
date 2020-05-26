package com.cxl.redis;

import java.io.IOException;

public class TestRedis {
    public static void main(String[] args) {
        RedisServer server=new RedisServer();
        try {
            server.init(6380);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
