SET FOREIGN_KEY_CHECKS=0;


DROP DATABASE IF EXISTS `listen`;

-- ----------------------------
-- Database structure for `listen`
-- ----------------------------
CREATE DATABASE `listen` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE listen;


-- --------------------------------------
-- Table structure for `user` 用户表
-- --------------------------------------
DROP TABLE if exists `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `pass_word` varchar(20) NOT NULL COMMENT '密码',
  `user_nick` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '生日',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '0未知;1为男,2为女',
  `user_img` varchar(100) DEFAULT NULL COMMENT '头像',
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `introduction` varchar(200) DEFAULT NULL COMMENT '个人简介',
  `mobile` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `reg_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `modify_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最近一次修改时间',
  `is_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用,0为注销;1为可用',
  `is_index` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用,0为注销;1为可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------------------
-- Table structure for `friend` 好友表
-- ----------------------------------------
DROP TABLE if exists `friend`;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '好友ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用。可用1；不可用0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ------------------------------------------------
-- Table structure for `third` 第三方授权表
-- ------------------------------------------------
DROP TABLE if exists `third`;
CREATE TABLE `third` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL,
  `type` int(3) NOT NULL COMMENT '来源名称,详见Src',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `meta_index_1` varchar(50) DEFAULT NULL,
  `meta_index_2` varchar(50) DEFAULT NULL,
  `meta_index_3` varchar(50) DEFAULT NULL,
  `meta_index_4` varchar(50) DEFAULT NULL,
  `meta_index_5` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- -----------------------------------------------
-- Table structure for `comment` 评论表
-- -----------------------------------------------
DROP TABLE if exists `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `depend_id` bigint(20) NOT NULL COMMENT '依附ID',
  `user_id` bigint(20) DEFAULT 0 COMMENT '用户ID',
  `re_user_id` bigint(20) DEFAULT 0 COMMENT '用户ID',
  `content` varchar(200) NOT NULL COMMENT '评论内容',
  `re_content` varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `re_user_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `comment_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '评论时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `type` int(2) NOT NULL COMMENT '类别',
  `is_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0为不可用;1为可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- -----------------------------------------------
-- Table structure for `author` 作者表
-- -----------------------------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `en_name` varchar(50) NOT NULL,
  `first_en_name` varchar(2) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_valid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8




-- -----------------------------------------------
-- Table structure for `music` 音乐表
-- -----------------------------------------------
CREATE TABLE `music` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `author` varchar(50) NOT NULL,
  `url` varchar(200) NOT NULL,
  `lrc` varchar(200) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_upload` tinyint(1) NOT NULL DEFAULT '0',
  `is_index` tinyint(1) NOT NULL DEFAULT '0',
  `is_valid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8