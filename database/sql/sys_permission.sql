DROP TABLE IF EXISTS `smart_system_permission`;
CREATE TABLE `smart_system_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单模块ID\r\n',
  `url_perm` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL权限标识',
  `btn_perm` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `smart_system_permission` VALUES (1, '查看用户', 2, 'GET:/smart-auth/test2', 'sys:user:view', '2021-02-02 14:16:07','1', '1', '2021-06-16 22:25:24', 0);