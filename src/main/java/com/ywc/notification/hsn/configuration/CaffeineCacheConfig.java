package com.ywc.notification.hsn.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

@Configuration
public class CaffeineCacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("HSNCODEMODEL", "SECOND_CACHE");
		cacheManager.setAllowNullValues(false); //can happen if you get a value from a @Cachable that returns null
		cacheManager.setCaffeineSpec(caffeineSpec());
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}

	private CaffeineSpec caffeineSpec() {
		return CaffeineSpec.parse
				("initialCapacity=100,maximumSize=1000,expireAfterAccess=10m,recordStats");

	}


	Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder()
				.initialCapacity(100)
				.maximumSize(150)
				.expireAfterAccess(5, TimeUnit.DAYS)
				.weakKeys()
				.removalListener(new CustomRemovalListener())
				.recordStats();
	}

	class CustomRemovalListener implements RemovalListener<Object, Object>{
		public void onRemoval(Object key, Object value, RemovalCause cause) {
			System.out.format("removal listerner called with key [%s], cause [%s], evicted [%S]\n", 
					key, cause.toString(), cause.wasEvicted());
		}
	}

}
