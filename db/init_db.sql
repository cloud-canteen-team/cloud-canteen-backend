USE canteen_db; -- 这里的名字要和你之前创建的 Schema 名字一致
-- 1. 用户表：支持余额查询与消费账单
CREATE TABLE `u_user` (
                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                          `student_id` VARCHAR(20) UNIQUE NOT NULL COMMENT '学号',
                          `password` VARCHAR(100) NOT NULL,
                          `balance` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '校园卡余额',
                          `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '学生用户表';

-- 2. 菜品表：自主精细化运营，管理库存与单价 [cite: 13]
CREATE TABLE `c_item` (
                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                          `name` VARCHAR(50) NOT NULL COMMENT '菜品名称',
                          `price` DECIMAL(10, 2) NOT NULL COMMENT '单价',
                          `stock` INT NOT NULL COMMENT '每日初始库存/当前余量 [cite: 13]',
                          `status` TINYINT DEFAULT 1 COMMENT '0:下架, 1:上架',
                          `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '菜品库存表';

-- 3. 订单主表：驱动商家看板的流转（待制作 -> 制作中 -> 待取餐）
CREATE TABLE `o_order` (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `order_no` VARCHAR(50) UNIQUE NOT NULL COMMENT '订单号',
                           `user_id` BIGINT NOT NULL,
                           `total_price` DECIMAL(10, 2) NOT NULL,
                           `status` TINYINT DEFAULT 0 COMMENT '0:待制作, 1:制作中, 2:待取餐, 3:已核销 [cite: 14]',
                           `wait_time_est` INT DEFAULT 0 COMMENT '预估等待时间(分钟) [cite: 6, 49]',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '订单主表';

-- 4. 订单明细表：处理口味定制标签 [cite: 6]
CREATE TABLE `o_order_item` (
                                `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                `order_id` BIGINT NOT NULL,
                                `item_id` BIGINT NOT NULL,
                                `quantity` INT DEFAULT 1,
                                `flavor_tag` VARCHAR(100) COMMENT '口味定制：如少盐、少油 [cite: 6]'
) COMMENT '订单详情明细';

-- 5. 余额流水表：支撑账单查询功能 [cite: 47]
CREATE TABLE `u_balance_log` (
                                 `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 `user_id` BIGINT NOT NULL,
                                 `amount` DECIMAL(10, 2) NOT NULL COMMENT '变动金额',
                                 `type` TINYINT COMMENT '1:充值, 2:消费',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '余额变动流水';

-- 1. 往菜品表塞点吃的（满足商家管理端初始化需求） [cite: 13, 40]
INSERT INTO `c_item` (`name`, `price`, `stock`, `status`) VALUES
                                                              ('红烧肉盖饭', 15.00, 50, 1),
                                                              ('香菇滑鸡', 13.50, 30, 1),
                                                              ('西红柿炒鸡蛋', 8.00, 100, 1),
                                                              ('小份米饭', 1.00, 500, 1);

-- 2. 往用户表塞个“土豪”学生（满足学生端登录和余额查询需求） [cite: 9, 47]
INSERT INTO `u_user` (`student_id`, `password`, `balance`) VALUES
                                                               ('20260001', '123456', 500.00),
                                                               ('20260002', '123456', 0.50); -- 这个用来测试余额不足逻辑 [cite: 9]

-- 3. 手动造一个“待制作”的订单（让成员 B 明天打开商家看板就能看到活儿） [cite: 14, 45]
INSERT INTO `o_order` (`order_no`, `user_id`, `total_price`, `status`) VALUES
    ('ORD202603210001', 1, 16.00, 0);

-- 4. 关联订单详情（少盐需求测试） [cite: 6, 39]
INSERT INTO `o_order_item` (`order_id`, `item_id`, `quantity`, `flavor_tag`) VALUES
                                                                                 (1, 1, 1, '少盐'),
                                                                                 (1, 4, 1, '正常');
