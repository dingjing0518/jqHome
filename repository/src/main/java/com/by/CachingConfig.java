package com.by;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

/**
 * Created by yagamai on 15-11-27.
 */
@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
	public net.sf.ehcache.CacheManager ehCacheManager() {
		// rule cache config
		CacheConfiguration ruleCacheConfig = new CacheConfiguration();
		ruleCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		ruleCacheConfig.setMaxEntriesLocalHeap(1000);
		ruleCacheConfig.setTimeToIdleSeconds(30 * 60);
		ruleCacheConfig.setName("rule");

		// coupon cache config
		CacheConfiguration couponCacheConfig = new CacheConfiguration();
		couponCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		couponCacheConfig.setMaxEntriesLocalHeap(1000);
		couponCacheConfig.setTimeToIdleSeconds(30 * 60);
		couponCacheConfig.setName("coupon");

		// menu cache config
		CacheConfiguration menuCacheConfig = new CacheConfiguration();
		menuCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		menuCacheConfig.setMaxEntriesLocalHeap(1000);
		menuCacheConfig.setTimeToIdleSeconds(30 * 60);
		menuCacheConfig.setName("menu");

		// card cache config
		CacheConfiguration cardRuleCacheConfig = new CacheConfiguration();
		cardRuleCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		cardRuleCacheConfig.setMaxEntriesLocalHeap(1000);
		cardRuleCacheConfig.setTimeToIdleSeconds(30 * 60);
		cardRuleCacheConfig.setName("cardRule");

		CacheConfiguration shopRuleCacheConfig = new CacheConfiguration();
		shopRuleCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		shopRuleCacheConfig.setMaxEntriesLocalHeap(1000);
		shopRuleCacheConfig.setTimeToIdleSeconds(30 * 60);
		shopRuleCacheConfig.setName("shopRule");

		// shop cache config
		CacheConfiguration shopCacheConfig = new CacheConfiguration();
		shopCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		shopCacheConfig.setMaxEntriesLocalHeap(1000);
		shopCacheConfig.setTimeToIdleSeconds(30 * 60);
		shopCacheConfig.setName("shop");

		// member cache config
		CacheConfiguration memberCacheConfig = new CacheConfiguration();
		memberCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		memberCacheConfig.setMaxEntriesLocalHeap(1000);
		memberCacheConfig.setTimeToIdleSeconds(30 * 60);
		memberCacheConfig.setName("member");

		// help cache config
		CacheConfiguration helpCacheConfig = new CacheConfiguration();
		helpCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		helpCacheConfig.setMaxEntriesLocalHeap(1000);
		helpCacheConfig.setTimeToIdleSeconds(30 * 60);
		helpCacheConfig.setName("help");

		// license cache config
		CacheConfiguration licenseCacheConfig = new CacheConfiguration();
		licenseCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		licenseCacheConfig.setMaxEntriesLocalHeap(1000);
		licenseCacheConfig.setTimeToIdleSeconds(30 * 60);
		licenseCacheConfig.setName("license");

		CacheConfiguration authCacheConfig = new CacheConfiguration();
		authCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		authCacheConfig.setMaxEntriesLocalHeap(1000);
		authCacheConfig.setTimeToIdleSeconds(30 * 60);
		authCacheConfig.setName("auth");

		CacheConfiguration cardCacheConfig = new CacheConfiguration();
		cardCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		cardCacheConfig.setMaxEntriesLocalHeap(1000);
		cardCacheConfig.setTimeToIdleSeconds(30 * 60);
		cardCacheConfig.setName("card");

		// user menu
		CacheConfiguration userMenuCacheConfig = new CacheConfiguration();
		userMenuCacheConfig.setMemoryStoreEvictionPolicy("LRU");
		userMenuCacheConfig.setMaxEntriesLocalHeap(1000);
		userMenuCacheConfig.setTimeToIdleSeconds(30 * 60);
		userMenuCacheConfig.setName("userMenu");

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(ruleCacheConfig);
		config.addCache(couponCacheConfig);
		config.addCache(menuCacheConfig);
		config.addCache(cardRuleCacheConfig);
		config.addCache(shopCacheConfig);
		config.addCache(memberCacheConfig);
		config.addCache(helpCacheConfig);
		config.addCache(licenseCacheConfig);
		config.addCache(authCacheConfig);
		config.addCache(cardCacheConfig);
		config.addCache(userMenuCacheConfig);
		config.addCache(shopRuleCacheConfig);

		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
}
