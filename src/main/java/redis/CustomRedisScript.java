package redis;

import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lesson on 2017/9/27.
 */
public class CustomRedisScript<T> implements RedisScript<T> {

    private static final ConcurrentHashMap<String,String> sha1Map=new ConcurrentHashMap<>();
    private Class<T> returnType;

    private String script;

    public CustomRedisScript(String script, Class<T> returnType) {
        this.script=script;
        this.returnType=returnType;
    }


    public String getSha1() {
        return sha1Map.computeIfAbsent(getScriptAsString(),key->DigestUtils.sha1DigestAsHex(key));
    }

    public Class<T> getResultType() {
        return returnType;
    }

    public String getScriptAsString() {
        return script;
    }



}
