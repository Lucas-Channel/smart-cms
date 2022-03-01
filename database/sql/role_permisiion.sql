DROP TABLE IF EXISTS `smart_system_role_permission`;
CREATE TABLE `smart_system_role_permission`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int NULL DEFAULT NULL COMMENT '资源id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of smart_system_role_permission
-- ----------------------------
INSERT INTO `smart_system_role_permission` VALUES (1001, 1);
