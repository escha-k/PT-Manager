package com.project.ptmanager.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofDays(1));

    // 캐시별 커스텀 만료시간 설정
    Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
    // 주간 통계: 4주간 보관
    cacheConfigs.put("weeklyStatistics",
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(28)));
    // 월간 통계: 1달간 보관
    cacheConfigs.put("monthlyStatistics",
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(31)));

    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultConfig)
        .withInitialCacheConfigurations(cacheConfigs)
        .build();
  }
} 