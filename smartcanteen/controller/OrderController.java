package com.s008.smartcanteen.controller;

import com.s008.smartcanteen.common.Result;
import com.s008.smartcanteen.pojo.Order;
import com.s008.smartcanteen.pojo.dto.OrderDTO;
import com.s008.smartcanteen.pojo.vo.OrderStatsVO;
import com.s008.smartcanteen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. 下单
    @PostMapping("/orders/submit")
    public Result submit(@RequestBody OrderDTO orderDTO) {
        orderService.submitOrder(orderDTO);
        return Result.success(null);
    }

    // 2. 获取订单列表 (商家端/学生端通用)
    @GetMapping("/orders")
    public Result<List<Order>> getOrderList(@RequestParam Integer status) {
        List<Order> list = orderService.getOrderListByStatus(status);
        return Result.success(list);
    }

    // 3. 经营统计 (商家端)
    @GetMapping("/dashboard/stats")
    public Result<OrderStatsVO> getDashboardStats() {
        OrderStatsVO stats = orderService.getStatistics();
        return Result.success(stats);
    }

    /**
     * 4. 获取预估排队时间 (学生端 A 同学调用)
     */
    @GetMapping("/predict-time")
    public Result<Integer> getPredictTime(@RequestParam Long shopId) {
        Integer waitTime = orderService.getEstimatedWaitTime(shopId);
        return Result.success(waitTime);
    }
}