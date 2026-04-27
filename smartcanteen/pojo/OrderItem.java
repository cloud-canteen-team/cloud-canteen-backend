package com.s008.smartcanteen.pojo;

import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;      // 关联订单ID
    private Long itemId;       // 关联菜品ID
    private Integer quantity;  // 数量
    private String flavorTag;  // 口味定制标签（如：少盐、少油）[cite: 6]
}