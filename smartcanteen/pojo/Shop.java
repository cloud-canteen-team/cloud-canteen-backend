package com.s008.smartcanteen.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Shop {
    private Long id;
    private String shopName;
    private String location;
    private String contact;
    private Integer isOpen; // 0:休息中, 1:营业中
    private String announcement;
    private LocalDateTime createTime;
}