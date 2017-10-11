package init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.CustomRedisScript;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lesson on 2017/9/27.
 */
@Component
public class DistributeRedisLock{

    public static final String FLAG_LOCK_SUCCESS="true";

    public static final String FLAG_UNLOCK_SUCCESS="true";

    private  String LOCK_SCRIPT;
    private  String UNLOCK_SCRIPT;


    public DistributeRedisLock(){

    }


    @PostConstruct
    public void readScript(){
        try{
            Path path=Paths.get(DistributeRedisLock.class.getClassLoader().getResource("redis/lock.lua").toURI());
            LOCK_SCRIPT=Files.readAllLines(path).stream().reduce((a,b)->a+"\n"+b).get();
        }catch(Exception exp){
            throw new RuntimeException(exp);
        }

        try{
            Path path=Paths.get(DistributeRedisLock.class.getClassLoader().getResource("redis/unlock.lua").toURI());
            UNLOCK_SCRIPT=Files.readAllLines(path).stream().reduce((a,b)->a+"\n"+b).get();
        }catch(Exception exp){
            throw new RuntimeException(exp);
        }
    }


    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    public  boolean lock(String lockKey,int expireInSecond,String lockValue){
        List<String> list=new ArrayList<>();
        list.add(lockKey);
        String result= redisTemplate.execute(new CustomRedisScript<>(LOCK_SCRIPT,String.class),list,expireInSecond+"",lockValue);
        return FLAG_LOCK_SUCCESS.equals(result);
    }

    public boolean unlock(String lockKey,String lockValue){
        List<String> list=new ArrayList<>();
        list.add(lockKey);
        String result= redisTemplate.execute(new CustomRedisScript<>(UNLOCK_SCRIPT,String.class),list,lockValue);
        return FLAG_UNLOCK_SUCCESS.equals(result);
    }

    public static void main(String[] args) throws  Exception {
        Set<HostAndPort> hostAndPorts=new HashSet<>();
        hostAndPorts.add(new HostAndPort("127.0.0.1",7000 ));
        hostAndPorts.add(new HostAndPort("127.0.0.1",7001 ));
        hostAndPorts.add(new HostAndPort("127.0.0.1",7002 ));
        hostAndPorts.add(new HostAndPort("127.0.0.1",7003 ));
        hostAndPorts.add(new HostAndPort("127.0.0.1",7004 ));
        hostAndPorts.add(new HostAndPort("127.0.0.1",7005 ));
        JedisCluster jedisCluster=new JedisCluster(hostAndPorts);
        Path path=Paths.get(DistributeRedisLock.class.getClassLoader().getResource("redis/lock.lua").toURI());
        String LOCK_SCRIPT=Files.readAllLines(path).stream().reduce((a,b)->a+"\n"+b).get();
        Object object=jedisCluster.evalsha(LOCK_SCRIPT,1,"hello","50","ready");
        System.out.println(object);

    }
}
