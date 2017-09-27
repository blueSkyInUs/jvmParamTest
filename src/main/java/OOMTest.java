import init.DistributeRedisLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lesson on 2017/9/20.
 */
public class OOMTest {

    public static void main(String[] args) {


        List<String> list=new ArrayList<String>();
        list.add("hello");


        DistributeRedisLock distributeRedisLock=new DistributeRedisLock();
        System.out.println(distributeRedisLock.lock("lock:account:1",5,"what"));
        System.out.println(distributeRedisLock.unlock("lock:account:1","what"));


//        init.DistributeRedisLock distributeRedisLock1=new init.DistributeRedisLock();
//        System.out.println(distributeRedisLock1.lock("lock:account:1",5,"what"));
//        System.out.println(distributeRedisLock1.unlock("lock:account:1","what"));




    }
}
