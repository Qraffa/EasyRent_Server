package com.qraffa.easyrentboot.model.res.commodity;

import com.qraffa.easyrentboot.model.res.commodity.entity.ListCommodity;
import lombok.Data;

import java.util.List;

@Data
public class GetMyCommodityListRes {
    private Integer totalCommodities;
    private Integer totalPages;
    private Integer pno;
    private List<ListCommodity> commodityList;
}
