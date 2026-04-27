package com.s008.smartcanteen.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BalanceLog {
    private Long id;
    private Long userId;       // 用户ID
    private BigDecimal amount; // 变动金额
    private Integer type;      // 1:充值, 2:消费
    private LocalDateTime createTime;
}