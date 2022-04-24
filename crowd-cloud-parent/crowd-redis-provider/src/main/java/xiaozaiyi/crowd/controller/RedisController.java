package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.util.api.R;

import java.util.concurrent.TimeUnit;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   22:20
 */
@RestController
public class RedisController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置 键值
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("set/redis/key/value")
    R<String> setRedisKeyValue(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            return R.status(true);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 设置 键值,超时时间
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("set/redis/key/value/timeout")
    R<String> setRedisKeyValueWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnit") TimeUnit timeUnit) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value, time, timeUnit);
            return R.status(true);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }

    }

    @RequestMapping("get/redis/value/by/key")
    R<String> getRedisValueByKey(@RequestParam("key") String key) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String value = operations.get(key);
            return R.data(value);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    @RequestMapping("remove/redis/value/by/key")
    R<String> removeRedisValueByKey(@RequestParam("key") String key) {
        try {
            Boolean delete = stringRedisTemplate.delete(key);
            return R.status(delete);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


}
