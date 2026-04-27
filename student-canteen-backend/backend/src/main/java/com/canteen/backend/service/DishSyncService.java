package com.canteen.backend.service;

import com.canteen.backend.dto.ExternalDishDTO;
import com.canteen.backend.dto.ExternalResponse;
import com.canteen.backend.entity.Item;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishSyncService {

    private final WebClient webClient;

    // 🌟 在构造函数中初始化 WebClient，指向外部 5000 端口
    public DishSyncService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:5000")
                .build();
    }

    /**
     * 核心方法：从外部 API 获取数据并转换为本地 Item 实体
     */
    public List<Item> fetchExternalItems() {
        try {
            // 1. 发起 GET 请求并接收 JSON
            ExternalResponse<List<ExternalDishDTO>> response = webClient.get()
                    .uri("/api/dishes")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ExternalResponse<List<ExternalDishDTO>>>() {})
                    .block(); // 阻塞式等待结果

            if (response != null && response.getCode() == 200 && response.getData() != null) {
                // 2. 调用下面的转换方法，将 DTO 转为数据库能用的 Entity
                return convertToEntityList(response.getData());
            }
        } catch (Exception e) {
            System.err.println(">>> [错误] 无法连接到外部接口: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * 映射逻辑：将外部字段“翻译”为本地字段
     */
    private List<Item> convertToEntityList(List<ExternalDishDTO> dtoList) {
        return dtoList.stream().map(dto -> {
            Item item = new Item();
            item.setId(dto.getId());
            item.setName(dto.getName());
            item.setPrice(dto.getPrice());
            // 🌟 自动翻译：得益于 application.properties 的配置，
            // 外部 current_stock 已自动映射到了 DTO 的 currentStock 变量中
            item.setStock(dto.getCurrentStock());
            // 状态转换：active -> 1, 其他 -> 0
            item.setStatus("active".equals(dto.getStatus()) ? 1 : 0);

            // 补充本地必要字段
            item.setShopId(1L);
            item.setVersion(0);
            return item;
        }).collect(Collectors.toList());
    }
}