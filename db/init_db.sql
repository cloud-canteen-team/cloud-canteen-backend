/*
 Navicat MySQL Data Transfer
 Target Server Type    : MySQL
 Project              : Canteen Management System (canteen_db)
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 确保使用正确的数据库
CREATE DATABASE IF NOT EXISTS `canteen_db`;
USE `canteen_db`;

-- ----------------------------
-- 1. 店铺信息表：管理食堂档口基本信息
-- ----------------------------
DROP TABLE IF EXISTS `s_shop`;
CREATE TABLE `s_shop` (
                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                          `shop_name` VARCHAR(50) NOT NULL COMMENT '档口名称',
                          `location` VARCHAR(100) COMMENT '具体位置（如：一楼东侧）',
                          `contact` VARCHAR(20) COMMENT '联系电话',
                          `is_open` TINYINT DEFAULT 1 COMMENT '0:休息中, 1:营业中',
                          `announcement` VARCHAR(255) COMMENT '店铺公告（如：今日特价红烧肉）',
                          `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺信息表';

-- ----------------------------
-- 2. 用户表：支持余额查询与消费账单
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                          `student_id` VARCHAR(20) UNIQUE NOT NULL COMMENT '学号',
                          `password` VARCHAR(100) NOT NULL,
                          `balance` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '校园卡余额',
                          `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生用户表';

-- ----------------------------
-- 3. 菜品表：自主精细化运营，管理库存与单价
-- ----------------------------
DROP TABLE IF EXISTS `c_item`;
CREATE TABLE `c_item` (
                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                          `shop_id` BIGINT DEFAULT 1 COMMENT '关联店铺ID',
                          `name` VARCHAR(50) NOT NULL COMMENT '菜品名称',
                          `price` DECIMAL(10, 2) NOT NULL COMMENT '单价',
                          `stock` INT NOT NULL COMMENT '每日初始库存/当前余量',
                          `status` TINYINT DEFAULT 1 COMMENT '0:下架, 1:上架',
                          `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品库存表';

-- ----------------------------
-- 4. 订单主表：驱动商家看板的流转
-- ----------------------------
DROP TABLE IF EXISTS `o_order`;
CREATE TABLE `o_order` (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `order_no` VARCHAR(50) UNIQUE NOT NULL COMMENT '订单号',
                           `user_id` BIGINT NOT NULL COMMENT '下单学生ID',
                           `shop_id` BIGINT DEFAULT 1 COMMENT '关联店铺ID',
                           `total_price` DECIMAL(10, 2) NOT NULL,
                           `status` TINYINT DEFAULT 0 COMMENT '0:待制作, 1:制作中, 2:待取餐, 3:已核销, 4:已取消',
                           `wait_time_est` INT DEFAULT 0 COMMENT '预估等待时间(分钟)',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- ----------------------------
-- 5. 订单明细表：处理口味定制标签
-- ----------------------------
DROP TABLE IF EXISTS `o_order_item`;
CREATE TABLE `o_order_item` (
                                `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                `order_id` BIGINT NOT NULL COMMENT '关联订单ID',
                                `item_id` BIGINT NOT NULL COMMENT '关联菜品ID',
                                `quantity` INT DEFAULT 1,
                                `flavor_tag` VARCHAR(100) COMMENT '口味定制：如少盐、少油'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情明细';

-- ----------------------------
-- 6. 余额流水表：支撑账单查询功能
-- ----------------------------
DROP TABLE IF EXISTS `u_balance_log`;
CREATE TABLE `u_balance_log` (
                                 `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                 `amount` DECIMAL(10, 2) NOT NULL COMMENT '变动金额',
                                 `type` TINYINT COMMENT '1:充值, 2:消费',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额变动流水';


-- ============================================================
-- 初始测试数据填充
-- ============================================================

-- 1. 初始化店铺
INSERT INTO `s_shop` (`shop_name`, `location`, `contact`, `announcement`)
VALUES ('一号窗口·家常便饭', '学生食堂二楼', '13800000000', '欢迎光临，新开业满20减2！');

-- 2. 初始化菜品
INSERT INTO `c_item` (`name`, `price`, `stock`, `status`, `shop_id`) VALUES
                                                                         ('红烧肉盖饭', 15.00, 50, 1, 1),
                                                                         ('香菇滑鸡', 13.50, 30, 1, 1),
                                                                         ('西红柿炒鸡蛋', 8.00, 100, 1, 1),
                                                                         ('小份米饭', 1.00, 500, 1, 1);

-- 3. 初始化学生用户
INSERT INTO `u_user` (`student_id`, `password`, `balance`) VALUES
                                                               ('20260001', '123456', 500.00),
                                                               ('20260002', '123456', 0.50);

-- 4. 初始化正常订单（待制作）
INSERT INTO `o_order` (`order_no`, `user_id`, `shop_id`, `total_price`, `status`)
VALUES ('ORD202603210001', 1, 1, 16.00, 0);

INSERT INTO `o_order_item` (`order_id`, `item_id`, `quantity`, `flavor_tag`) VALUES
                                                                                 (1, 1, 1, '少盐'),
                                                                                 (1, 4, 1, '正常');

-- 5. 初始化异常订单（已取消）
INSERT INTO `o_order` (`order_no`, `user_id`, `shop_id`, `total_price`, `status`)
VALUES ('ORD202603219999', 2, 1, 15.00, 4);

SET FOREIGN_KEY_CHECKS = 1;