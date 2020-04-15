package com.qraffa.easyrentboot.model.res.message;

import lombok.Data;

@Data
public class GetMessageListRes {
    private Integer mid;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Long sendTime;
    private Boolean isSender;
}
