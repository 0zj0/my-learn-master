package com.example.redis;

import com.example.redis.helper.ICacheHelper;
import com.example.redis.helper.IZSetTuple;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjie
 * @date 2020/11/2 16:55
 */
@Service
public class RedisHelper implements ICacheHelper {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashOperations<String, String, Object> opsForHash;

    private static Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    private byte[] serializeString(String key) {
        RedisSerializer serializer = redisTemplate.getKeySerializer();
        return serializer.serialize(key);
    }

    private byte[] serializeObject(Object value) {
        RedisSerializer serializer = redisTemplate.getValueSerializer();
        return serializer.serialize(value);
    }

    private Set<String> deserializeCollection(Collection<byte[]> rawValues) {
        if (rawValues == null) {
            return null;
        } else {
            Set<String> cols = new LinkedHashSet<>(rawValues.size());
            Iterator<byte[]> it = rawValues.iterator();

            while (it.hasNext()) {
                byte[] rawValue = it.next();
                String value = null;
                if (redisTemplate.getHashKeySerializer() != null) {
                    value = ValueUtil.parseString(redisTemplate.getHashKeySerializer().deserialize(rawValue));
                }
                cols.add(value);
            }
            return cols;
        }
    }

    private Set<IZSetTuple> deserializeTupleValues(Collection<RedisZSetCommands.Tuple> rawValues) {
        if (rawValues == null) {
            return null;
        } else {
            Set<IZSetTuple> cols = new LinkedHashSet<>(rawValues.size());
            Iterator<RedisZSetCommands.Tuple> it = rawValues.iterator();

            while (it.hasNext()) {
                RedisZSetCommands.Tuple rawValue = it.next();

                String key = null;
                if (redisTemplate.getHashKeySerializer() != null) {
                    key = ValueUtil.parseString(redisTemplate.getHashKeySerializer().deserialize(rawValue.getValue()));
                }
                long value = ValueUtil.parseLong(rawValue.getScore());
                cols.add(new ZSetTuple(key, value));
            }
            return cols;
        }
    }

    private Object[] toObjectArr(String... strings) {
        Object[] objects = strings == null ? null : (new Object[strings.length]);
        for (int i = 0; i < strings.length; i++) {
            objects[i] = strings[i];
        }
        return objects;
    }

    @Override
    public boolean set(String key, Object value) {
        //默认没有返回值，所以重写了该方法
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.set(serializeString(key), serializeObject(value));
        }, true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean set(String key, Object value, long expire) {
        //默认没有返回值，所以重写了该方法
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.set(serializeString(key), serializeObject(value), Expiration.seconds(expire), RedisStringCommands.SetOption.UPSERT);
        }, true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean setnx(String key, Object value) {
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.setNX(serializeString(key), serializeObject(value));
        }, true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean setIfAbSent(String key, Object value, long expire) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
        return ValueUtil.getValue(result);
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            return ValueUtil.parse(obj, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getString(String key) {
        Object v = get(key);
        return ValueUtil.parseString(v);
    }

    @Override
    public int getInt(String key) {
        Object v = get(key);
        return ValueUtil.parseInt(v);
    }

    @Override
    public long getLong(String key) {
        Object v = get(key);
        return ValueUtil.parseLong(v);
    }

    @Override
    public double getDouble(String key) {
        Object v = get(key);
        return ValueUtil.parseDouble(v);
    }

    @Override
    public boolean getBoolean(String key) {
        Object v = get(key);
        return ValueUtil.parseBoolean(v);
    }

    @Override
    public Object getSet(String key, Object value) {
        try {
            Object result = redisTemplate.opsForValue().getAndSet(key, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T getSet(String key, Object value, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForValue().getAndSet(key, value);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean expire(String key, long expire) {
        Boolean result = redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return ValueUtil.getValue(result);
    }

    @Override
    public int llen(String key) {
        Long len = redisTemplate.opsForList().size(key);
        return len == null ? 0 : len.intValue();
    }

    @Override
    public <T> T lindex(String key, int index, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForList().index(key, index);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> lrange(String key, int start, int stop, Class<T> clazz) {
        try {
            List<Object> resultList = redisTemplate.opsForList().range(key, start, stop);
            return ValueUtil.parseList(resultList, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long lpush(String key, Object... objs) {
        Long result = redisTemplate.opsForList().leftPushAll(key, objs);
        return ValueUtil.getValue(result);
    }

    @Override
    public long rpush(String key, Object... objs) {
        Long result = redisTemplate.opsForList().rightPushAll(key, objs);
        return ValueUtil.getValue(result);
    }

    @Override
    public Object lpop(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object rpop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T lpop(String key, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForList().leftPop(key);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T rpop(String key, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForList().rightPop(key);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ltrim(String key, int start, int stop) {
        redisTemplate.opsForList().trim(key, start, stop);
    }

    @Override
    public boolean delByPattern(String key) {
        if (StringUtil.isNotEmpty(key)){
            Set<String> set = redisTemplate.keys(key + "*");
            for (String key1 : set) {
                redisTemplate.delete(key1);
            }
        }
        return true;
    }

    @Override
    public boolean del(String key) {
        Boolean result = redisTemplate.delete(key);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean expireAt(String key, long expireTimestamp) {
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.pExpireAt(serializeString(key), expireTimestamp);
        }, true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean persist(String key) {
        Boolean result = redisTemplate.persist(key);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean hset(String key, String field, Object value) {
        ///opsForHash.put(key, field, value);
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.hSet(serializeString(key), serializeString(field), serializeObject(value));
        }, true);
        return ValueUtil.getValue(result);

    }

    @Override
    public boolean hsetnx(String key, String field, Object value) {
        Boolean result = redisTemplate.execute((connection) -> {
            return connection.hSetNX(serializeString(key), serializeString(field), serializeObject(value));
        }, true);
        return ValueUtil.getValue(result);
    }

    @Override
    public Object hget(String key, String field) {
        try {
            return opsForHash.get(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T hget(String key, String field, Class<T> clazz) {
        try {
            Object object = opsForHash.get(key, field);
            return ValueUtil.parse(object, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        opsForHash.putAll(key, map);
        return true;
    }

    @Override
    public List<Object> hmget(String key, List<String> fieldList) {
        if (fieldList == null || fieldList.size() == 0) {
            return null;
        }
        try {
            return opsForHash.multiGet(key, fieldList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> hmget(String key, List<String> fieldList, Class<T> clazz) {
        try {
            List<Object> valueList = opsForHash.multiGet(key, fieldList);
            return ValueUtil.parseList(valueList, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> hgetall(String key) {
        try {
            return opsForHash.entries(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> Map<String, T> hmgetall(String key, Class<T> clazz) {
        try {
            Map<String, Object> map = opsForHash.entries(key);
            return ValueUtil.parseMap(map, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean hexists(String key, String field) {
        Boolean result = opsForHash.hasKey(key, field);
        return ValueUtil.parseBoolean(result);
    }

    @Override
    public int hdel(String key, String... fields) {
        Long result = opsForHash.delete(key, toObjectArr(fields));
        return ValueUtil.parseInt(result);
    }

    @Override
    public long hincrby(String key, String field, int increment) {
        Long result = opsForHash.increment(key, field, increment);
        return ValueUtil.parseLong(result);
    }

    @Override
    public Set<String> hkeys(String key) {
        try {
            Set<String> result = opsForHash.keys(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> hvals(String key) {
        try {
            List<Object> result = opsForHash.values(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> hvals(String key, Class<T> clazz) {
        try {
            List<Object> result = opsForHash.values(key);
            return ValueUtil.parseList(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int hlen(String key) {
        Long result = opsForHash.size(key);
        return ValueUtil.parseInt(result);
    }

    @Override
    public int sadd(String key, Object... members) {
        Long result = redisTemplate.opsForSet().add(key, members);
        return ValueUtil.parseInt(result);
    }

    @Override
    public int scard(String key) {
        Long result = redisTemplate.opsForSet().size(key);
        return ValueUtil.parseInt(result);
    }

    @Override
    public boolean sismember(String key, Object member) {
        Boolean result = redisTemplate.opsForSet().isMember(key, member);
        return ValueUtil.parseBoolean(result);
    }

    @Override
    public Set<Object> smembers(String key) {
        try {
            Set<Object> result = redisTemplate.opsForSet().members(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> Set<T> smembers(String key, Class<T> clazz) {
        try {
            Set<Object> result = redisTemplate.opsForSet().members(key);
            return ValueUtil.parseSet(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object srandmember(String key) {
        try {
            Object result = redisTemplate.opsForSet().randomMember(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T srandmember(String key, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForSet().randomMember(key);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> srandmember(String key, int count) {
        try {
            List<Object> result = redisTemplate.opsForSet().randomMembers(key, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> srandmember(String key, int count, Class<T> clazz) {
        try {
            List<Object> result = redisTemplate.opsForSet().randomMembers(key, count);
            return ValueUtil.parseList(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object spop(String key) {
        try {
            Object result = redisTemplate.opsForSet().pop(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T spop(String key, Class<T> clazz) {
        try {
            Object result = redisTemplate.opsForSet().pop(key);
            return ValueUtil.parse(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> spop(String key, int count) {
        try {
            List<Object> result = redisTemplate.opsForSet().pop(key, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> spop(String key, int count, Class<T> clazz) {
        try {
            List<Object> result = redisTemplate.opsForSet().pop(key, count);
            return ValueUtil.parseList(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int srem(String key, Object... members) {
        Long result = redisTemplate.opsForSet().remove(key, members);
        return ValueUtil.parseInt(result);
    }

    @Override
    public boolean zadd(String key, String member, long score) {
        Boolean result = redisTemplate.opsForZSet().add(key, member, score);
        return ValueUtil.parseBoolean(result);
    }

    @Override
    public int zadd(String key, Map<String, Long> memberScoreMap) {
        Set<ZSetOperations.TypedTuple<Object>> sets = new TreeSet<>();
        for (Map.Entry<String, Long> entry : memberScoreMap.entrySet()) {
            Double value = ValueUtil.parseDouble(entry.getValue());
            ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<>(entry.getKey(), value);
            sets.add(tuple);
        }
        Long result = redisTemplate.opsForZSet().add(key, sets);
        return ValueUtil.parseInt(result);
    }

    @Override
    public long zscore(String key, String member) {
        try {
            Double result = redisTemplate.opsForZSet().score(key, member);
            return ValueUtil.parseLong(result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int zcard(String key) {
        Long result = redisTemplate.opsForZSet().zCard(key);
        return ValueUtil.parseInt(result);
    }

    @Override
    public int zcount(String key, long min, long max) {
        Long result = redisTemplate.opsForZSet().count(key, min, max);
        return ValueUtil.parseInt(result);
    }

    @Override
    public long zincr(String key, String member) {
        try {
            Double result = redisTemplate.opsForZSet().incrementScore(key, member, 1);
            return ValueUtil.parseLong(result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long zincrby(String key, String member, long increment) {
        try {
            Double result = redisTemplate.opsForZSet().incrementScore(key, member, increment);
            return ValueUtil.parseLong(result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int zrank(String key, String member) {
        try {
            Long result = redisTemplate.opsForZSet().rank(key, member);
            return ValueUtil.parseInt(result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long stop) {
        try {
            Set<Object> result = redisTemplate.opsForZSet().range(key, start, stop);
            return ValueUtil.parseSet(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<IZSetTuple> zrangeWithScores(String key, long start, long stop) {
        try {
            Set<RedisZSetCommands.Tuple> rawValues = redisTemplate.execute((connection) -> {
                return connection.zRangeWithScores(serializeString(key), start, stop);
            }, true);
            return this.deserializeTupleValues(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax) {
        try {
            Set<byte[]> rawValues = redisTemplate.execute((connection) -> {
                RedisZSetCommands.Range range = new RedisZSetCommands.Range();
                range = eqMin ? range.gte(min) : range.gt(min);
                range = eqMax ? range.lte(max) : range.lt(max);
                return connection.zRangeByScore(serializeString(key), range);
            }, true);
            return deserializeCollection(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<IZSetTuple> zrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax) {
        try {
            Set<RedisZSetCommands.Tuple> rawValues = redisTemplate.execute((connection) -> {
                RedisZSetCommands.Range range = new RedisZSetCommands.Range();
                range = eqMin ? range.gte(min) : range.gt(min);
                range = eqMax ? range.lte(max) : range.lt(max);
                return connection.zRangeByScoreWithScores(serializeString(key), range);
            }, true);
            return this.deserializeTupleValues(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int zrevrank(String key, String member) {
        Long result = redisTemplate.opsForZSet().reverseRank(key, member);
        return ValueUtil.parseInt(result);
    }

    @Override
    public Set<String> zrevrange(String key, long start, long stop) {
        try {
            Set<Object> result = redisTemplate.opsForZSet().reverseRange(key, start, stop);
            return ValueUtil.parseSet(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<IZSetTuple> zrevrangeWithScores(String key, long start, long stop) {
        try {
            Set<RedisZSetCommands.Tuple> rawValues = redisTemplate.execute((connection) -> {
                return connection.zRevRangeWithScores(serializeString(key), start, stop);
            }, true);
            return this.deserializeTupleValues(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax) {
        try {
            Set<byte[]> rawValues = redisTemplate.execute((connection) -> {
                RedisZSetCommands.Range range = new RedisZSetCommands.Range();
                range = eqMin ? range.gte(min) : range.gt(min);
                range = eqMax ? range.lte(max) : range.lt(max);
                return connection.zRevRangeByScore(serializeString(key), range);
            }, true);
            return deserializeCollection(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<IZSetTuple> zrevrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax) {
        try {
            Set<RedisZSetCommands.Tuple> rawValues = redisTemplate.execute((connection) -> {
                RedisZSetCommands.Range range = new RedisZSetCommands.Range();
                range = eqMin ? range.gte(min) : range.gt(min);
                range = eqMax ? range.lte(max) : range.lt(max);
                return connection.zRevRangeByScoreWithScores(serializeString(key), range);
            }, true);
            return this.deserializeTupleValues(rawValues);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int zrem(String key, String... members) {
        Long result = redisTemplate.opsForZSet().remove(key, toObjectArr(members));
        return ValueUtil.parseInt(result);
    }

    @Override
    public boolean exists(String key) {
        Boolean isExistKey = redisTemplate.hasKey(key);
        return isExistKey != null && isExistKey;
    }

    @Override
    public long incr(String key) {
        try {
            Long ret = redisTemplate.opsForValue().increment(key);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long incrby(String key, long increment) {
        try {
            Long ret = redisTemplate.opsForValue().increment(key, increment);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long decr(String key) {
        try {
            Long ret = redisTemplate.opsForValue().decrement(key);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long decrBy(String key, long decrement) {
        try {
            Long ret = redisTemplate.opsForValue().decrement(key,decrement);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long ttl(String key) {
        Long timeout = redisTemplate.execute(new RedisCallback<Long>() {
            @Nullable
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.ttl(serializeString(key));
                } catch (Exception e) {
                    logger.info("hasKey.err", e);
                }
                return -1L;
            }
        });
        return timeout == null ? -1L : timeout;
    }

    @Override
    public long removeByScore(String key, long min, long max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
    }

}
