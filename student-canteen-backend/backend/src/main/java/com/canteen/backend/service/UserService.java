package com.canteen.backend.service;

import com.canteen.backend.entity.User;
import com.canteen.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户余额
     */
    public BigDecimal getUserBalance(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getBalance();
    }

    /**
     * 绑定校园卡
     */
    public boolean bindCampusCard(Long userId, String cardNo) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 如果已经绑定过了，可以加拦截逻辑（这里为了演示灵活，允许直接覆盖）
        int rows = userMapper.bindCampusCard(userId, cardNo);
        return rows > 0;
    }
}