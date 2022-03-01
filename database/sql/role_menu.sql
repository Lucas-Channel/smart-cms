DROP TABLE IF EXISTS `smart_system_role_menu`;
CREATE TABLE `smart_system_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `smart_system_role_menu` VALUES (1001, 1);
INSERT INTO `smart_system_role_menu` VALUES (1001, 2);
INSERT INTO `smart_system_role_menu` VALUES (1001, 3);