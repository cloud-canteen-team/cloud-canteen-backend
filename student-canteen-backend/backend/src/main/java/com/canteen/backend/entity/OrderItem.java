package com.canteen.backend.entity;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Long orderId;
    private Long itemId;
    private Integer quantity;
    private BigDecimal price; // 下单时的单价（防止以后菜品涨价影响旧订单）
    private String flavors;  // 选中的具体口味

    // --- Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getFlavors() { return flavors; }
    public void setFlavors(String flavors) { this.flavors = flavors; }
}