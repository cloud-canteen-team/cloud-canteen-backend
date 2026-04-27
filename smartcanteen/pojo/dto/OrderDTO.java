package com.s008.smartcanteen.pojo.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long itemId;    // 菜品ID
    private Integer count;  // 购买数量
}