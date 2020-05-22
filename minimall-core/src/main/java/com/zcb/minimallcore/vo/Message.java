package com.zcb.minimallcore.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: zcbin
 * @title: Message
 * @packageName: com.zcb.minimallcore.vo
 * @projectName: minimall
 * @description: 消息实体
 * @date: 2020/5/20 16:17
 */
public class Message {
    private Long id;
    private String topic;
    private String message;
    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", message='" + message + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
