package com.qraffa.easyrentboot.model.res.favorite;

import com.qraffa.easyrentboot.model.entity.Favorite;
import lombok.Data;

import java.util.List;

@Data
public class GetFavoriteListRes {
    private Integer totalCommodities;
    private Integer totalPages;
    private Integer pno;
    private List<Favorite> favoriteList;
}
