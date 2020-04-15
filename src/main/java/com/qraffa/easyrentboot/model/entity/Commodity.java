package com.qraffa.easyrentboot.model.entity;

import lombok.Data;

import java.util.List;

/***
 * 商品
 */
@Data
public class Commodity {
    // 商品id
    private Integer cid;
    // 卖家id
    private Integer userId;
    // 标题
    private String title;
    // 商品详细描述
    private String detail;
    // 押金
    private Double deposit;
    // 租金
    private Double rent;
    //出租时间
    private Long rentalTime;
    // 要求归还时间
    private Long requestTime;
    // 商品发布时间
    private Long shelfTime;
    // 商品状态
    private Integer status;

    // 商品图片列表
    private List<CommodityPic> commodityPicList;
}
