package com.s008.smartcanteen.mapper;

import com.s008.smartcanteen.pojo.Order;
import org.apache.ibatis.annotations.*;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入新订单
     */
    @Insert("INSERT INTO s_order (user_id, item_id, count, status, create_time) " +
            "VALUES (#{userId}, #{itemId}, #{count}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    /**
     * 根据状态查询订单列表
     */
    @Select("SELECT * FROM s_order WHERE status = #{status}")
    List<Order> selectByStatus(Integer status);

    /**
     * 统计时间范围内的订单数
     */
    @Select("SELECT COUNT(*) FROM s_order WHERE create_time BETWEEN #{start} AND #{end}")
    Integer countOrders(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    /**
     * 根据状态统计订单数
     */
    @Select("SELECT COUNT(*) FROM s_order WHERE status = #{status}")
    Integer countByStatus(Integer status);

    /**
     * 【难点攻坚：排队时间算法核心查询】
     * 统计指定档口当前正在排队的菜品总份数
     */
    @Select("SELECT SUM(o.count) " +
            "FROM s_order o " +
            "JOIN s_item i ON o.item_id = i.id " +
            "WHERE i.shop_id = #{shopId} AND o.status = 1")
    Integer selectPendingCountByShop(@Param("shopId") Long shopId);
}