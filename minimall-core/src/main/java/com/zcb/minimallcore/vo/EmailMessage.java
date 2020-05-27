package com.zcb.minimallcore.vo;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author: zcbin
 * @title: EmailMessage
 * @packageName: com.zcb.minimallwxapi.dto
 * @projectName: minimall
 * @description: email消息
 * @date: 2020/5/26 11:02
 */
public class EmailMessage {
    private String receiver; //接收者
    private String subject; //主题
    private String content; //内容
    private String[] cc; //抄送
    private LocalDateTime localDateTime;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", cc=" + Arrays.toString(cc) +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
