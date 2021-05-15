package cn.xq.ssm.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/13
 */
public class JedisClientPool implements JedisClient {

    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**------------------------ 字符串 类型 ------------------------*/

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    /**
     * 检查给定 key 是否存在。
     * @param key
     * @return
     */
    @Override
    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public Long expire(String key, long seconds) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, seconds);
        jedis.close();
        return result;
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     * @param key
     * @return
     */
    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    /**
     * 为键 key 储存的数字值加上一。
     * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令。
     * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误。
     * @param key
     * @return
     */
    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    /**------------------------ Hash类型 ------------------------*/

    /**
     * 将哈希表 hash 中域 field 的值设置为 value 。
     * 如果给定的哈希表并不存在， 那么一个新的哈希表将被创建并执行 HSET 操作。
     * 如果域 field 已经存在于哈希表中， 那么它的旧值将被新值 value 覆盖。
     * @param key
     * @param field
     * @param value
     * @return
     * */
    @Override
    public Long hSet(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    /**
     * 返回哈希表中给定域的值。
     * @param key
     * @param field
     * @return
     * */
    @Override
    public String hGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     * @param key
     * @param field
     * @return
     */
    @Override
    public Long hDel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, field);
        jedis.close();
        return result;
    }

    /**
     * 检查给定域 field 是否存在于哈希表 hash 当中。
     * @param key
     * @param field
     * @return
     */
    @Override
    public Boolean heXiSts(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.hexists(key, field);
        jedis.close();
        return result;
    }

    /**
     * 返回哈希表 key 中所有域的值。
     * @param key
     * @return
     */
    @Override
    public List<String> hVaLs(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> result = jedis.hvals(key);
        jedis.close();
        return result;
    }

    /**
     * 删除给定的一个或多个 key 。
     * 不存在的 key 会被忽略。
     * @param key
     * @return
     */
    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }
}
