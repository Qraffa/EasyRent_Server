package com.qraffa.easyrentboot.model.entity;

import lombok.Data;

/***
 * 订单
 */
@Data
public class Order {
    // 订单id
    private Integer oid;
    // 买家id
    private Integer buyerId;
    // 卖家id
    private Integer sellerId;
    // 商品id
    private Integer commodityId;
    // 实际出租时间
    private Long actualRentalTime;
    // 实际归还时间
    private Long actualTime;
    //订单创建时间
    private Long creationTime;
    // 订单状态
    private Integer status;

    // 商品
    private Commodity commodity;
}
