package com.canteen.backend.controller;

import com.canteen.backend.service.OrderService;
import com.canteen.backend.service.UserService;
import com.canteen.backend.common.Result;
import com.canteen.backend.vo.BillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin // 🌟 关键：允许前端跨域访问，解决请求变红、数据 0B 的问题
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 1. 查询余额接口
     */
    @GetMapping("/{id}/balance")
    public Result<BigDecimal> getBalance(@PathVariable Long id) {
        // 打印一下日志，方便在控制台确认后端收到了请求
        System.out.println(">>> [后端] 正在查询用户 ID 为 " + id + " 的余额");
        BigDecimal balance = userService.getUserBalance(id);
        return Result.success(balance);
    }

    /**
     * 2. 绑定校园卡接口
     */
    @PostMapping("/{id}/bind")
    public Result<String> bindCard(@PathVariable Long id, @RequestParam String cardNo) {
        boolean success = userService.bindCampusCard(id, cardNo);
        if (success) {
            return Result.success("绑定成功");
        } else {
            return Result.error("绑定失败，请重试");
        }
    }

    /**
     * 3. 获取历史账单接口
     */
    @GetMapping("/{id}/bills")
    public Result<List<BillVO>> getUserBills(@PathVariable Long id) {
        List<BillVO> bills = orderService.getUserBills(id);
        return Result.success(bills);
    }
}