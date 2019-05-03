/*
 Navicat MySQL Data Transfer

 Source Server         : q
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : esend

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 03/05/2019 19:47:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `fid` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fname` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `root` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1051', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');
INSERT INTO `file` VALUES ('1116', 'me.jpg', '.jpg');
INSERT INTO `file` VALUES ('1189', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');
INSERT INTO `file` VALUES ('1468', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');
INSERT INTO `file` VALUES ('1658', '??????0.2.docx', '.docx');
INSERT INTO `file` VALUES ('3891', 'me.jpg', '.jpg');
INSERT INTO `file` VALUES ('5222', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');
INSERT INTO `file` VALUES ('5734', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');
INSERT INTO `file` VALUES ('7282', 'C:\\Users\\12916\\Desktop\\8770.txt', '.txt');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '20161000331', '199807');
INSERT INTO `user` VALUES (2, '20171002222', '1997');
INSERT INTO `user` VALUES (3, '2016100', 'huang');
INSERT INTO `user` VALUES (4, '111', '222');
INSERT INTO `user` VALUES (5, '1232151q', '11111');
INSERT INTO `user` VALUES (6, '1232151q', '11111');

SET FOREIGN_KEY_CHECKS = 1;
