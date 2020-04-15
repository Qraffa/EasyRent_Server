package com.qraffa.easyrentboot.model.res.order;

import com.qraffa.easyrentboot.model.entity.Order;
import lombok.Data;

@Data
public class GetOrderRes {
    private Order order;
    private String buyerNickName;
    private String sellerNickName;
}
