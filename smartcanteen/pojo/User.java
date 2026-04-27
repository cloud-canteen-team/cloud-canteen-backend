package com.s008.smartcanteen.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String studentId;   // 学号
    private String password;
    private BigDecimal balance; // 校园卡余额
    private LocalDateTime createTime;
}