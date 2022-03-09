DROP TABLE IF EXISTS `smart_system_role_permission`;
CREATE TABLE `smart_system_role_permission`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint NULL DEFAULT NULL COMMENT '资源id',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

INSERT INTO `smart_system_role_permission` VALUES (1,1001, 1, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
INSERT INTO `smart_system_role_permission` VALUES (2,1001, 2, '2021-08-28 09:12:21','1','1', '2021-08-28 09:12:21',0);
