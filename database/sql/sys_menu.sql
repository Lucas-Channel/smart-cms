DROP TABLE IF EXISTS `smart_menu`;
CREATE TABLE `smart_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由路径',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-禁用 1-开启',
  `remark` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

INSERT INTO `smart_menu` VALUES (1, '系统管理', 0, 'system', 'table', 1, 1, NULL, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_menu` VALUES (2, '用户管理', 1, 'user', 'user', 1, 1, NULL, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_menu` VALUES (3, '角色管理', 1, 'role', 'peoples', 2, 1, NULL, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_menu` VALUES (4, '菜单管理', 1, 'menu', 'tree-table', 3, 1, NULL, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_menu` VALUES (5, '部门管理', 1, 'dept', 'tree', 4, 1, NULL, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);