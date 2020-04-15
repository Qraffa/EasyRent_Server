package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostLoginReq {
    @NotBlank
    private String uname;
    @NotBlank
    private String upwd;
}
