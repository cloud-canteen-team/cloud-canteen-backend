package com.canteen.backend.dto;

import java.util.List;

public class OrderRequest {
    private Long userId;
    private List<CartItem> items;

    // 内部类，代表购物车里的每一项
    public static class CartItem {
        private Long itemId;
        private Integer quantity;
        private String flavors;
        private Integer version; // 🌟 必须带上，用于乐观锁校验

        // Getter 和 Setter (建议直接生成)
        public Long getItemId() { return itemId; }
        public void setItemId(Long itemId) { this.itemId = itemId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getFlavors() { return flavors; }
        public void setFlavors(String flavors) { this.flavors = flavors; }
        public Integer getVersion() { return version; }
        public void setVersion(Integer version) { this.version = version; }
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}