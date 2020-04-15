package com.qraffa.easyrentboot.model.req.message;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetMessageListReq {
    @NotNull
    private Integer receiverId;
}
