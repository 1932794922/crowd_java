package xiaozaiyi.crowd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xiaozaiyi.crowd.util.api.R;

import java.util.concurrent.TimeUnit;

/**
 * 调用远程 redis provider
 */
@FeignClient("august-redis")
//浏览器请求到达这里
public interface IRedisClient {

    /**
     * 设置 键值
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("set/redis/key/value")
    R<String> setRedisKeyValue(@RequestParam("key") String key, @RequestParam("value") String value);


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
            @RequestParam("timeUnit") TimeUnit timeUnit
    );

    @RequestMapping("get/redis/value/by/key")
    R<String> getRedisValueByKey(@RequestParam("key") String key);


    @RequestMapping("remove/redis/value/by/key")
    R<String> removeRedisValueByKey(@RequestParam("key") String key);



}
