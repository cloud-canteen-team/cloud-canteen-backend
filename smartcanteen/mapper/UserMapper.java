package com.s008.smartcanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.s008.smartcanteen.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 这里继承了 BaseMapper 后，增删改查就全都有了，不用写 SQL
}