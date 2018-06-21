package xin.lovegrave.user.redis;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xin.lovegrave.user.util.BeanUtil;
import xin.lovegrave.user.util.SerializeUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Redis封装类
 * Created by a on 2018/3/19.
 *
 * @author wy
 */
@Service
public class RedisService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private JedisPool jedisPool;

    /**
     * 日志对象
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(4);
        return jedis;
    }

    public void remove(String key) {
        Jedis jedis = getJedis();
        try {
            Boolean exists = jedis.exists(key);
            if(exists) {
                jedis.del(key);
            }
        } finally {
            jedis.close();
        }

    }

    public void del(String key, String field) {
        Jedis jedis = getJedis();
        try {
            Boolean exists = jedis.exists(key);
            if(exists){
                jedis.hdel(key, field);
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取Map对象
     */
    public Map getMap(final String key) {
        try {
            Jedis jedis = getJedis();
            try {
                Map<String, String> result = jedis.hgetAll(key);
                return result;

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            return null;
        }
    }

    /**
     * 获取object对象
     */
    public <T> T getObject(String key, Class<T> targetClass) {
        try {
            Jedis jedis = getJedis();
            try {
                String result = jedis.get(key);
                if (result != null) {
                    T contentMap = JSONObject.parseObject(result, targetClass);
                    return contentMap;
                }
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error("Exception", e);
            return null;
        }
        return null;
    }


    /**
     * 获取String
     */
    public String getString(String key) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.get(key);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 写入Map对象
     *
     * @param key
     * @param map
     * @return
     */
    public String setMap(String key, Map<String, String> map) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.hmset(key, map);
            jedis.expire(key, 7200);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 写入List
     *
     * @param key
     * @param object
     * @param seconds
     * @return
     */
    public <T> String setList(String key, List<T> object, int seconds) {
        Jedis jedis = getJedis();
        String result = null;
        try {
            result = jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(object));
            jedis.expire(key, seconds);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 获取list
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key) {
        List<T> list;
        try {
            Jedis jedis = getJedis();
            try {
                byte[] bty = jedis.get(SerializeUtil.serialize(key));
                list = SerializeUtil.unserializeForList(bty);
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error("Exception", e);
            return null;
        }
        return list;
    }

    /**
     * 写入list
     */

    public Long lpush(String key, Object object, int seconds) {
        Jedis jedis = getJedis();
        Long result = null;
        try {
            result = jedis.lpush(key, BeanUtil.toJsonString(object));
            jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 取list
     */
    public <T> List<T> lpList(String key, Class<T> clz) {
        List<T> list = new ArrayList<>();
        try {
            Jedis jedis = getJedis();
            try {
                List<String> response = jedis.lrange(key, 0, -1);
                for (String str : response) {
                    T t = BeanUtil.createBean(str, clz);
                    list.add(t);
                }
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error("Exception", e);
            return null;
        }
        return list;
    }

    /**
     * 写入object对象
     */
    public String setObject(String key, Object object,int seconds) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.set(key, BeanUtil.toJsonString(object));
            jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 写入String
     *
     * @param key
     * @param value
     */
    public String setString(String key, String value) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.set(key, value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 写入Map对象（有效期）
     *
     * @param key
     * @param map
     * @param seconds 有效期 单位：s
     */
    public String setMap(String key, Map<String, String> map, final int seconds) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.hmset(key, map);
            jedis.expire(key, 7200);
        } finally {
            jedis.close();
        }
        return result;
    }

    public Long setMapString(String key, String field, String value,int seconds) {
        Jedis jedis = getJedis();
        Long result;
        try {
            logger.info(new Date()+key);
            result = jedis.hset(key, field, value);
            jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
        return result;
    }
    /**
     * 写入String（有效期）
     *
     * @param key
     * @param value
     * @param seconds 有效期 单位：s
     */
    public String setString(String key, String value, int seconds) {
        Jedis jedis = getJedis();
        String result;
        try {
            result = jedis.setex(key, seconds, value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 修改有效期
     *
     * @param key
     * @param seconds
     */
    public Long updateString(String key, int seconds) {
        Jedis jedis = getJedis();
        Long result;
        try {
            result = jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 检测缓存中是否有对应的value
     */
    public Boolean exists(String key) {
        Jedis jedis = getJedis();
        Boolean result = false;
        try {
            result = jedis.exists(key);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * set 插入
     */
    public void zset(String key,Object object){
        Jedis jedis = getJedis();
        try{
            jedis.sadd(key,BeanUtil.toJsonString(object));
        }finally {
            jedis.close();
        }
    }
    /**
     * map 取值
     */
    public String getMapString(String key,String field){
        Jedis jedis = getJedis();
        try{
            String value = jedis.hget(key,field);
            return value;
        }finally {
            jedis.close();
        }
    }
}
