package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetCommodityListReq {
    @NotNull
    private Integer pno;
    @NotNull
    private Integer psize;
}
