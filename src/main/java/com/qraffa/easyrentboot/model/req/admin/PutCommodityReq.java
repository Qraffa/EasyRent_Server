package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PutCommodityReq {
    @NotNull
    private Integer cid;
    private String title;
    private String detail;
    private Double deposit;
    private Double rent;
    private Long rentalTime;
    private Long requestTime;
    private Long shelfTime;
    private Integer status;
}
