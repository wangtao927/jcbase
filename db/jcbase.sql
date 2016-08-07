/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50621
 Source Host           : localhost
 Source Database       : jcbase

 Target Server Version : 50621
 File Encoding         : utf-8

 Date: 07/03/2016 11:38:17 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `access_token`
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
  `uid` int(10) NOT NULL,
  `token` varchar(64) NOT NULL COMMENT 'app用户登陆凭证',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `im_token` varchar(64) DEFAULT NULL COMMENT '第三方IM登陆凭证',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `token_index` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `app_version`
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL COMMENT '更新说明',
  `create_time` datetime DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL COMMENT '外部下载地址',
  `nature_no` int(11) DEFAULT NULL COMMENT '自然版本号',
  `os` varchar(20) DEFAULT NULL COMMENT '系统类型android or ios',
  `url` varchar(200) DEFAULT NULL COMMENT '内部下载地址',
  `version_no` varchar(20) DEFAULT NULL COMMENT '版本号',
  `status` tinyint(4) DEFAULT NULL COMMENT '1-有效   0-无效',
  `is_force` tinyint(4) DEFAULT '0' COMMENT '是否强制升级 0:否 1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `dict_data`
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `value` varchar(255) NOT NULL COMMENT '对应的值',
  `remark` varchar(32) DEFAULT NULL,
  `seq` int(11) NOT NULL COMMENT '排序，由小到大',
  `update_time` int(10) NOT NULL,
  `dict_type_id` int(11) NOT NULL COMMENT '字典类型ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `dict_data`
-- ----------------------------
BEGIN;
INSERT INTO `dict_data` VALUES ('4', '诽谤辱骂', '1', '', '1', '1467514812', '3'), ('5', '淫秽色情', '2', '', '2', '1467514834', '3'), ('6', '垃圾广告', '3', '', '3', '1467514907', '3');
COMMIT;

-- ----------------------------
--  Table structure for `dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `remark` varchar(32) DEFAULT NULL,
  `update_time` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `dict_type`
-- ----------------------------
BEGIN;
INSERT INTO `dict_type` VALUES ('1', '兴趣爱好', '用户选择兴趣爱好', '0'), ('3', '举报内容', '举报内容2', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '系统用户ID',
  `from` varchar(255) DEFAULT NULL COMMENT '来源 url',
  `ip` varchar(22) DEFAULT NULL COMMENT '客户端IP',
  `url` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL COMMENT '记录时间',
  `err_msg` text COMMENT '异常信息',
  `err_code` int(10) DEFAULT '0' COMMENT '状态码，0：正常',
  `class_name` varchar(200) DEFAULT NULL COMMENT 'controller类名',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名',
  `start_time` datetime DEFAULT NULL COMMENT '操作时间',
  `spend_time` bigint(20) DEFAULT NULL COMMENT '耗时，毫秒',
  `params` text COMMENT '提供的参数',
  PRIMARY KEY (`id`),
  KEY `FK_sys_EVENT` (`uid`),
  CONSTRAINT `sys_log_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36767 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `sys_res`
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(111) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT 'am-icon-file',
  `seq` int(11) DEFAULT '1',
  `type` int(1) DEFAULT '2' COMMENT '1 功能 2 权限',
  `modifydate` timestamp NULL DEFAULT NULL,
  `enabled` int(1) DEFAULT '1' COMMENT '是否启用 1：启用  0：禁用',
  `level` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `sys_res`
-- ----------------------------
BEGIN;
INSERT INTO `sys_res` VALUES ('1', null, '系统管理', '系统管理', '', 'fa-cogs', '10', '1', null, '1', '0'), ('2', '1', '资源管理', null, '/sys/res', 'fa-list', '1', '1', null, '1', '1'), ('3', '1', '角色管理', null, '/sys/role', 'fa-list', '10', '1', null, '1', '1'), ('4', '1', '用户管理', null, '/sys/user', 'fa-list', '11', '1', null, '1', '1'), ('9', '4', '用户删除', null, '/sys/user/delete', 'fa-list', '1', '2', null, '1', '2'), ('12', '4', '搜索用户', null, '/sys/user/serach', 'fa-list', '1', '2', null, '1', '2'), ('18', '2', '资源删除', null, '/sys/res/delete', 'fa-list', '11', '2', null, '1', '0'), ('19', '2', '资源保存', null, '/sys/res/save', 'fa-list', '11', '2', null, '1', '0'), ('28', '3', '角色删除', null, '/sys/role/delete', 'fa-list', '11', '2', null, '1', '0'), ('29', '3', '角色保存', null, '/sys/role/save', 'fa-list', '11', '2', null, '1', '0'), ('63', '4', '冻结用户', null, '/sys/user/freeze', 'fa-list', '11', '2', null, '1', '0'), ('146', '4', '用户列表', null, '/sys/user/list', 'fa-list', '8', '2', null, '1', '0'), ('147', '4', '用户保存', null, '/sys/user/save', 'fa-list', '10', '2', null, '1', '0'), ('150', '1', '操作日志', null, '/sys/log', 'fa-list', '11', '1', null, '1', '0'), ('152', null, '控制台', '1234', '/', 'fa-desktop', '1', '1', '2015-02-10 16:09:40', '1', '0'), ('181', '1', '数据字典', null, '/dict', 'fa-list', '12', '1', null, '1', '0'), ('182', '181', '数据字典列表', null, '/dict/list', 'fa-list', '1', '2', null, '1', '0'), ('192', null, 'APP管理', null, '', 'fa-android', '9', '1', null, '1', '0'), ('193', '192', 'App版本管理', null, '/app', '', '1', '1', null, '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) NOT NULL DEFAULT '',
  `des` varchar(55) DEFAULT NULL,
  `seq` int(11) DEFAULT '1',
  `createdate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1' COMMENT '0-禁用  1-启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '1', '2015-05-05 14:24:26', '1'), ('56', '内容编辑', '内容编辑4', '1', null, '1'), ('57', '客服人员', '客服人员', '1', null, '1'), ('60', 'dd', 'ss ', '1', null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_res`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `res_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_ROLE_RES_RES_ID` (`res_id`),
  KEY `FK_sys_ROLE_RES_ROLE_ID` (`role_id`),
  CONSTRAINT `sys_role_res_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_res_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4164 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `sys_role_res`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_res` VALUES ('4141', '1', '1'), ('4142', '2', '1'), ('4143', '18', '1'), ('4144', '19', '1'), ('4145', '3', '1'), ('4146', '28', '1'), ('4147', '29', '1'), ('4148', '4', '1'), ('4149', '9', '1'), ('4150', '12', '1'), ('4151', '63', '1'), ('4152', '146', '1'), ('4153', '147', '1'), ('4154', '150', '1'), ('4155', '181', '1'), ('4156', '182', '1'), ('4157', '152', '1'), ('4162', '192', '1'), ('4163', '193', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `des` varchar(55) DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '#1 不在线 2.封号状态 ',
  `icon` varchar(255) DEFAULT '/images/guest.jpg',
  `email` varchar(50) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL COMMENT 'cookieid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_index` (`name`),
  UNIQUE KEY `token_index` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '6fc3b9ada8c1e50b400cd6998ff7be76ea3ae312', '1289', '1', '/sys/static/i/9.jpg', 'admin@admin.com', '2015-07-15 18:00:27', '1234567', '10mk9b7bhFNML9GYR8eRnaTWixEEd42G355l4kHzoX6nNeknUKUghHu6hmYRlnHe'), ('32', 'eason', '5d46f91596dc2f24c1535148f778af0a25612289', 'sfsf', '1', '/images/guest.jpg', '569165857@qq.com', '2016-06-12 23:54:24', '13332892938', null), ('34', 'test', '6fc3b9ada8c1e50b400cd6998ff7be76ea3ae312', 'dfsdfsdfddf ', '1', '/images/guest.jpg', '569165857@qq.com', '2016-07-02 22:24:50', '13332892938', 'hDEflmnlVituFC6hbfTzeze8RVSRseIfdgBAGkAU2RRh4c4kIrIYXTTuwEG2Caem');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SYSTME_USER_ROLE_USER_ID` (`user_id`),
  KEY `FK_SYSTME_USER_ROLE_ROLE_ID` (`role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=336 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('315', '1', '1'), ('332', '32', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
