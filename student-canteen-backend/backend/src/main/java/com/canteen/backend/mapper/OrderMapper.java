package com.canteen.backend.mapper;

import com.canteen.backend.entity.Order;
import com.canteen.backend.entity.OrderItem;
import com.canteen.backend.vo.BillItemVO;
import com.canteen.backend.vo.BillVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 插入订单主表
    @Insert("INSERT INTO o_order(order_no, user_id, total_price, status, create_time) " +
            "VALUES(#{orderNo}, #{userId}, #{totalPrice}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(Order order);

    // 插入订单明细
    @Insert("INSERT INTO o_order_item(order_id, item_id, quantity, price, flavors) " +
            "VALUES(#{orderId}, #{itemId}, #{quantity}, #{price}, #{flavors})")
    int insertOrderItem(OrderItem orderItem);

    // 更新订单状态
    @Update("UPDATE o_order SET status = #{status} WHERE id = #{orderId}")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") Integer status);

    // 更新总价
    @Update("UPDATE o_order SET total_price = #{totalPrice} WHERE id = #{id}")
    int updateOrderPrice(Order order);

    // 根据 ID 查询订单信息（支付前校验用）
    @Select("SELECT * FROM o_order WHERE id = #{id}")
    Order findById(Long id);

    // 🌟 新增：查询账单主表（关联流水表和订单表）
    @Select("SELECT l.amount as payAmount, l.create_time as payTime, o.id as orderId, o.order_no as orderNo, o.status " +
            "FROM u_balance_log l " +
            "JOIN o_order o ON l.order_id = o.id " +
            "WHERE l.user_id = #{userId} AND l.type = 2 " +
            "ORDER BY l.create_time DESC")
    List<BillVO> findUserBills(Long userId);

    // 🌟 新增：查询账单明细（关联明细表和菜品表获取菜名）
    @Select("SELECT i.name as itemName, oi.quantity, oi.price, oi.flavors " +
            "FROM o_order_item oi " +
            "JOIN c_item i ON oi.item_id = i.id " +
            "WHERE oi.order_id = #{orderId}")
    List<BillItemVO> findBillItems(Long orderId);
}