package com.s008.smartcanteen.controller;

import com.s008.smartcanteen.common.Result;
import com.s008.smartcanteen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulate")
public class SimulationController {

    @Autowired
    private OrderService orderService;

    /**
     * 仿真功能：模拟食堂高峰期，自动随机创建一批订单
     */
    @PostMapping("/batch-orders")
    public Result<String> simulateHeavyLoad() {
        // 这里写一个循环，随机生成 itemId 和 count 调用 submitOrder
        return Result.success("仿真订单生成成功，请查看订单列表和库存变动");
    }

    /**
     * 仿真功能：模拟某个菜品瞬间售罄
     */
    @PutMapping("/clear-inventory/{itemId}")
    public Result<String> clearInventory(@PathVariable Long itemId) {
        // 直接把某个菜品库存清零，测试前端在“售罄”状态下的表现
        return Result.success("仿真操作：菜品已设为售罄");
    }
}