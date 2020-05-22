package com.zcb.minimallcore.mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @KafkaListener(topics = {"producer_test"}, groupId = "test_group")
    public void listener(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            LOGGER.info("record----**********--" + record);
            LOGGER.info("message------********-" + message);
        }

    }

    @KafkaListener(topics = {"admin_topic_order"}, groupId = "test_group")
    public void adminTopicOrder(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            //邮件/短信发送
            LOGGER.info("admin_topic_order----**********--" + record);
            LOGGER.info("admin_topic_order------********-" + message);
        }

    }

    @KafkaListener(topics = {"user_topic_order"}, groupId = "test_group")
    public void userTopicOrder(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            LOGGER.info("user_topic_order----**********--" + record);
            LOGGER.info("user_topic_order------********-" + message);
        }

    }
}
