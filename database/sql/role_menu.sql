DROP TABLE IF EXISTS `smart_system_role_menu`;
CREATE TABLE `smart_system_role_menu`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `smart_system_role_menu` VALUES (1,1001, 1, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (2,1001, 2, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (3,1001, 3, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (4,1001, 4, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (5,1001, 5, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (6,1001, 6, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_menu` VALUES (7,1001, 7, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);