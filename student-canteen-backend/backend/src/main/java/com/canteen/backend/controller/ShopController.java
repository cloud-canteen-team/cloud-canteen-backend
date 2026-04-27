package com.canteen.backend.controller;

import com.canteen.backend.common.Result;
import com.canteen.backend.entity.Item;
import com.canteen.backend.mapper.ItemMapper;
import com.canteen.backend.service.DishSyncService; // 🌟 引入新服务
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private DishSyncService dishSyncService; // 🌟 注入抓取逻辑服务

    /**
     * 接口 1：读取本地数据库菜品列表 (保留原有功能)
     */
    @GetMapping("/{shopId}/items")
    public Result<List<Item>> getShopItems(@PathVariable Long shopId) {
        return Result.success(itemMapper.findByShopId(shopId));
    }

    /**
     * 🌟 新增接口：读取外部 (5000端口) 菜品列表
     * 访问地址：GET http://localhost:8080/api/shops/external/items
     */
    @GetMapping("/external/items")
    public Result<List<Item>> getExternalItems() {
        // 调用 Service 去 5000 端口抓取并翻译数据
        List<Item> externalItems = dishSyncService.fetchExternalItems();
        return Result.success(externalItems);
    }

    /**
     * 接口 2：按口味加入购物车
     */
    @PostMapping("/cart/add")
    public Result<String> addToCart(@RequestBody Map<String, Object> params) {
        Long itemId = Long.valueOf(params.get("itemId").toString());
        String flavor = (String) params.get("flavor");

        System.out.println("收到购物车请求：菜品ID=" + itemId + "，口味偏好=" + flavor);
        return Result.success("已按口味[" + flavor + "]加入购物车");
    }
}