package cn.xq.ssm.common;

import java.util.List;

/**
 * @author qiong.xie
 * @description redis 服务
 * @date 2021/5/13
 */
public interface JedisClient {

    String set(String key, String value);
    String get(String key);
    Boolean exists(String key);
    Long expire(String key, long seconds);
    Long ttl(String key);
    Long incr(String key);
    Long hSet(String key, String field, String value);
    String hGet(String key, String field);
    Long hDel(String key, String... field);
    Boolean heXiSts(String key, String field);
    List<String> hVaLs(String key);
    Long del(String key);
}
