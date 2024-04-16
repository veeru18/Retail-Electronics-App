package com.ecommerce.retail_electronicsapp.cache;

import java.time.Duration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheStore<T> {
	
	private Cache<String, T> cache;
	
	public CacheStore(int maxAge) {
		this.cache=CacheBuilder.newBuilder()
							.expireAfterWrite(Duration.ofMinutes(maxAge))
							.concurrencyLevel(Runtime.getRuntime().availableProcessors())
//							concurrencyLevel specifies how much resources available of that system where app is running to handle multiples user threads
//							Rumtime returns the number of available processors for a different user thread that tries to use same application 
							.build();
	}
	
	public void add(String key,T value) {
		cache.put(key, value);
	}
	
	public T get(String key) {
		return cache.getIfPresent(key);
	}
	
	public void remove(String key) {
		cache.invalidate(key);
	}
}
