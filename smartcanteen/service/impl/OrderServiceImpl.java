package com.s008.smartcanteen.service.impl;

import com.s008.smartcanteen.common.UserHolder;
import com.s008.smartcanteen.mapper.ItemMapper;
import com.s008.smartcanteen.mapper.OrderMapper;
import com.s008.smartcanteen.pojo.Order;
import com.s008.smartcanteen.pojo.dto.OrderDTO;
import com.s008.smartcanteen.pojo.vo.OrderStatsVO;
import com.s008.smartcanteen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public void submitOrder(OrderDTO orderDTO) {
        Long userId = UserHolder.getUser();
        int success = itemMapper.updateStock(orderDTO.getItemId(), orderDTO.getCount());
        if (success == 0) throw new RuntimeException("库存不足");

        Order order = new Order();
        order.setUserId(userId);
        order.setItemId(orderDTO.getItemId());
        order.setCount(orderDTO.getCount());
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(1);
        orderMapper.insert(order);
    }

    @Override
    public List<Order> getOrderListByStatus(Integer status) {
        return orderMapper.selectByStatus(status);
    }

    @Override
    public OrderStatsVO getStatistics() {
        OrderStatsVO vo = new OrderStatsVO();
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        vo.setTodayOrderCount(orderMapper.countOrders(start, end));
        vo.setTodayRevenue(new BigDecimal("0.00"));

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, orderMapper.countByStatus(0));
        map.put(1, orderMapper.countByStatus(1));
        map.put(2, orderMapper.countByStatus(2));
        vo.setStatusCounts(map);

        return vo;
    }

    /**
     * 实现动态排队时间算法
     */
    @Override
    public Integer getEstimatedWaitTime(Long shopId) {
        Integer pendingCount = orderMapper.selectPendingCountByShop(shopId);
        if (pendingCount == null || pendingCount == 0) {
            return 0;
        }
        // 核心算法：总份数 * 单份平均耗时 + 基础准备时间
        return (pendingCount * 3) + 2;
    }
}