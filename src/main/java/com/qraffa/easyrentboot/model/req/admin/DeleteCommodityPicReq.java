package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteCommodityPicReq {
    @NotNull
    private Integer pid;
}
