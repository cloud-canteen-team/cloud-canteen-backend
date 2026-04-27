package com.canteen.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceLog {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Integer type; // 1:充值, 2:消费
    private Long orderId;
    private LocalDateTime createTime;

    // --- Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}