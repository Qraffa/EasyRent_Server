package com.qraffa.easyrentboot.model.res.commodity.entity;

import com.qraffa.easyrentboot.model.entity.Commodity;
import lombok.Data;

@Data
public class ListCommodity {
    private Commodity commodity;
    private String nickName;
}
