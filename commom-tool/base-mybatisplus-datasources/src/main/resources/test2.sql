/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50533
Source Host           : localhost:3306
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50533
File Encoding         : 65001

Date: 2020-04-30 17:32:05
*/

drop database if exists `test2`;
create database `test2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `test2`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `orderId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `orderNo` varchar(64) DEFAULT NULL,
  `state` tinyint(4) DEFAULT '1' COMMENT '1: 未下单； 2 已下单，未支付；3 已支付，未成功；4 已支付，成功',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(20) DEFAULT NULL,
  `sage` int(11) DEFAULT NULL,
  `saddr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '1111', '1', 'qqqqq');
INSERT INTO `student` VALUES ('2', '1111', '2', 'wwwww');
INSERT INTO `student` VALUES ('3', '777', '3', '999');
INSERT INTO `student` VALUES ('4', '2222', '3', '65546');
INSERT INTO `student` VALUES ('5', 'name0001', '15', 'addr0001');
INSERT INTO `student` VALUES ('6', 'name0002', '16', 'addr0002');
INSERT INTO `student` VALUES ('7', 'name000111', '15', 'addr0001111');
INSERT INTO `student` VALUES ('8', 'name000222', '16', 'addr0002222');
INSERT INTO `student` VALUES ('9', 'name000111', '15', 'addr0001111');
INSERT INTO `student` VALUES ('10', 'name000111', '15', 'addr0001111');
