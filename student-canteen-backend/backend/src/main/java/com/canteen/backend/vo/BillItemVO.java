package com.canteen.backend.vo;

import java.math.BigDecimal;

public class BillItemVO {
    private String itemName;  // 菜品名称
    private Integer quantity; // 购买数量
    private BigDecimal price; // 购买时的单价
    private String flavors;   // 选中的口味组合

    // --- Getter 和 Setter ---
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFlavors() {
        return flavors;
    }

    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }
}