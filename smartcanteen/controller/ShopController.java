package com.s008.smartcanteen.controller;

import com.s008.smartcanteen.common.Result;
import com.s008.smartcanteen.mapper.ShopMapper;
import com.s008.smartcanteen.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopMapper shopMapper;

    // 获取所有档口列表
    @GetMapping
    public Result<List<Shop>> list() {
        // selectList(null) 会查询出 s_shop 表里的所有行
        List<Shop> shops = shopMapper.selectList(null);
        return Result.success(shops);
    }
}