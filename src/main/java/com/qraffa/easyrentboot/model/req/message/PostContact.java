package com.qraffa.easyrentboot.model.req.message;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostContact {

    @NotNull
    private String token;

    @NotNull
    private String to;

    private String content;
}
