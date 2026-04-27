package com.s008.smartcanteen.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;           // 订单ID
    private Long userId;       // 用户ID
    private Long itemId;       // 菜品ID
    private Integer count;     // 购买数量
    private Integer status;    // 状态：1-待取餐，2-已完成，0-已取消
    private LocalDateTime createTime; // 创建时间
}