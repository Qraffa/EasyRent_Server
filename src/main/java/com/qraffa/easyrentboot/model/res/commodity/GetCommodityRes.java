package com.qraffa.easyrentboot.model.res.commodity;

import com.qraffa.easyrentboot.model.entity.Commodity;
import lombok.Data;

@Data
public class GetCommodityRes {
    private Commodity commodity;
    private String nickName;
}
