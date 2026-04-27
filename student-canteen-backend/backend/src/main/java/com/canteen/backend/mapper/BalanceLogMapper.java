package com.canteen.backend.mapper;

import com.canteen.backend.entity.BalanceLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceLogMapper {
    @Insert("INSERT INTO u_balance_log(user_id, amount, type, order_id, create_time) " +
            "VALUES(#{userId}, #{amount}, #{type}, #{orderId}, NOW())")
    int insertLog(BalanceLog log);
}