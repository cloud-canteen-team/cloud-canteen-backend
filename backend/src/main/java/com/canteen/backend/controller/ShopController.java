package com.canteen.backend.controller;

import com.canteen.backend.common.Result;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 解决前端 Vue 跨域请求的问题，直接加上这个注解！
@CrossOrigin
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @GetMapping("/{shopId}/items")
    public Result<List<Map<String, Object>>> getShopItems(@PathVariable Long shopId) {

        List<Map<String, Object>> mockItemList = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("id", 1);
        item1.put("name", "红烧肉盖饭");
        item1.put("price", new BigDecimal("15.00"));
        item1.put("stock", 50);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 4);
        item2.put("name", "小份米饭");
        item2.put("price", new BigDecimal("1.00"));
        item2.put("stock", 500);

        mockItemList.add(item1);
        mockItemList.add(item2);

        return Result.success(mockItemList);
    }
}