package xin.lovegrave.user.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EhCacheServiceImpl {


    @Autowired
    private CacheManager cacheManager;

    public void save(String name,String key,Object value){
//        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(name);
        Element element = new Element(key,value);
        cache.put(element);
    }

    public Object getValue(String name,String key){
//        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(name);
        Element element = cache.get(key);
        return element.getObjectValue();
    }

    public void remove(String name,String key){
//        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(name);
        cache.remove(key);
        cacheManager.shutdown();
    }

    public void update(String name,String key,Object value){
//        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(name);
        cache.replace(new Element(key,value));
        cacheManager.shutdown();
    }

}
