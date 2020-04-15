package com.qraffa.easyrentboot.model.entity;

import lombok.Data;

/***
 * 收藏夹
 */
@Data
public class Favorite {
    // 收藏夹物品id
    private Integer fid;
    // 所属用户id
    private Integer userId;
    // 商品
    private Commodity commodity;
}
