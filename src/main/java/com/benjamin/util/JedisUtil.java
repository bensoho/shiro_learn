package com.benjamin.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;
}
