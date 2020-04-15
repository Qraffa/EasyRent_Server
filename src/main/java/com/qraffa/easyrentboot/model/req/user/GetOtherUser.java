package com.qraffa.easyrentboot.model.req.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetOtherUser {

    @NotNull
    private String uname;
}
