package com.qraffa.easyrentboot.model.req.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PutOrderReq {
    @NotNull
    private Integer oid;
    private Long actualRentalTime;
    private Long actualTime;
    @NotNull
    private Integer status;
}
