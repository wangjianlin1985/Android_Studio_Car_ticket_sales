/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : car_ticket_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-03-15 18:19:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `guestbook`;
CREATE TABLE `guestbook` (
  `guestBookId` int(11) NOT NULL auto_increment,
  `title` varchar(40) default NULL,
  `content` varchar(200) default NULL,
  `userObj` varchar(20) default NULL,
  `addTime` varchar(20) default NULL,
  PRIMARY KEY  (`guestBookId`),
  KEY `FK427950C1C80FC67` (`userObj`),
  CONSTRAINT `FK427950C1C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of guestbook
-- ----------------------------
INSERT INTO `guestbook` VALUES ('1', '你好啊', '这个客户端不错', 'user1', '2019-06-05 12:11:13');
INSERT INTO `guestbook` VALUES ('2', 'hafa', 'afaaf', 'user1', '2019-06-12 09:17:26');
INSERT INTO `guestbook` VALUES ('3', 'aaa', 'bbbb', 'user3', '2019-06-13 13:57:53');
INSERT INTO `guestbook` VALUES ('4', '这个网站不错呢', '我以后可以用手机订票了', 'user3', '2019-06-13 13:59:05');

-- ----------------------------
-- Table structure for `newsinfo`
-- ----------------------------
DROP TABLE IF EXISTS `newsinfo`;
CREATE TABLE `newsinfo` (
  `newsId` int(11) NOT NULL auto_increment,
  `newsTitle` varchar(30) default NULL,
  `newsContent` varchar(200) default NULL,
  `newsDate` datetime default NULL,
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of newsinfo
-- ----------------------------
INSERT INTO `newsinfo` VALUES ('1', '订票手机客户端上线了', '欢迎大家下载手机客户端进行订票\r\n\r\n', '2019-02-06 00:00:00');

-- ----------------------------
-- Table structure for `orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `orderId` int(11) NOT NULL auto_increment,
  `userObj` varchar(20) default NULL,
  `planeObj` int(11) default NULL,
  `planeNumber` varchar(20) default NULL,
  `startStation` int(11) default NULL,
  `endStation` int(11) default NULL,
  `startDate` datetime default NULL,
  `seatType` int(11) default NULL,
  `seatInfo` varchar(40) default NULL,
  `totalPrice` float default NULL,
  `startTime` varchar(20) default NULL,
  `endTime` varchar(20) default NULL,
  PRIMARY KEY  (`orderId`),
  KEY `FK601628FC8F5605A0` (`seatType`),
  KEY `FK601628FC2633903` (`planeObj`),
  KEY `FK601628FC944F8752` (`startStation`),
  KEY `FK601628FCC80FC67` (`userObj`),
  KEY `FK601628FCA9E278B9` (`endStation`),
  KEY `FK601628FC6D6C1E83` (`planeObj`),
  CONSTRAINT `FK601628FC2633903` FOREIGN KEY (`planeObj`) REFERENCES `planeinfo` (`seatId`),
  CONSTRAINT `FK601628FC6D6C1E83` FOREIGN KEY (`planeObj`) REFERENCES `planeinfo` (`seatId`),
  CONSTRAINT `FK601628FC8F5605A0` FOREIGN KEY (`seatType`) REFERENCES `seattype` (`seatTypeId`),
  CONSTRAINT `FK601628FC944F8752` FOREIGN KEY (`startStation`) REFERENCES `stationinfo` (`stationId`),
  CONSTRAINT `FK601628FCA9E278B9` FOREIGN KEY (`endStation`) REFERENCES `stationinfo` (`stationId`),
  CONSTRAINT `FK601628FCC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES ('6', 'user1', '1', 'CA1333', '1', '2', '2019-04-24 00:00:00', '1', '一等座2张:6号  7号  ', '137', '中午12点8分', '当日下午5点32分');
INSERT INTO `orderinfo` VALUES ('7', 'user1', '1', 'CA1333', '1', '2', '2019-04-24 00:00:00', '1', '一等座1张:8号  ', '68.5', '中午12点8分', '当日下午5点32分');
INSERT INTO `orderinfo` VALUES ('8', 'user3', '3', 'CA1349', '3', '4', '2019-06-28 00:00:00', '3', '软座2张:4号  5号  ', '246', '下午3:28', '当日下午5:42');
INSERT INTO `orderinfo` VALUES ('9', 'user1', '1', 'CA1333', '1', '2', '2019-06-19 00:00:00', '1', '一等座1张:9号  ', '68.5', '中午12点8分', '当日下午5点32分');

-- ----------------------------
-- Table structure for `planeinfo`
-- ----------------------------
DROP TABLE IF EXISTS `planeinfo`;
CREATE TABLE `planeinfo` (
  `seatId` int(11) NOT NULL auto_increment,
  `planeNumber` varchar(20) default NULL,
  `startStation` int(11) default NULL,
  `endStation` int(11) default NULL,
  `startDate` datetime default NULL,
  `seatType` int(11) default NULL,
  `price` float default NULL,
  `seatNumber` int(11) default NULL,
  `leftSeatNumber` int(11) default NULL,
  `startTime` varchar(20) default NULL,
  `endTime` varchar(20) default NULL,
  `totalTime` varchar(20) default NULL,
  PRIMARY KEY  (`seatId`),
  KEY `FKAD54A6B68F5605A0` (`seatType`),
  KEY `FKAD54A6B6944F8752` (`startStation`),
  KEY `FKAD54A6B6A9E278B9` (`endStation`),
  KEY `FKF505450A8F5605A0` (`seatType`),
  KEY `FKF505450A944F8752` (`startStation`),
  KEY `FKF505450AA9E278B9` (`endStation`),
  CONSTRAINT `FKAD54A6B68F5605A0` FOREIGN KEY (`seatType`) REFERENCES `seattype` (`seatTypeId`),
  CONSTRAINT `FKAD54A6B6944F8752` FOREIGN KEY (`startStation`) REFERENCES `stationinfo` (`stationId`),
  CONSTRAINT `FKAD54A6B6A9E278B9` FOREIGN KEY (`endStation`) REFERENCES `stationinfo` (`stationId`),
  CONSTRAINT `FKF505450A8F5605A0` FOREIGN KEY (`seatType`) REFERENCES `seattype` (`seatTypeId`),
  CONSTRAINT `FKF505450A944F8752` FOREIGN KEY (`startStation`) REFERENCES `stationinfo` (`stationId`),
  CONSTRAINT `FKF505450AA9E278B9` FOREIGN KEY (`endStation`) REFERENCES `stationinfo` (`stationId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of planeinfo
-- ----------------------------
INSERT INTO `planeinfo` VALUES ('1', 'CA1333', '1', '2', '2019-06-19 00:00:00', '1', '68.5', '600', '591', '中午12点8分', '当日下午5点32分', '5个半小时');
INSERT INTO `planeinfo` VALUES ('2', 'CA2342', '1', '2', '2019-06-20 00:00:00', '2', '113.5', '300', '300', '中午12点8分', '下午5点32分', '5个半小时');
INSERT INTO `planeinfo` VALUES ('3', 'CA1349', '3', '4', '2019-06-28 00:00:00', '3', '123', '680', '675', '下午3:28', '当日下午5:42', '2个小时14分');
INSERT INTO `planeinfo` VALUES ('4', 'CA1535', '3', '4', '2019-06-21 00:00:00', '4', '97', '790', '790', '下午3:28', '当日下午5:42', '2个小时14分');

-- ----------------------------
-- Table structure for `recharge`
-- ----------------------------
DROP TABLE IF EXISTS `recharge`;
CREATE TABLE `recharge` (
  `id` int(11) NOT NULL auto_increment,
  `userObj` varchar(20) default NULL,
  `money` float default NULL,
  `chargeTime` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD3CCE2C7C80FC67` (`userObj`),
  CONSTRAINT `FKD3CCE2C7C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recharge
-- ----------------------------
INSERT INTO `recharge` VALUES ('1', 'user1', '1000', '2019-02-12 18:13:07');
INSERT INTO `recharge` VALUES ('2', 'user2', '1122', '2019-02-12 19:33:25');
INSERT INTO `recharge` VALUES ('4', 'user3', '2000', '2019-06-15 00:45:23');

-- ----------------------------
-- Table structure for `seattype`
-- ----------------------------
DROP TABLE IF EXISTS `seattype`;
CREATE TABLE `seattype` (
  `seatTypeId` int(11) NOT NULL auto_increment,
  `seatTypeName` varchar(20) default NULL,
  PRIMARY KEY  (`seatTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seattype
-- ----------------------------
INSERT INTO `seattype` VALUES ('1', '一等座');
INSERT INTO `seattype` VALUES ('2', '二等座');
INSERT INTO `seattype` VALUES ('3', '软座');
INSERT INTO `seattype` VALUES ('4', '硬座');

-- ----------------------------
-- Table structure for `stationinfo`
-- ----------------------------
DROP TABLE IF EXISTS `stationinfo`;
CREATE TABLE `stationinfo` (
  `stationId` int(11) NOT NULL auto_increment,
  `stationName` varchar(20) default NULL,
  `connectPerson` varchar(20) default NULL,
  `telephone` varchar(30) default NULL,
  `postcode` varchar(20) default NULL,
  `address` varchar(20) default NULL,
  PRIMARY KEY  (`stationId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stationinfo
-- ----------------------------
INSERT INTO `stationinfo` VALUES ('1', '成都十陵汽车站', '黄小东', '028-82695123', '610057', '成都北二环53号');
INSERT INTO `stationinfo` VALUES ('2', '重庆江北汽车站', '杨小明', '0818-66839842', '635311', '达州北站北路11号');
INSERT INTO `stationinfo` VALUES ('3', '福州长乐汽车站', '吴明明', '028-68108391', '610044', '成都东三环外侧');
INSERT INTO `stationinfo` VALUES ('4', '重庆西门汽车站', '黄慧', '023-81934312', '650183', '重庆站西路13号');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `realName` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `birthday` datetime default NULL,
  `cardNumber` varchar(20) default NULL,
  `city` varchar(20) default NULL,
  `money` float(20,2) default NULL,
  `photo` varchar(50) default NULL,
  `address` varchar(20) default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', 'xiaolin', '男', '1994-02-17 00:00:00', '513030199402175931', '四川达州', '2027.85', 'upload/39c69d87-d091-4be8-b496-3b4df76110a5.jpg', '四川成都二仙桥113号');
INSERT INTO `userinfo` VALUES ('user2', '123', '小敏', '女', '1997-02-19 00:00:00', '513030198702194423', '四川南充', '2885.00', 'upload/07822f55-8b1a-4059-a4ee-525d444ab495.jpg', '四川南充市滨江路112号');
INSERT INTO `userinfo` VALUES ('user3', '123', '邓小芳', '女', '1994-02-13 00:00:00', '513030198402134212', '福建福州', '1954.00', 'upload/677b59b1-4882-4c8b-9f6f-6e31cdd8cb79.jpg', '福州建安路113号');
