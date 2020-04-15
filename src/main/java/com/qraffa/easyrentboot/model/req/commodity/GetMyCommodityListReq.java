package com.qraffa.easyrentboot.model.req.commodity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetMyCommodityListReq {
    @NotNull
    private Integer pno;
    @NotNull
    private Integer psize;
    private String title;
}
