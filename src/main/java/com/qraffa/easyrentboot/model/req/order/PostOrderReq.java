package com.qraffa.easyrentboot.model.req.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostOrderReq {
    @NotNull
    private Integer sellerId;
    @NotNull
    private Integer commodityId;
    @NotNull
    private Long creationTime;
}
