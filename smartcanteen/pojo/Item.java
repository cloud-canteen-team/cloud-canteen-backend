package com.s008.smartcanteen.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version; // 必须引入
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Item {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer inventory; // 库存字段

    @Version // 标记这是乐观锁的版本号
    private Integer version;
}