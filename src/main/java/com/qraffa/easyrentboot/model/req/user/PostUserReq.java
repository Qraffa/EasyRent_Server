package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostUserReq {
    @NotBlank
    private String uname;
    @NotBlank
    private String nickName;
    @NotBlank
    private String upwd;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Range(min = 0,max = 1)
    private Integer gender;
    @NotNull
    @Range(min = 0,max = 2)
    private Integer campus;
    @NotNull
    @Range(min = 0,max = 1)
    private Integer admin;
}
