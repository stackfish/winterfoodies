package com.food.winterfoodies2.config;

import com.food.winterfoodies2.dto.Cart.CartActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, CartActionDto> cartActionDtoRedisTemplate() {
        RedisTemplate<String, CartActionDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(CartActionDto.class));
        return redisTemplate;
    }

    @Bean
    public RedisService redisService() {
        return new RedisService("redis-15739.c10.us-east-1-2.ec2.cloud.redislabs.com", 15739, "k21KkmXIy7CUimxaK6mT6IKUcf15vrbt");
    }

    @Bean
    public CommandLineRunner commandLineRunner(RedisService redisService) {
        return args -> redisService.flushDb();
    }
}
