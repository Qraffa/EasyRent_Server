package com.qraffa.easyrentboot.model.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PutUserReq {
    @NotNull
    private Integer uid;
    private String nickName;
    private String upwd;
    private String email;
    private Integer gender;
    private Integer campus;
    private Integer admin;
}
