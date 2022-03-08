DROP TABLE IF EXISTS `smart_system_dict`;
CREATE TABLE `smart_system_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `dict_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0-正常 ,1-停用）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of smart_system_dict
-- ----------------------------
INSERT INTO `smart_system_dict` VALUES (1, '性别', 'gender', 1, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict` VALUES (2, '授权方式', 'grant_type', 1, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict` VALUES (3, '微服务列表', 'micro_service', 1, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict` VALUES (4, '请求方式', 'request_method', 1, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);

DROP TABLE IF EXISTS `smart_system_dict_item`;
CREATE TABLE `smart_system_dict_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项名称',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项值',
  `dict_id` bigint NULL DEFAULT null COMMENT '字典id',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0 停用 1正常）',
  `defaulted` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL,
  `creator_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updater_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of smart_system_dict_item
-- ----------------------------
INSERT INTO `smart_system_dict_item` VALUES (1, '男', '1', 1, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (2, '女', '2', 1, 2, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (3, '未知', '0', 1, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (6, '密码模式', 'password', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (7, '授权码模式', 'authorization_code', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (8, '客户端模式', 'client_credentials', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (9, '刷新模式', 'refresh_token', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (10, '简化模式', 'implicit', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (22, '短信模式', 'SMS', 2, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (11, '系统服务', 'smart-system', 3, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (12, '会员服务', 'smart-member', 3, 2, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (13, '商品服务', 'smart-product', 3, 3, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (14, '订单服务', 'smart-order', 3, 4, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (16, '不限', '*', 4, 1, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (17, 'GET', 'GET', 4, 2, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (18, 'POST', 'POST', 4, 3, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (19, 'PUT', 'PUT', 4, 4, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (20, 'DELETE', 'DELETE', 4, 5, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);
INSERT INTO `smart_system_dict_item` VALUES (21, 'PATCH', 'PATCH', 4, 6, 1, 0, NULL, '2022-02-06 19:03:32','-1', '-1', '2022-02-06 19:03:32',0);

