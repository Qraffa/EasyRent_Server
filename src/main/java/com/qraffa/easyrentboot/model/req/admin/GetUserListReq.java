package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetUserListReq {
    @NotNull
    private Integer pno;
    @NotNull
    private Integer psize;
}
