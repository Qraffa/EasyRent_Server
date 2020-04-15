package com.qraffa.easyrentboot.model.req.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetOrderReq {
    @NotNull
    private Integer oid;
}
