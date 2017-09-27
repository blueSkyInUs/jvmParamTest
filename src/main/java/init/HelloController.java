package init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lesson on 2017/9/27.
 */
@SpringBootApplication
@Controller
public class HelloController {
    @Autowired
    private DistributeRedisLock distributeRedisLock;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        boolean result=distributeRedisLock.lock("lock:account:1",50,"what");
        return ""+result;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HelloController.class, args);

//        ConcurrentHashMap<String,String> hashMap=new ConcurrentHashMap<>();
//
//        String result=hashMap.computeIfAbsent("lesson",key->key+"a");
//        System.out.println(result);
//
//        System.out.println(hashMap.get("lesson"));






    }
}
