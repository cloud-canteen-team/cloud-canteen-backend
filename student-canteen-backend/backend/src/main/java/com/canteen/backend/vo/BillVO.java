package com.canteen.backend.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillVO {
    private Long orderId;          // 订单ID
    private String orderNo;        // 订单流水号
    private BigDecimal payAmount;  // 实际支付/扣除金额
    private LocalDateTime payTime; // 扣款发生时间
    private Integer status;        // 订单状态 (1:待制作, 2:已完成等)

    // 🌟 核心：这笔账单包含的菜品明细列表
    private List<BillItemVO> items;

    // --- Getter 和 Setter ---
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BillItemVO> getItems() {
        return items;
    }

    public void setItems(List<BillItemVO> items) {
        this.items = items;
    }
}