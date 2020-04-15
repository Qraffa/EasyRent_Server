package com.qraffa.easyrentboot.model.req.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetOrderListReq {
    @NotNull
    private Integer pno;
    @NotNull
    private Integer psize;
}
