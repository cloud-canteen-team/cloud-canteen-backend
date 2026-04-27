package com.canteen.backend.mapper;

import com.canteen.backend.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface ItemMapper {

    /**
     * 查询指定档口的所有菜品
     */
    @Select("SELECT * FROM c_item WHERE shop_id = #{shopId}")
    List<Item> findByShopId(Long shopId);

    /**
     * 🌟 新增：根据 ID 查询单个菜品信息（为了在下单时获取真实单价）
     */
    @Select("SELECT * FROM c_item WHERE id = #{id}")
    Item findById(Long id);

    /**
     * 数据库乐观锁扣减库存
     */
    @Update("UPDATE c_item SET stock = stock - #{quantity}, version = version + 1 " +
            "WHERE id = #{id} AND version = #{version} AND stock >= #{quantity}")
    int reduceStockWithLock(@Param("id") Long id,
                            @Param("quantity") Integer quantity,
                            @Param("version") Integer version);
}