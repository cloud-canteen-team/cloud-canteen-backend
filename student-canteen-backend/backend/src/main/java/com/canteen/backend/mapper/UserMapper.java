package com.canteen.backend.mapper;

import com.canteen.backend.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 根据 ID 查询用户完整信息（包括余额和卡号）
    @Select("SELECT * FROM u_user WHERE id = #{id}")
    User findById(Long id);

    // 绑定校园卡：根据用户 ID 更新卡号字段
    @Update("UPDATE u_user SET campus_card_no = #{campusCardNo} WHERE id = #{id}")
    int bindCampusCard(@Param("id") Long id, @Param("campusCardNo") String campusCardNo);

    // 安全扣款逻辑（利用数据库条件拦截超扣）
    @Update("UPDATE u_user SET balance = balance - #{amount} WHERE id = #{id} AND balance >= #{amount}")
    int deductBalance(@Param("id") Long id, @Param("amount") java.math.BigDecimal amount);
}