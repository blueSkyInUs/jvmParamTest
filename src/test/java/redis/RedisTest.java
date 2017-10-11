package redis;

import init.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lesson on 2017/9/27.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloController.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest<T extends  Object> {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private HelloController helloController;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void hello(){

//
//        BDDMockito.given(this.helloController.lock()).willReturn("lesson");
//
//        String body=testRestTemplate.getForObject("/",String.class);
//
//        System.out.println(helloController.lock());


    }

    @Test
    public void redisTrans(){
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        operations.set("xili","two");
        operations.set("lesson","what");
        operations.set("youtiaokankan","youtiaokankan");
        operations.set("1","2");

    }






}
