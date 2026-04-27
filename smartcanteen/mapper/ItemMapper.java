package com.s008.smartcanteen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemMapper {

    /**
     * 扣减库存（带乐观锁校验）
     * 只有当当前库存大于等于购买数量时才允许更新，防止超卖
     *
     * @param itemId 菜品ID
     * @param count  购买数量
     * @return 影响行数，1表示扣减成功，0表示库存不足
     */
    @Update("UPDATE s_item SET stock = stock - #{count} WHERE id = #{itemId} AND stock >= #{count}")
    int updateStock(@Param("itemId") Long itemId, @Param("count") Integer count);

}