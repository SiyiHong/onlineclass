package onlineclass.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseCache {
    private Cache<String,Object> tenMinuteCache = CacheBuilder.newBuilder()
            .initialCapacity(10).maximumSize(100)//初始容量及最大容量
            .concurrencyLevel(5)//允许并发线程数
            .expireAfterWrite(600, TimeUnit.SECONDS)//过期时间，写入后10分钟
            .recordStats()//统计命中率
            .build();
    private Cache<String,Object> OneHourCache = CacheBuilder.newBuilder()
            .initialCapacity(30).maximumSize(100)//初始容量及最大容量
            .concurrencyLevel(5)//允许并发线程数
            .expireAfterWrite(60, TimeUnit.MINUTES)//过期时间，写入后1小时
            .recordStats()//统计命中率
            .build();

    public Cache<String, Object> getOneHourCache() {
        return OneHourCache;
    }

    public void setOneHourCache(Cache<String, Object> oneHourCache) {
        OneHourCache = oneHourCache;
    }

    public Cache<String, Object> getTenMinuteCache() {
        return tenMinuteCache;
    }

    public void setTenMinuteCache(Cache<String, Object> tenMinuteCache) {
        this.tenMinuteCache = tenMinuteCache;
    }
}
