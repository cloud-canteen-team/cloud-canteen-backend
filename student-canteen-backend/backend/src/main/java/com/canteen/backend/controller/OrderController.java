package com.canteen.backend.controller;

import com.canteen.backend.common.Result;
import com.canteen.backend.dto.OrderRequest;
import com.canteen.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin // 🌟 解决前后端分离时的跨域拦截问题
@RestController
@RequestMapping("/api/order") // 请确认你的前端请求路径是否匹配
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 下单核心接口
     */
    @PostMapping("/create")
    public Result<Long> createOrder(@RequestBody OrderRequest request) {
        try {
            // 🌟 核心日志：看看前端到底发了什么过来
            System.out.println(">>> [Controller] 收到下单请求，前端传来的 userId: " + request.getUserId());
            if (request.getItems() != null) {
                System.out.println(">>> [Controller] 购物车菜品种类数量: " + request.getItems().size());
            }

            Long orderId = orderService.placeOrder(request);
            return Result.success(orderId);
        } catch (RuntimeException e) {
            System.err.println(">>> [Controller] 下单失败: " + e.getMessage());
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    /**
     * 扣款支付接口
     */
    @PostMapping("/pay/{id}")
    public Result<String> pay(@PathVariable Long id) {
        try {
            System.out.println(">>> [Controller] 开始支付订单: " + id);
            boolean success = orderService.payOrder(id);
            if (success) {
                return Result.success("支付成功，订单已发送至后厨");
            }
            return Result.error("支付失败，订单状态异常");
        } catch (RuntimeException e) {
            System.err.println(">>> [Controller] 支付失败: " + e.getMessage());
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
}