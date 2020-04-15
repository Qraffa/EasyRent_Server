package com.qraffa.easyrentboot.model.req.commodity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostCommodityReq {
    @NotNull
    private String title;
    private String detail;
    @NotNull
    private Double deposit;
    @NotNull
    private Double rent;
    @NotNull
    private Long rentalTime;
    @NotNull
    private Long requestTime;
    @NotNull
    private Long shelfTime;
    @NotNull
    private Integer status;
}
