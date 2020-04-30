/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50533
Source Host           : localhost:3306
Source Database       : test1

Target Server Type    : MYSQL
Target Server Version : 50533
File Encoding         : 65001

Date: 2020-04-30 17:32:17
*/

drop database if exists `test1`;
create database `test1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `test1`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '1111', '1', '11111');
INSERT INTO `person` VALUES ('2', '1111', '2', '22222');
INSERT INTO `person` VALUES ('3', '111', '1', '11111');
INSERT INTO `person` VALUES ('4', '222', '2', '22222');
INSERT INTO `person` VALUES ('5', '111', '1', '11111');
INSERT INTO `person` VALUES ('6', '222', '2', '22222');
INSERT INTO `person` VALUES ('7', '111', '1', '11111');
INSERT INTO `person` VALUES ('8', '111', '1', '11111');
INSERT INTO `person` VALUES ('9', '111', '1', '11111');
INSERT INTO `person` VALUES ('10', '222', '2', '22222');
INSERT INTO `person` VALUES ('11', 'name001', '12', 'addr001');
INSERT INTO `person` VALUES ('12', 'name002', '13', 'addr002');
INSERT INTO `person` VALUES ('15', 'name00111', '12', 'addr001111');
INSERT INTO `person` VALUES ('16', 'name00222', '13', 'addr002222');
INSERT INTO `person` VALUES ('17', 'name00111', '12', 'addr001111');
INSERT INTO `person` VALUES ('18', 'name00222', '13', 'addr002222');
INSERT INTO `person` VALUES ('19', 'name00111', '12', 'addr001111');
INSERT INTO `person` VALUES ('20', 'name00222', '13', 'addr002222');
INSERT INTO `person` VALUES ('21', 'name00111', '12', 'addr001111');
INSERT INTO `person` VALUES ('22', 'name00222', '13', 'addr002222');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL,
  `userName` varchar(32) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '张三', '2', '20');
