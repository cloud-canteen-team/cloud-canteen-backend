package com.s008.smartcanteen.service;

import com.s008.smartcanteen.pojo.Order;
import com.s008.smartcanteen.pojo.dto.OrderDTO;
import com.s008.smartcanteen.pojo.vo.OrderStatsVO;
import java.util.List;

public interface OrderService {
    void submitOrder(OrderDTO orderDTO);
    List<Order> getOrderListByStatus(Integer status);
    OrderStatsVO getStatistics();

    /**
     * 获取指定档口的预计排队时间
     */
    Integer getEstimatedWaitTime(Long shopId);
}