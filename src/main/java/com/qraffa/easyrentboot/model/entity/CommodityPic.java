package com.qraffa.easyrentboot.model.entity;

import lombok.Data;

/***
 * 商品图片
 */
@Data
public class CommodityPic {
    // 图片id
    private Integer pid;
    // 商品id
    private Integer commodityId;
    // 图片路径
    private String picPath;
}
