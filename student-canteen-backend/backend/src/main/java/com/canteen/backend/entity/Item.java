package com.canteen.backend.entity;

import java.math.BigDecimal;

public class Item {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Integer status; // 0-下架, 1-在售
    private Long shopId;
    private String flavors; // 口味选项（如：正常,少盐）
    private Integer version; // 🌟 核心：乐观锁版本号

    // --- Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getFlavors() { return flavors; }
    public void setFlavors(String flavors) { this.flavors = flavors; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}