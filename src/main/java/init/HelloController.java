package init;

import kafka.MessageHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lesson on 2017/9/27.
 */
@SpringBootApplication(scanBasePackages={"init","kafka"})
@Controller
@MapperScan(basePackages = "mybatis")
public class HelloController {
    @Autowired
    private DistributeRedisLock distributeRedisLock;

    @Autowired
    private MessageHandler messageHandler;


    @Value("${spring.kafka.topic}")
    private String topic;

    @RequestMapping("/")
    @ResponseBody
    public String lock() throws Exception{

        String lockKey="lock:account:1";
        String lockValue="what";
        boolean result=false;
        try{
            result=distributeRedisLock.lock(lockKey,50,lockValue);
        }catch(Exception exp){
            exp.printStackTrace();
        }finally {
            distributeRedisLock.unlock(lockKey,lockValue);
        }
        return ""+result;
    }



    @Bean
    public RedisConnectionFactory getRedisConnectionFactory(RedisProperties redisProperties){
        RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        return new LettuceConnectionFactory(config);
    }



    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMessage(String content){
        messageHandler.sendMessage(content,topic);
        return "good";
    }



    public static void main(String[] args) throws Exception {
       SpringApplication.run(HelloController.class, args);
    }
}
