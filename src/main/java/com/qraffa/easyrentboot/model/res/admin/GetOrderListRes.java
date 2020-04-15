package com.qraffa.easyrentboot.model.res.admin;

import com.qraffa.easyrentboot.model.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderListRes {
    private Integer totalOrders;
    private Integer totalPages;
    private Integer pno;
    private List<Order> orderList;
}
