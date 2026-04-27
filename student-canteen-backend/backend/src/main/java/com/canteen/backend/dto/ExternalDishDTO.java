package com.canteen.backend.dto;

import java.math.BigDecimal;

/**
 * 🌟 这个类专门用来接收 5000 端口的数据
 * 因为加了全局 SNAKE_CASE 配置，这里的 current_stock 会自动变成 currentStock
 */
public class ExternalDishDTO {
    private Long id;
    private String name;
    private String imageEmoji; // 对应外部的 image_emoji
    private BigDecimal price;
    private Integer currentStock; // 对应外部的 current_stock
    private String status;

    // Getter 和 Setter (或者直接用 Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImageEmoji() { return imageEmoji; }
    public void setImageEmoji(String imageEmoji) { this.imageEmoji = imageEmoji; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}