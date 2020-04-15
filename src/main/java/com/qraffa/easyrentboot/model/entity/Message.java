package com.qraffa.easyrentboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 消息
 */
@Data
public class Message {
    // 消息id
    private Integer mid;
    // 发送者id
    private Integer senderId;
    // 接受者id
    private Integer receiverId;
    // 消息内容
    private String content;
    // 发送时间
    private Long sendTime;
    // 消息状态
    private Integer status;
}
