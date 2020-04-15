package com.qraffa.easyrentboot.model.req.favorite;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetFavoriteListReq {
    @NotNull
    private Integer pno;
    @NotNull
    private Integer psize;
}
