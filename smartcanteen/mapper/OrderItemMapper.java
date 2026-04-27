package com.s008.smartcanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.s008.smartcanteen.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}