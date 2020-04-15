package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PutUserPassword {
    @NotBlank
    private String upwd;
    @NotBlank
    private String npwd;
}
