package com.s008.smartcanteen.pojo.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class OrderStatsVO {
    private Integer todayOrderCount;    // 今日订单数
    private BigDecimal todayRevenue;    // 今日营收
    private Map<Integer, Integer> statusCounts; // 各状态订单数量统计 (状态 -> 数量)
}