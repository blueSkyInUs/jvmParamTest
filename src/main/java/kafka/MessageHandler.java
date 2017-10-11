package kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by lesson on 2017/10/5.
 */
@Component
public class MessageHandler {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void sendMessage(@NotNull String message,@NotNull String topic){

        kafkaTemplate.send(topic,message,message);

    }

    @KafkaListener(topics = {"${spring.kafka.topic}"})
    public String receiveMessage(String content){
        System.out.println("receive msg:"+content);
        return content;
    }


}
