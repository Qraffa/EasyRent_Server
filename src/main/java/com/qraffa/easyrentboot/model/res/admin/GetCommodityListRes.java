package com.qraffa.easyrentboot.model.res.admin;

import com.qraffa.easyrentboot.model.entity.Commodity;
import lombok.Data;

import java.util.List;

@Data
public class GetCommodityListRes {
    private Integer totalCommodities;
    private Integer totalPages;
    private Integer pno;
    private List<Commodity> commodityList;
}
