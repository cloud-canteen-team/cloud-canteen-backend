package com.canteen.backend.service;

import com.canteen.backend.dto.OrderRequest;
import com.canteen.backend.entity.BalanceLog;
import com.canteen.backend.entity.Item;
import com.canteen.backend.entity.Order;
import com.canteen.backend.entity.OrderItem;
import com.canteen.backend.mapper.BalanceLogMapper;
import com.canteen.backend.mapper.ItemMapper;
import com.canteen.backend.mapper.OrderMapper;
import com.canteen.backend.mapper.UserMapper;
import com.canteen.backend.vo.BillItemVO;
import com.canteen.backend.vo.BillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BalanceLogMapper balanceLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public Long placeOrder(OrderRequest request) {
        System.out.println("\n=== [Service] 开始处理下单事务 ===");

        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }

        var user = userMapper.findById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在，用户ID: " + request.getUserId());
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus(0);
        order.setTotalPrice(BigDecimal.ZERO);

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String randomSuffix = String.valueOf((int)(Math.random() * 900 + 100));
        order.setOrderNo("SN" + timestamp + randomSuffix);

        orderMapper.insertOrder(order);
        Long orderId = order.getId();
        BigDecimal runningTotal = BigDecimal.ZERO;

        for (OrderRequest.CartItem cartItem : request.getItems()) {
            System.out.println("\n>>> [明细检查] 正在处理菜品 ID: " + cartItem.getItemId());

            // 1. 获取数据库当前状态
            Item currentItem = itemMapper.findById(cartItem.getItemId());
            if (currentItem == null) {
                throw new RuntimeException("菜品不存在，ID: " + cartItem.getItemId());
            }

            // 2. 准备使用的版本号（容错处理）
            Integer versionToUse = cartItem.getVersion();
            if (versionToUse == null) {
                System.out.println("    [警告] 前端未传 version，自动同步数据库版本: " + currentItem.getVersion());
                versionToUse = currentItem.getVersion();
            }

            System.out.println("    [版本对比] 前端: " + versionToUse + " | 数据库: " + currentItem.getVersion());
            System.out.println("    [库存检查] 需求: " + cartItem.getQuantity() + " | 剩余: " + currentItem.getStock());

            // 3. 第一次尝试扣减
            int rows = itemMapper.reduceStockWithLock(
                    cartItem.getItemId(),
                    cartItem.getQuantity(),
                    versionToUse
            );

            // 🌟 4. 自动修复逻辑：如果第一次失败，深度诊断原因并尝试重试
            if (rows == 0) {
                System.err.println(">>> !!! 乐观锁拦截 !!! 进行详细诊断...");

                boolean versionMismatch = !versionToUse.equals(currentItem.getVersion());
                boolean stockInsufficient = currentItem.getStock() < cartItem.getQuantity();

                if (versionMismatch) {
                    System.err.println("    原因1: 版本号不匹配! (前端 " + versionToUse + " != 数据库 " + currentItem.getVersion() + ")");
                }
                if (stockInsufficient) {
                    System.err.println("    原因2: 库存不足! (剩余 " + currentItem.getStock() + " < 需求 " + cartItem.getQuantity() + ")");
                }

                // 🌟 如果只是版本号不对，且库存还够，后端帮前端重试一次最新版本
                if (versionMismatch && !stockInsufficient) {
                    System.out.println("    [自动修复] 检测到版本过期但库存充足，正在使用最新版本 V" + currentItem.getVersion() + " 重试...");
                    rows = itemMapper.reduceStockWithLock(
                            cartItem.getItemId(),
                            cartItem.getQuantity(),
                            currentItem.getVersion()
                    );

                    if (rows > 0) {
                        System.out.println("    [自动修复] 重试成功！业务继续进行。");
                    } else {
                        System.err.println("    [自动修复] 重试仍然失败，可能存在极高并发竞争");
                        throw new RuntimeException("系统繁忙，请稍后重试！");
                    }
                } else {
                    // 如果是库存真的不够，或者其他原因，坚决拦截
                    throw new RuntimeException("菜品库存不足或系统繁忙，请重试！");
                }
            }

            // 5. 扣减成功后，计算单项价格并写入明细
            BigDecimal itemPrice = currentItem.getPrice();
            runningTotal = runningTotal.add(itemPrice.multiply(new BigDecimal(cartItem.getQuantity())));

            OrderItem detail = new OrderItem();
            detail.setOrderId(orderId);
            detail.setItemId(cartItem.getItemId());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(itemPrice);
            detail.setFlavors(cartItem.getFlavors());
            orderMapper.insertOrderItem(detail);
        }

        // 6. 更新订单总价
        order.setTotalPrice(runningTotal);
        orderMapper.updateOrderPrice(order);
        System.out.println("\n>>> [成功] 订单生成完成，总额: " + runningTotal + " ===\n");

        return orderId;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null || order.getStatus() != 0) {
            throw new RuntimeException("订单不存在或已支付");
        }

        int rows = userMapper.deductBalance(order.getUserId(), order.getTotalPrice());
        if (rows == 0) {
            throw new RuntimeException("余额不足，请先充值！");
        }

        BalanceLog log = new BalanceLog();
        log.setUserId(order.getUserId());
        log.setAmount(order.getTotalPrice().negate());
        log.setType(2);
        log.setOrderId(orderId);
        balanceLogMapper.insertLog(log);

        orderMapper.updateOrderStatus(orderId, 1);
        return true;
    }

    public List<BillVO> getUserBills(Long userId) {
        List<BillVO> bills = orderMapper.findUserBills(userId);
        for (BillVO bill : bills) {
            bill.setItems(orderMapper.findBillItems(bill.getOrderId()));
        }
        return bills;
    }
}