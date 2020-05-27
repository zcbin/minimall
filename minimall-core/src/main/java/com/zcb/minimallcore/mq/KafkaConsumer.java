package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.config.KafkaConfig;
import com.zcb.minimallcore.mail.MailService;
import com.zcb.minimallcore.vo.EmailMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author: zcbin
 * @title: KafkaConsumer
 * @packageName: com.zcb.minimallcore.mq
 * @projectName: minimall
 * @description: kafka消费者
 * @date: 2020/5/20 16:04
 */
@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;
    /**
     * 邮件通知
     * @param record
     */
    @KafkaListener(topics = {KafkaConfig.EMAIL_NOTIFICATION})
    public void emailNotification(ConsumerRecord<String, JSONObject> record) {

        JSONObject jsonObject = record.value();
        EmailMessage email = jsonObject.getObject("data", EmailMessage.class); //邮件数据
        try {
            mailService.sendSimpleMail(email.getReceiver(), email.getSubject(), email.getContent(), email.getCc());
            LOGGER.info("邮件发送成功");
        } catch (Exception e) {
            LOGGER.info("邮件发送失败");
        }

//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//            }
//        }

    }
    @KafkaListener(topics = {KafkaConfig.TEST_TOPIC})
    public void emailHtml(ConsumerRecord<String, JSONObject> record) {

        JSONObject jsonObject = record.value();
        EmailMessage email = jsonObject.getObject("data", EmailMessage.class); //邮件数据
        try {

            //html模版
            Context context = new Context();
            context.setVariable("project", "minimall");
            context.setVariable("author", "zcbin");
            context.setVariable("url", "https://github.com/zcbin/minimall");

            String emailTemplate = templateEngine.process("welcome", context);
            //mailService.sendHtmlMail(email.getReceiver(), email.getSubject(), emailTemplate);


            LOGGER.info("邮件发送成功");
        } catch (Exception e) {
            LOGGER.info("邮件发送失败");
        }

//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//            }
//        }

    }

}
