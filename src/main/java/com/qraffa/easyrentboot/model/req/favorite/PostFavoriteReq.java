package com.qraffa.easyrentboot.model.req.favorite;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostFavoriteReq {
    @NotNull
    private Integer cid;
}
