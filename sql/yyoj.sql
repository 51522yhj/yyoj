/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 8.152.161.159:3306
 Source Schema         : yyoj

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 01/03/2025 20:38:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionId` bigint NULL DEFAULT NULL COMMENT '题目id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论内容',
  `commentId` bigint NULL DEFAULT NULL COMMENT '评论id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `likeCount` int NOT NULL DEFAULT 0 COMMENT '点赞数',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, '阿拉啦', NULL, 1, 0, '2025-02-04 23:19:37', 0);
INSERT INTO `comment` VALUES (2, 1880180436512104450, '阿拉啦', NULL, 1, 0, '2025-02-05 10:59:49', 0);
INSERT INTO `comment` VALUES (3, NULL, '阿拉啦？？？？', 2, 1, 0, '2025-02-05 11:00:14', 0);
INSERT INTO `comment` VALUES (4, 1880180436512104450, '阿拉啦，啊啦啦啦啦', NULL, 1, 1, '2025-02-05 11:00:35', 0);
INSERT INTO `comment` VALUES (5, NULL, '阿拉啦，啊啦啦啦啦', 2, 1, 1, '2025-02-05 11:46:02', 0);
INSERT INTO `comment` VALUES (6, NULL, '阿拉啦，啊啦啦啦啦', 3, 1, 1, '2025-02-05 11:46:29', 0);
INSERT INTO `comment` VALUES (7, 1880180436512104450, '26262', NULL, 1, 1, '2025-02-05 17:12:33', 0);
INSERT INTO `comment` VALUES (8, 1880180436512104450, '\n\n333', NULL, 1, 1, '2025-02-05 17:13:05', 0);
INSERT INTO `comment` VALUES (9, 1880180436512104450, '撒次', NULL, 1, 1, '2025-02-05 17:13:21', 0);
INSERT INTO `comment` VALUES (10, NULL, '好滴', 9, 1, 1, '2025-02-05 17:17:35', 0);
INSERT INTO `comment` VALUES (11, NULL, '111', 6, 1, 0, '2025-02-05 17:22:04', 0);
INSERT INTO `comment` VALUES (12, NULL, '222', 6, 1, 0, '2025-02-05 17:22:14', 0);
INSERT INTO `comment` VALUES (13, NULL, '？？', 9, 1, 1, '2025-02-05 17:43:14', 0);
INSERT INTO `comment` VALUES (14, 1880180436512104450, 'OK了', NULL, 1886478365837283329, 1, '2025-02-05 17:50:03', 0);
INSERT INTO `comment` VALUES (15, NULL, '大哥你好', 9, 1886478365837283329, 0, '2025-02-05 17:50:14', 0);
INSERT INTO `comment` VALUES (16, 1880621459012952065, '简单', NULL, 1, 1, '2025-02-05 18:54:20', 0);
INSERT INTO `comment` VALUES (17, NULL, '111', 14, 1, 0, '2025-02-06 17:56:08', 1);
INSERT INTO `comment` VALUES (18, NULL, 'OK\nL', 4, 1, 0, '2025-02-08 10:03:51', 1);
INSERT INTO `comment` VALUES (19, NULL, 'halo ', 10, 1, 0, '2025-02-14 22:11:25', 1);
INSERT INTO `comment` VALUES (20, NULL, '哈来来来', 10, 1, 0, '2025-02-17 17:46:30', 0);
INSERT INTO `comment` VALUES (21, NULL, '去去去', 20, 1, 0, '2025-02-18 00:34:38', 0);
INSERT INTO `comment` VALUES (22, NULL, '我委屈', 21, 1, 0, '2025-02-18 00:34:43', 0);
INSERT INTO `comment` VALUES (23, NULL, '钱王拳王', 22, 1, 0, '2025-02-18 00:34:48', 0);
INSERT INTO `comment` VALUES (24, NULL, '去去去', 23, 1, 0, '2025-02-18 00:34:53', 0);

-- ----------------------------
-- Table structure for comment_like
-- ----------------------------
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `commentId` bigint NULL DEFAULT NULL COMMENT '评论id',
  `userId` bigint NOT NULL COMMENT '点赞用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '点赞表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment_like
-- ----------------------------
INSERT INTO `comment_like` VALUES (1, 4, 1, '2025-02-05 14:15:48', 1);
INSERT INTO `comment_like` VALUES (2, 4, 1, '2025-02-05 14:23:14', 1);
INSERT INTO `comment_like` VALUES (3, 2, 1, '2025-02-05 14:23:20', 1);
INSERT INTO `comment_like` VALUES (4, 4, 1, '2025-02-05 14:23:26', 1);
INSERT INTO `comment_like` VALUES (5, 5, 1, '2025-02-05 14:23:28', 0);
INSERT INTO `comment_like` VALUES (6, 2, 1, '2025-02-05 14:44:59', 1);
INSERT INTO `comment_like` VALUES (7, 2, 1, '2025-02-05 14:45:24', 1);
INSERT INTO `comment_like` VALUES (8, 3, 1, '2025-02-05 14:45:43', 1);
INSERT INTO `comment_like` VALUES (9, 6, 1, '2025-02-05 14:45:44', 1);
INSERT INTO `comment_like` VALUES (10, 8, 1, '2025-02-05 17:13:10', 1);
INSERT INTO `comment_like` VALUES (11, 9, 1, '2025-02-05 17:17:38', 1);
INSERT INTO `comment_like` VALUES (12, 9, 1, '2025-02-05 17:17:39', 1);
INSERT INTO `comment_like` VALUES (13, 2, 1, '2025-02-05 17:17:43', 1);
INSERT INTO `comment_like` VALUES (14, 2, 1, '2025-02-05 17:17:44', 1);
INSERT INTO `comment_like` VALUES (15, 2, 1, '2025-02-05 17:17:57', 1);
INSERT INTO `comment_like` VALUES (16, 2, 1, '2025-02-05 17:19:51', 1);
INSERT INTO `comment_like` VALUES (17, 2, 1, '2025-02-05 17:19:53', 1);
INSERT INTO `comment_like` VALUES (18, 7, 1, '2025-02-05 17:20:44', 1);
INSERT INTO `comment_like` VALUES (19, 2, 1, '2025-02-05 17:21:01', 1);
INSERT INTO `comment_like` VALUES (20, 2, 1, '2025-02-05 17:22:19', 1);
INSERT INTO `comment_like` VALUES (21, 2, 1, '2025-02-05 17:29:56', 1);
INSERT INTO `comment_like` VALUES (22, 9, 1, '2025-02-05 17:32:06', 1);
INSERT INTO `comment_like` VALUES (23, 10, 1, '2025-02-05 17:32:42', 1);
INSERT INTO `comment_like` VALUES (24, 2, 1, '2025-02-05 17:32:46', 1);
INSERT INTO `comment_like` VALUES (25, 6, 1, '2025-02-05 17:32:48', 0);
INSERT INTO `comment_like` VALUES (26, 13, 1, '2025-02-05 17:43:16', 0);
INSERT INTO `comment_like` VALUES (27, 9, 1886478365837283329, '2025-02-05 17:50:05', 0);
INSERT INTO `comment_like` VALUES (28, 16, 1, '2025-02-05 18:54:23', 0);
INSERT INTO `comment_like` VALUES (29, 14, 1, '2025-02-06 17:55:55', 1);
INSERT INTO `comment_like` VALUES (30, 14, 1, '2025-02-06 17:55:57', 1);
INSERT INTO `comment_like` VALUES (31, 14, 1, '2025-02-06 17:56:17', 1);
INSERT INTO `comment_like` VALUES (32, 7, 1, '2025-02-06 17:56:19', 0);
INSERT INTO `comment_like` VALUES (33, 14, 1, '2025-02-06 22:43:37', 0);
INSERT INTO `comment_like` VALUES (34, 17, 1, '2025-02-06 22:43:38', 1);
INSERT INTO `comment_like` VALUES (35, 8, 1, '2025-02-06 22:43:40', 0);
INSERT INTO `comment_like` VALUES (36, 4, 1, '2025-02-08 10:03:43', 0);
INSERT INTO `comment_like` VALUES (37, 17, 1, '2025-02-14 22:11:14', 1);
INSERT INTO `comment_like` VALUES (38, 10, 1, '2025-02-14 22:11:16', 1);
INSERT INTO `comment_like` VALUES (39, 9, 1, '2025-02-15 14:28:32', 1);
INSERT INTO `comment_like` VALUES (40, 10, 1, '2025-02-17 09:46:24', 0);
INSERT INTO `comment_like` VALUES (41, 22, 1, '2025-02-23 14:23:30', 1);
INSERT INTO `comment_like` VALUES (42, 21, 1, '2025-02-23 16:03:29', 1);

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '竞赛描述',
  `competeTime` int NULL DEFAULT NULL COMMENT '竞赛时长',
  `beginTime` datetime NOT NULL COMMENT '创建时间',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '竞赛封面',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '审核状态',
  `participantLimit` int NOT NULL COMMENT '限制参与人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '竞赛表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES (1, '编程竞赛', '这是一场面向程序员的编程竞赛。', 120, '2023-10-01 17:00:00', '', 1, '2025-02-13 22:12:02', 0, 0, 100);
INSERT INTO `competition` VALUES (7, '编程竞赛题目', '这是一个编程竞赛题目，要求实现一个函数...', 120, '2023-10-01 20:00:00', '', 1, '2025-02-13 22:29:41', 0, 0, 100);
INSERT INTO `competition` VALUES (8, 'test1', '阿萨', 90, '2025-02-13 04:00:00', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/PyNViBUU-微信图片_20240328211434.jpg', 1, '2025-02-13 23:24:59', 0, 0, 100);
INSERT INTO `competition` VALUES (9, 'testDoing', '测试未开始的竞赛', 90, '2025-02-15 16:42:40', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/2wNwq78f-微信图片_20241213003008.jpg', 1, '2025-02-14 16:43:29', 0, 0, 100);
INSERT INTO `competition` VALUES (10, '天天', 'i和cN\n', 90, '2025-02-14 21:00:00', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/e9DVFV1k-e1deb6654b2a41806acea080ce2ce6c1.jpeg', 1, '2025-02-14 21:05:01', 0, 0, 100);
INSERT INTO `competition` VALUES (11, 'ttest', '急急急', 4440, '2025-02-23 15:00:00', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/rIN1OgsL-1298079510.jpg', 1, '2025-02-15 00:13:59', 0, 0, 100);
INSERT INTO `competition` VALUES (12, 'ttest', '急急急', 1440, '2025-02-16 15:00:00', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/rIN1OgsL-1298079510.jpg', 1, '2025-02-15 00:14:32', 0, 0, 100);
INSERT INTO `competition` VALUES (13, '未开始', '仨未开始', 90, '2025-02-18 15:29:50', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/contest_cover/1/ZiCJ4iA1-20220731183853_afbb8.jpg', 1, '2025-02-16 07:30:28', 0, 0, 100);

-- ----------------------------
-- Table structure for competition_question
-- ----------------------------
DROP TABLE IF EXISTS `competition_question`;
CREATE TABLE `competition_question`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `judgeCase` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '判题用例（json 数组）',
  `judgeConfig` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '判题配置（json 对象）',
  `competitionId` bigint NOT NULL COMMENT '竞赛 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '竞赛题目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of competition_question
-- ----------------------------
INSERT INTO `competition_question` VALUES (1, '题目1', '描述题目1的内容，包括输入输出要求。', '[{\"input\":\"输入示例1\",\"output\":\"输出示例1\"},{\"input\":\"输入示例2\",\"output\":\"输出示例2\"}]', '{\"memoryLimit\":256,\"stackLimit\":1024,\"timeLimit\":1000}', 7, '2025-02-13 22:29:42', 0);
INSERT INTO `competition_question` VALUES (2, '题目2', '描述题目2的内容，包括输入输出要求。', '[{\"input\":\"输入示例1\",\"output\":\"输出示例1\"},{\"input\":\"输入示例2\",\"output\":\"输出示例2\"}]', '{\"memoryLimit\":512,\"stackLimit\":2048,\"timeLimit\":2000}', 7, '2025-02-13 22:29:42', 0);
INSERT INTO `competition_question` VALUES (3, '阿城洒水车', '啊啊啊', '[{\"input\":\"1 1\",\"output\":\"1\"}]', '{\"memoryLimit\":128,\"stackLimit\":256,\"timeLimit\":1}', 8, '2025-02-13 23:25:00', 0);
INSERT INTO `competition_question` VALUES (4, 'lll', '1+1', '[{\"input\":\"1 1\",\"output\":\"2\"}]', '{\"memoryLimit\":1281,\"stackLimit\":2561,\"timeLimit\":1111}', 9, '2025-02-14 16:43:30', 0);
INSERT INTO `competition_question` VALUES (5, 'MGU', '玫瑰', '[{\"input\":\"1\",\"output\":\"1\"}]', '{\"memoryLimit\":128,\"stackLimit\":256,\"timeLimit\":1}', 10, '2025-02-14 21:05:02', 0);
INSERT INTO `competition_question` VALUES (6, '11', '1111', '[{\"input\":\"1 1\",\"output\":\"2\"}]', '{\"memoryLimit\":1281,\"stackLimit\":2561,\"timeLimit\":111}', 11, '2025-02-15 00:14:00', 0);
INSERT INTO `competition_question` VALUES (7, '11', '1111', '[{\"input\":\"1 1\",\"output\":\"2\"}]', '{\"memoryLimit\":1281,\"stackLimit\":2561,\"timeLimit\":111}', 12, '2025-02-15 00:14:33', 0);
INSERT INTO `competition_question` VALUES (8, 'etst', '未开始', '[{\"input\":\"未开始\",\"output\":\"未开始\"}]', '{\"memoryLimit\":128,\"stackLimit\":256,\"timeLimit\":1}', 13, '2025-02-16 15:30:29', 0);
INSERT INTO `competition_question` VALUES (9, '阿萨', 'asca', '[{\"input\":\"未开始\",\"output\":\"未开始\"}]', '{\"memoryLimit\":128,\"stackLimit\":256,\"timeLimit\":1}', 13, '2025-02-16 15:30:29', 0);

-- ----------------------------
-- Table structure for competition_register
-- ----------------------------
DROP TABLE IF EXISTS `competition_register`;
CREATE TABLE `competition_register`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` bigint NULL DEFAULT NULL COMMENT '报名用户id',
  `competitionId` bigint NOT NULL COMMENT '竞赛 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '竞赛报名表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of competition_register
-- ----------------------------
INSERT INTO `competition_register` VALUES (1, 1, 9, '2025-02-14 16:47:49', 0);
INSERT INTO `competition_register` VALUES (2, 1, 11, '2025-02-15 00:31:09', 0);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '题目答案',
  `submitNum` int NOT NULL DEFAULT 0 COMMENT '题目提交数',
  `acceptedNum` int NOT NULL DEFAULT 0 COMMENT '题目通过数',
  `judgeCase` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '判题用例（json 数组）',
  `judgeConfig` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '判题配置（json 对象）',
  `thumbNum` int NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favourNum` int NOT NULL DEFAULT 0 COMMENT '收藏数',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `status` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '提交状态0待审核1通过2不通过',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1891420322908913667 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '题目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 'A+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+BA+B', '题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容题目内容', '[\"栈\",\"简单\"]', '暴力破解', 0, 0, '[{\"input\":\"1 2\",\"output\":\"3 4\"},{\"input\":\"3 5\",\"output\":\"8\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-17 11:12:21', '2025-02-04 10:50:59', 0, 2);
INSERT INTO `question` VALUES (2, '', '新的题目', '[]', '新的', 0, 0, '[{\"input\":\"123\",\"output\":\"456\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-17 17:06:49', '2025-02-03 12:28:57', 1, 1);
INSERT INTO `question` VALUES (1880180420754104321, 'ssda', 'assa', '[\"15\",\"s621a56\"]', 'YYYYY', 0, 0, '[{\"input\":\"ss\",\"output\":\"ss\"}]', '{\"timeLimit\":10000,\"memoryLimit\":100,\"stackLimit\":1000}', 0, 0, 1, '2025-01-17 17:08:39', '2025-02-03 12:29:00', 1, 1);
INSERT INTO `question` VALUES (1880180436512104450, 'ssda', 'assa', '[\"15\",\"s621a56\"]', 'cdc', 0, 0, '[{\"input\":\"ss\",\"output\":\"ss\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 0, 0, 1, '2025-01-17 17:08:42', '2025-02-03 12:29:02', 0, 1);
INSERT INTO `question` VALUES (1880621459012952065, 'aaa', 'asadss\n# aasa\n## as', '[\"nun\",\"cmai\"]', 'yyy', 0, 0, '[{\"input\":\"15\",\"output\":\"100\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-18 22:21:11', '2025-02-03 12:29:03', 0, 1);
INSERT INTO `question` VALUES (1880898280740696065, '嗯嗯', '啊洒水车', '[\"aa\"]', '阿萨', 0, 0, '[{\"input\":\"aa\",\"output\":\"啊啊\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-19 16:41:10', '2025-02-03 12:29:02', 0, 1);
INSERT INTO `question` VALUES (1884179843943301122, 'A+B', '两数相加', '[\"简单\"]', '例如：\n输入：\n1 1\n输出：\n2', 0, 0, '[{\"input\":\"1 1\",\"output\":\"2\"}]', '{\"timeLimit\":20000,\"memoryLimit\":20000,\"stackLimit\":20000}', 0, 0, 1, '2025-01-28 18:00:56', '2025-02-03 12:29:07', 0, 1);
INSERT INTO `question` VALUES (1886265452434378754, 'Test', '# 一个TEst罢了', '[\"111\",\"55\",\"62s6ax2a\",\"5ac1a\"]', '# 无', 0, 0, '[{\"input\":\"1 1\",\"output\":\"1\"}]', '{\"timeLimit\":10000,\"memoryLimit\":10000,\"stackLimit\":10000}', 0, 0, 1, '2025-02-03 12:08:24', '2025-02-03 12:13:48', 0, 1);
INSERT INTO `question` VALUES (1886267826716626945, 'Test2', '## hhhh', '[\"777\"]', '## hhhh', 0, 0, '[{\"input\":\"2 2\",\"output\":\"4\"},{\"input\":\"5 5\",\"output\":\"10\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-02-03 12:17:49', '2025-02-03 12:29:08', 0, 1);
INSERT INTO `question` VALUES (1886268895857242113, 'Tset3', '        51asx5a', '[\"155\"]', '都市传说', 0, 0, '[{\"input\":\"a a\",\"output\":\"aa\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-02-03 12:22:05', '2025-02-03 12:29:09', 0, 1);
INSERT INTO `question` VALUES (1886270552506650626, '4848', '\n        a51x5as', '[\"262\"]', '擦拭', 0, 0, '[{\"input\":\"2\",\"output\":\"2\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-02-03 12:28:39', '2025-02-03 12:28:39', 0, 1);
INSERT INTO `question` VALUES (1886607519144542209, '无名', '一个用户提交的', '[\"44\"]', '~~~~', 0, 0, '[{\"input\":\"1\",\"output\":\"1\"},{\"input\":\"2\",\"output\":\"2\"}]', '{\"timeLimit\":10002,\"memoryLimit\":10001,\"stackLimit\":10001}', 0, 0, 1886478365837283329, '2025-02-04 10:47:38', '2025-02-04 11:09:09', 1, 1);
INSERT INTO `question` VALUES (1886615699224313857, '51515', '1', '[\"25626\"]', '1', 0, 0, '[{\"input\":\"1\",\"output\":\"1\"},{\"input\":\"2\",\"output\":\"2\"},{\"input\":\"3\",\"output\":\"3\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1886478365837283329, '2025-02-04 11:20:08', '2025-02-04 11:20:33', 0, 1);
INSERT INTO `question` VALUES (1887840622819213313, '两数之和', '给定一个整数数组 `nums` 和一个目标值 `target`，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。', '[\"数组\",\"哈希表\",\"Java\"]', '以下是使用Java语言的解决方案，我们使用哈希表来降低时间复杂度至 O(n)。\n\n```java\npublic int[] twoSum(int[] nums, int target) {\n    Map<Integer, Integer> map = new HashMap<>();\n    for (int i = 0; i < nums.length; i++) {\n        int complement = target - nums[i];\n        if (map.containsKey(complement)) {\n            return new int[] { map.get(complement), i };\n        }\n        map.put(nums[i], i);\n    }\n    throw new IllegalArgumentException(\"No two sum solution\");\n}\n```', 0, 0, '[{\"input\":\"2 7 11 15\",\"output\":\"0 1\"},{\"input\":\"3 2 4\",\"output\":\"1 2\"},{\"input\":\"3 3\",\"output\":\"0 1\"}]', '{\"memoryLimit\":65536,\"timeLimit\":1000,\"stackLimit\":65536}', 0, 0, 1, '2025-02-07 20:27:33', '2025-02-07 20:27:33', 0, 1);
INSERT INTO `question` VALUES (1887840872325775362, '两数之和', '给定一个整数数组 `nums` 和一个目标值 `target`，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。', '[\"数组\",\"哈希表\",\"Java\"]', '以下是使用Java语言的解决方案，我们使用哈希表来降低时间复杂度至 O(n)。\n\n```java\npublic int[] twoSum(int[] nums, int target) {\n    Map<Integer, Integer> map = new HashMap<>();\n    for (int i = 0; i < nums.length; i++) {\n        int complement = target - nums[i];\n        if (map.containsKey(complement)) {\n            return new int[] { map.get(complement), i };\n        }\n        map.put(nums[i], i);\n    }\n    throw new IllegalArgumentException(\"No two sum solution\");\n}\n```', 0, 0, '[{\"input\":\"2 7 11 15\",\"output\":\"0 1\"},{\"input\":\"3 2 4\",\"output\":\"1 2\"},{\"input\":\"3 3\",\"output\":\"0 1\"}]', '{\"memoryLimit\":65536,\"timeLimit\":1000,\"stackLimit\":65536}', 0, 0, 1, '2025-02-07 20:28:33', '2025-02-07 20:28:33', 0, 1);
INSERT INTO `question` VALUES (1888066368259018753, '两数之和', '给定一个整数数组 `nums` 和一个目标值 `target`，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。', '[\"数组\",\"哈希表\",\"Java\"]', '以下是使用Java语言的解决方案，我们使用哈希表来降低时间复杂度至 O(n)。\n\n```java\npublic int[] twoSum(int[] nums, int target) {\n    Map<Integer, Integer> map = new HashMap<>();\n    for (int i = 0; i < nums.length; i++) {\n        int complement = target - nums[i];\n        if (map.containsKey(complement)) {\n            return new int[] { map.get(complement), i };\n        }\n        map.put(nums[i], i);\n    }\n    throw new IllegalArgumentException(\"No two sum solution\");\n}\n```', 0, 0, '[{\"input\":\"2 7 11 15\",\"output\":\"0 1\"},{\"input\":\"3 2 4\",\"output\":\"1 2\"},{\"input\":\"3 3\",\"output\":\"0 1\"}]', '{\"memoryLimit\":65536,\"timeLimit\":1000,\"stackLimit\":65536}', 0, 0, 1886478365837283329, '2025-02-08 11:24:35', '2025-02-08 11:24:35', 0, 0);
INSERT INTO `question` VALUES (1891345250236002306, '水仙花数的判定', '编写一个函数，判断一个整数是否为水仙花数。', '[\"数学\",\"简单\",\"编程基础\"]', '以下是判断一个整数是否为水仙花数的Java函数实现：\n```java\npublic class NarcissisticNumber {\n    public static boolean isNarcissistic(int number) {\n        int originalNumber = number;\n        int sum = 0;\n        while (number > 0) {\n            int digit = number % 10;\n            sum += Math.pow(digit, 3);\n            number /= 10;\n        }\n        return sum == originalNumber;\n    }\n}\n```', 0, 0, '[{\"input\":\"153\",\"output\":\"true\"},{\"input\":\"370\",\"output\":\"true\"},{\"input\":\"371\",\"output\":\"true\"},{\"input\":\"407\",\"output\":\"true\"},{\"input\":\"1634\",\"output\":\"false\"}]', '{\"memoryLimit\":10000,\"timeLimit\":1000,\"stackLimit\":10000}', 0, 0, 1, '2025-02-17 04:33:40', '2025-02-17 04:33:40', 0, 1);
INSERT INTO `question` VALUES (1891420322908913666, '二叉树的最大深度', '给定一个二叉树，找出其最大深度。二叉树的深度是从根节点到最远叶子节点的最长路径上的节点数。', '[\"树\",\"深度优先搜索\",\"递归\"]', '以下是使用 Java 语言实现的解决该问题的函数：\n```java\npublic class TreeNode {\n    int val;\n    TreeNode left;\n    TreeNode right;\n    TreeNode(int x) { val = x; }\n}\npublic int maxDepth(TreeNode root) {\n    if (root == null) return 0;\n    int left = maxDepth(root.left);\n    int right = maxDepth(root.right);\n    return Math.max(left, right) + 1;\n}\n```', 0, 0, '[{\"input\":\"1 2 3 4 5 null null 6 null null 7 null null\",\"output\":\"3\"},{\"input\":\"1 null 2\",\"output\":\"2\"},{\"input\":\"\",\"output\":\"0\"}]', '{\"memoryLimit\":65536,\"timeLimit\":1000,\"stackLimit\":65536}', 0, 0, 1, '2025-02-17 09:32:01', '2025-02-17 09:32:01', 0, 1);

-- ----------------------------
-- Table structure for question_competition_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_competition_submit`;
CREATE TABLE `question_competition_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编程语言',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户代码',
  `judgeInfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '判题信息（json 对象）',
  `status` int NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
  `competitionId` bigint NOT NULL COMMENT '竞赛 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_questionId`(`competitionId` ASC) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '竞赛题目提交' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_competition_submit
-- ----------------------------
INSERT INTO `question_competition_submit` VALUES (28, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":156}', 2, 6, 1, '2025-02-17 22:59:15', '2025-02-17 22:59:17', 0);
INSERT INTO `question_competition_submit` VALUES (29, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":304}', 2, 6, 1, '2025-02-17 23:04:37', '2025-02-17 23:04:40', 0);
INSERT INTO `question_competition_submit` VALUES (30, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":201}', 2, 6, 1, '2025-02-17 23:13:35', '2025-02-17 23:13:37', 0);
INSERT INTO `question_competition_submit` VALUES (31, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":209}', 2, 6, 1, '2025-02-17 23:14:45', '2025-02-17 23:14:47', 0);
INSERT INTO `question_competition_submit` VALUES (32, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":1130}', 2, 6, 1, '2025-02-17 23:34:34', '2025-02-17 23:34:38', 0);

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编程语言',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户代码',
  `judgeInfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '判题信息（json 对象）',
  `status` int NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
  `questionId` bigint NOT NULL COMMENT '题目 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_questionId`(`questionId` ASC) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1891429158923288579 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '题目提交' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_submit
-- ----------------------------
INSERT INTO `question_submit` VALUES (1884155215078293506, 'java', 'class Main{\r\n    public static void main()\r\n    {\r\n        System.out.println(2);\r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:23:03', '2025-01-28 16:23:04', 0);
INSERT INTO `question_submit` VALUES (1884156255320211457, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:27:11', '2025-01-28 16:27:12', 0);
INSERT INTO `question_submit` VALUES (1884156584363360257, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:28:30', '2025-01-28 16:28:30', 0);
INSERT INTO `question_submit` VALUES (1884156742039830530, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:29:07', '2025-01-28 16:29:08', 0);
INSERT INTO `question_submit` VALUES (1884156823140892674, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:29:27', '2025-01-28 16:29:27', 0);
INSERT INTO `question_submit` VALUES (1884156942548533249, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:29:55', '2025-01-28 16:29:55', 0);
INSERT INTO `question_submit` VALUES (1884157222115672065, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:31:02', '2025-01-28 16:31:03', 0);
INSERT INTO `question_submit` VALUES (1884160253657939969, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:43:05', '2025-01-28 16:43:06', 0);
INSERT INTO `question_submit` VALUES (1884160483828760577, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:44:00', '2025-01-28 16:44:00', 0);
INSERT INTO `question_submit` VALUES (1884160720962125826, 'java', 'class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:44:56', '2025-01-28 16:44:56', 0);
INSERT INTO `question_submit` VALUES (1884161611685494785, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     System.out.println(2);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:48:28', '2025-01-28 16:48:29', 0);
INSERT INTO `question_submit` VALUES (1884162432510787586, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:51:44', '2025-02-01 12:33:56', 0);
INSERT INTO `question_submit` VALUES (1884162521463586817, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:52:05', '2025-01-28 16:52:05', 0);
INSERT INTO `question_submit` VALUES (1884163449994412034, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:55:47', '2025-01-28 16:55:47', 0);
INSERT INTO `question_submit` VALUES (1884163482152140802, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 16:55:54', '2025-01-28 16:55:54', 0);
INSERT INTO `question_submit` VALUES (1884166411240144898, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 17:07:33', '2025-01-28 17:07:34', 0);
INSERT INTO `question_submit` VALUES (1884167316039598082, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 17:11:08', '2025-01-28 17:11:09', 0);
INSERT INTO `question_submit` VALUES (1884171018913775617, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 17:25:51', '2025-01-28 17:25:52', 0);
INSERT INTO `question_submit` VALUES (1884171185071128577, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{}', 1, 1, 1, '2025-01-28 17:26:31', '2025-01-28 17:26:31', 0);
INSERT INTO `question_submit` VALUES (1884176859733753857, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.println(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":16}', 2, 1, 1, '2025-01-28 17:49:04', '2025-01-28 17:49:08', 0);
INSERT INTO `question_submit` VALUES (1884180040228339714, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":15}', 2, 1884179843943301122, 1, '2025-01-28 18:01:42', '2025-01-28 18:01:44', 0);
INSERT INTO `question_submit` VALUES (1884180299230806018, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":14}', 2, 1884179843943301122, 1, '2025-01-28 18:02:44', '2025-01-28 18:02:47', 0);
INSERT INTO `question_submit` VALUES (1884196510719082498, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":3734}', 2, 1884179843943301122, 1, '2025-01-28 19:07:09', '2025-01-28 19:08:32', 0);
INSERT INTO `question_submit` VALUES (1884198738108416002, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":16}', 2, 1884179843943301122, 1, '2025-01-28 19:16:00', '2025-01-28 19:16:15', 0);
INSERT INTO `question_submit` VALUES (1884201161015230465, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":10}', 2, 1884179843943301122, 1, '2025-01-28 19:25:38', '2025-01-28 19:25:44', 0);
INSERT INTO `question_submit` VALUES (1884204418127167489, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":12}', 2, 1884179843943301122, 1, '2025-01-28 19:38:34', '2025-01-28 19:38:39', 0);
INSERT INTO `question_submit` VALUES (1884208458852532225, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":28}', 2, 1884179843943301122, 1, '2025-01-28 19:54:38', '2025-01-28 19:54:42', 0);
INSERT INTO `question_submit` VALUES (1884214302528831490, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":76}', 2, 1884179843943301122, 1, '2025-01-28 20:17:51', '2025-01-28 20:17:53', 0);
INSERT INTO `question_submit` VALUES (1884216770142732290, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":50}', 2, 1884179843943301122, 1, '2025-01-28 20:27:39', '2025-01-28 20:27:41', 0);
INSERT INTO `question_submit` VALUES (1884222636015288322, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":9}', 2, 1884179843943301122, 1, '2025-01-28 20:50:58', '2025-01-28 20:51:01', 0);
INSERT INTO `question_submit` VALUES (1884233728544276481, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-01-28 21:35:02', '2025-01-28 21:35:03', 0);
INSERT INTO `question_submit` VALUES (1884233890326970370, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":8}', 2, 1884179843943301122, 1, '2025-01-28 21:35:41', '2025-01-28 21:35:44', 0);
INSERT INTO `question_submit` VALUES (1884238957289472001, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":8}', 2, 1884179843943301122, 1, '2025-01-28 21:55:49', '2025-01-28 21:55:57', 0);
INSERT INTO `question_submit` VALUES (1884268288787152897, 'java', 'import cn.hutool.core.util.StrUtil;\r\npublic class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-01-28 23:52:22', '2025-01-28 23:52:23', 0);
INSERT INTO `question_submit` VALUES (1884277147710844929, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":9}', 2, 1884179843943301122, 1, '2025-01-29 00:27:34', '2025-01-29 00:27:40', 0);
INSERT INTO `question_submit` VALUES (1884277612456505346, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":8}', 2, 1884179843943301122, 1, '2025-01-29 00:29:25', '2025-01-29 00:29:28', 0);
INSERT INTO `question_submit` VALUES (1884513826631639042, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":34}', 2, 1884179843943301122, 1, '2025-01-29 16:08:03', '2025-01-29 16:13:17', 0);
INSERT INTO `question_submit` VALUES (1884513826631639043, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":32}', 2, 1884179843943301122, 1, '2025-01-29 16:08:03', '2025-01-29 16:13:17', 0);
INSERT INTO `question_submit` VALUES (1884515265621843970, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":15}', 2, 1884179843943301122, 1, '2025-01-29 16:13:46', '2025-01-29 16:13:53', 0);
INSERT INTO `question_submit` VALUES (1884515703008059394, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":52}', 2, 1884179843943301122, 1, '2025-01-29 16:15:30', '2025-01-29 16:21:00', 0);
INSERT INTO `question_submit` VALUES (1884524230871146497, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-01-29 16:49:24', '2025-01-29 16:49:25', 0);
INSERT INTO `question_submit` VALUES (1884524362668761089, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":15}', 2, 1884179843943301122, 1, '2025-01-29 16:49:55', '2025-01-29 16:49:59', 0);
INSERT INTO `question_submit` VALUES (1884526941326860289, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":7}', 2, 1884179843943301122, 1, '2025-01-29 17:00:10', '2025-01-29 17:00:14', 0);
INSERT INTO `question_submit` VALUES (1884527113612091394, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":10}', 2, 1884179843943301122, 1, '2025-01-29 17:00:51', '2025-01-29 17:00:53', 0);
INSERT INTO `question_submit` VALUES (1884528711901962242, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":23}', 2, 1884179843943301122, 1, '2025-01-29 17:07:12', '2025-01-29 17:11:30', 0);
INSERT INTO `question_submit` VALUES (1884529903008800769, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":6}', 2, 1884179843943301122, 1, '2025-01-29 17:11:56', '2025-01-29 17:12:00', 0);
INSERT INTO `question_submit` VALUES (1884534906746363906, 'java', 'public class Main{\r\n  public static void main(String[] args) {\r\n     int a = Integer.parseInt(args[0]);\r\n     int b = Integer.parseInt(args[1]);\r\n     System.out.print(a+b);   \r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":6}', 2, 1884179843943301122, 1, '2025-01-29 17:31:49', '2025-01-29 17:31:52', 0);
INSERT INTO `question_submit` VALUES (1885525690593394690, 'java', 'import java.io.*;\r\nimport java.util.*;\r\n\r\npublic class Main\r\n{\r\n    public static void main(String args[]) throws Exception\r\n    {\r\n        Scanner cin=new Scanner(System.in);\r\n        int a=cin.nextInt(),b=cin.nextInt();\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":5040}', 2, 1884179843943301122, 1, '2025-02-01 11:08:50', '2025-02-01 11:08:56', 0);
INSERT INTO `question_submit` VALUES (1885525968675749889, 'java', 'import java.io.*;\r\nimport java.util.*;\r\n\r\npublic class Main\r\n{\r\n    public static void main(String args[]) throws Exception\r\n    {\r\n        int a = args[0];\r\n        int b =args[1];\r\n        System.out.println(a+b);\r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:09:56', '2025-02-01 11:09:56', 0);
INSERT INTO `question_submit` VALUES (1885526086292422658, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[]) throws Exception\r\n    {\r\n        int a = args[0];\r\n        int b =args[1];\r\n        System.out.println(a+b);\r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:10:25', '2025-02-01 11:10:25', 0);
INSERT INTO `question_submit` VALUES (1885526169490636801, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n        int a = args[0];\r\n        int b =args[1];\r\n        System.out.println(a+b);\r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:10:44', '2025-02-01 11:10:44', 0);
INSERT INTO `question_submit` VALUES (1885526580729561090, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":101}', 2, 1884179843943301122, 1, '2025-02-01 11:12:22', '2025-02-01 11:12:23', 0);
INSERT INTO `question_submit` VALUES (1885532865327882242, 'java', 'ascacac', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:37:21', '2025-02-01 11:37:21', 0);
INSERT INTO `question_submit` VALUES (1885533587482173442, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:40:13', '2025-02-01 11:40:13', 0);
INSERT INTO `question_submit` VALUES (1885533964633989121, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:41:43', '2025-02-01 11:41:43', 0);
INSERT INTO `question_submit` VALUES (1885534148575191041, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:42:27', '2025-02-01 11:42:27', 0);
INSERT INTO `question_submit` VALUES (1885534927721660417, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 11:45:32', '2025-02-01 11:45:33', 0);
INSERT INTO `question_submit` VALUES (1885543646949867521, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 12:20:11', '2025-02-01 12:20:12', 0);
INSERT INTO `question_submit` VALUES (1885544116191821826, 'java', 'ecsdv dv', '{}', 1, 1884179843943301122, 1, '2025-02-01 12:22:03', '2025-02-01 12:22:03', 0);
INSERT INTO `question_submit` VALUES (1885545821713268737, 'java', 'ecsdv dv', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1884179843943301122, 1, '2025-02-01 12:28:50', '2025-02-01 12:28:51', 0);
INSERT INTO `question_submit` VALUES (1885546108842737666, 'java', 'sadscasc as', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1884179843943301122, 1, '2025-02-01 12:29:58', '2025-02-01 12:29:59', 0);
INSERT INTO `question_submit` VALUES (1885546230221701121, 'java', 'sadscasc as', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1884179843943301122, 1, '2025-02-01 12:30:27', '2025-02-01 12:30:28', 0);
INSERT INTO `question_submit` VALUES (1885546899401932801, 'java', 'import java.io.*;\r\nimport java.util.*;\r\n\r\npublic class Main\r\n{\r\n    public static void main(String args[]) throws Exception\r\n    {\r\n        int a = args[0];\r\n        int b =args[1];\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1884179843943301122, 1, '2025-02-01 12:33:07', '2025-02-01 12:33:07', 0);
INSERT INTO `question_submit` VALUES (1885547220912111618, 'java', '1884179843943301122', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1884179843943301122, 1, '2025-02-01 12:34:23', '2025-02-01 12:34:24', 0);
INSERT INTO `question_submit` VALUES (1885549063037845505, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":117}', 2, 1884179843943301122, 1, '2025-02-01 12:41:43', '2025-02-01 12:41:43', 0);
INSERT INTO `question_submit` VALUES (1885703936819638274, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-02-01 22:57:07', '2025-02-01 22:57:08', 0);
INSERT INTO `question_submit` VALUES (1885705997426667521, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{}', 1, 1884179843943301122, 1, '2025-02-01 23:05:19', '2025-02-01 23:05:19', 0);
INSERT INTO `question_submit` VALUES (1885706721405480961, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":146}', 2, 1884179843943301122, 1, '2025-02-01 23:08:11', '2025-02-01 23:08:13', 0);
INSERT INTO `question_submit` VALUES (1885937612207812610, 'java', '吃撒多吃蔬菜', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1880898280740696065, 1, '2025-02-02 14:25:40', '2025-02-02 14:25:41', 0);
INSERT INTO `question_submit` VALUES (1886064323201200130, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1880180436512104450, 1, '2025-02-02 22:49:10', '2025-02-02 22:49:12', 0);
INSERT INTO `question_submit` VALUES (1886070897923715073, 'java', '\r\npublic class Main\r\n{\r\n    public static void main(String args[])\r\n    {\r\n    int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n        System.out.println(a+b);\r\n    }\r\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1880180436512104450, 1, '2025-02-02 23:15:18', '2025-02-02 23:15:19', 0);
INSERT INTO `question_submit` VALUES (1886083751682895873, 'java', 'public class Main {\n    public static void main(String[] args) {\n        // 请在此输入代码\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":298}', 2, 1880180436512104450, 1, '2025-02-03 00:06:22', '2025-02-03 00:06:24', 0);
INSERT INTO `question_submit` VALUES (1886615359418580993, 'java', '\npublic class Main\n{\n    public static void main(String args[])\n    {\n    int a = Integer.parseInt(args[0]);\n        int b = Integer.parseInt(args[1]);\n        System.out.println(a+b);\n    }\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":223}', 2, 1884179843943301122, 1, '2025-02-04 11:18:47', '2025-02-04 11:18:53', 0);
INSERT INTO `question_submit` VALUES (1886615873728331778, 'java', '\npublic class Main\n{\n    public static void main(String args[])\n    {\n    int a = Integer.parseInt(args[0]);\n      \n        System.out.println(a);\n    }\n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":169}', 2, 1886615699224313857, 1, '2025-02-04 11:20:50', '2025-02-04 11:20:51', 0);
INSERT INTO `question_submit` VALUES (1886615946302373890, 'java', 'public class Main {\n    public static void main(String[] args) {\n        // 请在此输入代码\n        akcoak\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":0}', 2, 1886615699224313857, 1, '2025-02-04 11:21:07', '2025-02-04 11:21:08', 0);
INSERT INTO `question_submit` VALUES (1887788310956417025, 'cpp', '#include <iostream>\n\nint main() {\n    // 提示用户输入两个整数\n    std::cout << \"请输入两个整数，用空格分隔：\" << std::endl;\n\n    int num1, num2;\n    // 读取两个整数\n    std::cin >> num1 >> num2;\n        std::cout << num1+num2 ;\n    return 0;\n}', '{\"message\":\"Wrong Answer\",\"time\":5021}', 2, 1884179843943301122, 1, '2025-02-07 16:59:41', '2025-02-07 16:59:48', 0);
INSERT INTO `question_submit` VALUES (1887788510659813378, 'cpp', '#include <iostream>\n\nint main() {\n    // 提示用户输入两个整数\n    int num1, num2;\n    // 读取两个整数\n    std::cin >> num1 >> num2;\n        std::cout << num1+num2 ;\n    return 0;\n}', '{\"message\":\"Wrong Answer\",\"time\":5030}', 2, 1884179843943301122, 1, '2025-02-07 17:00:29', '2025-02-07 17:00:35', 0);
INSERT INTO `question_submit` VALUES (1887791148218523650, 'cpp', '#include <iostream>\nusing namespace std;\n\nint main(){\n    // 请在此输入代码\n    int nums1,nums2;\n    cin>>nums1>>nums2;\n    cout<<nums1+nums2;\n    return 0;\n}', '{\"message\":\"Wrong Answer\",\"time\":5003}', 2, 1884179843943301122, 1, '2025-02-07 17:10:57', '2025-02-07 17:11:03', 0);
INSERT INTO `question_submit` VALUES (1887792252054491138, 'cpp', '#include <iostream>\nusing namespace std;\n\nint main(){\n    // 请在此输入代码\n    int nums1,nums2;\n    cin>>nums1>>nums2;\n    cout<<nums1+nums2;\n    return 0;\n}', '{\"message\":\"Wrong Answer\",\"time\":5029}', 2, 1884179843943301122, 1, '2025-02-07 17:15:21', '2025-02-07 17:15:27', 0);
INSERT INTO `question_submit` VALUES (1887797411753054210, 'cpp', '#include <iostream>\nusing namespace std;\n\nint main(){\n    // 请在此输入代码\n    int nums1,nums2;\n    cin>>nums1>>nums2;\n    cout<<nums1+nums2;\n    return 0;\n}', '{\"message\":\"Wrong Answer\",\"time\":5031}', 2, 1884179843943301122, 1, '2025-02-07 17:35:51', '2025-02-07 17:35:57', 0);
INSERT INTO `question_submit` VALUES (1887798401696878594, 'cpp', '#include <iostream>\n \nint main(int argc, char* argv[]) {\n    // 打印程序名和所有传入的参数\n    int nums1=argv[0];\n    int nums2=argv[1];\n    std::cout<<nums1+nums2;\n    return 0;\n}', '{}', 1, 1884179843943301122, 1, '2025-02-07 17:39:47', '2025-02-07 17:39:47', 0);
INSERT INTO `question_submit` VALUES (1887799031329017858, 'cpp', '#include <iostream>\n \nint main(int argc, char* argv[]) {\n    // 打印程序名和所有传入的参数\n    int nums1=argv[0];\n    int nums2=argv[1];\n    std::cout<<nums1+nums2;\n    return 0;\n}', '{}', 1, 1884179843943301122, 1, '2025-02-07 17:42:17', '2025-02-07 17:42:17', 0);
INSERT INTO `question_submit` VALUES (1887799205891756033, 'cpp', '#include <iostream>\nusing namespace std;\nint main(int argc, char* argv[]) {\n    // 打印程序名和所有传入的参数\n    int nums1=argv[0];\n    int nums2=argv[1];\n    std::cout<<nums1+nums2;\n    return 0;\n}', '{}', 1, 1884179843943301122, 1, '2025-02-07 17:42:58', '2025-02-07 17:42:58', 0);
INSERT INTO `question_submit` VALUES (1887799575250554881, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 确保至少提供两个参数  \n    if (argc < 3) {  \n        cout << \"使用方法: \" << argv[0] << \" num1 num2\" << endl;  \n        return 1; // 返回错误代码  \n    }  \n\n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2 << endl;  \n\n    return 0;  \n}', '{\"message\":\"Wrong Answer\",\"time\":33}', 2, 1884179843943301122, 1, '2025-02-07 17:44:27', '2025-02-07 17:44:27', 0);
INSERT INTO `question_submit` VALUES (1887800816261222402, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 确保至少提供两个参数  \n    if (argc < 3) {  \n        cout << \"使用方法: \" << argv[0] << \" num1 num2\" << endl;  \n        return 1; // 返回错误代码  \n    }  \n\n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2 << endl;  \n\n    return 0;  \n}', '{\"message\":\"Wrong Answer\",\"time\":38}', 2, 1884179843943301122, 1, '2025-02-07 17:49:22', '2025-02-07 17:49:23', 0);
INSERT INTO `question_submit` VALUES (1887801002140192770, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 确保至少提供两个参数  \n    if (argc < 3) {  \n        cout << \"使用方法: \" << argv[0] << \" num1 num2\" << endl;  \n        return 1; // 返回错误代码  \n    }  \n\n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2;  \n\n    return 0;  \n}', '{\"message\":\"Wrong Answer\",\"time\":47}', 2, 1884179843943301122, 1, '2025-02-07 17:50:07', '2025-02-07 17:50:08', 0);
INSERT INTO `question_submit` VALUES (1887819908745613313, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2;  \n\n    return 0;  \n}', '{\"message\":\"Wrong Answer\",\"time\":5019}', 2, 1884179843943301122, 1, '2025-02-07 19:05:14', '2025-02-07 19:05:20', 0);
INSERT INTO `question_submit` VALUES (1887831443484266497, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2;  \n\n    return 0;  \n}', '{}', 1, 1884179843943301122, 1, '2025-02-07 19:51:05', '2025-02-07 19:51:05', 0);
INSERT INTO `question_submit` VALUES (1887832709983686657, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2;  \n\n    return 0;  \n}', '{}', 1, 1884179843943301122, 1, '2025-02-07 19:56:06', '2025-02-07 19:56:07', 0);
INSERT INTO `question_submit` VALUES (1887838237782822914, 'cpp', '#include <iostream>  \n#include <string> // 包含这个头文件以使用 std::stoi  \nusing namespace std;  \n\nint main(int argc, char* argv[]) {  \n    // 转换命令行参数为整数  \n    int nums1 = stoi(argv[1]);  \n    int nums2 = stoi(argv[2]);  \n\n    // 打印参数的和  \n    cout << nums1 + nums2;  \n\n    return 0;  \n}', '{\"message\":\"Accepted\",\"memory\":0,\"time\":45}', 2, 1884179843943301122, 1, '2025-02-07 20:18:04', '2025-02-07 20:18:07', 0);
INSERT INTO `question_submit` VALUES (1890406671078948866, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":182}', 2, 1884179843943301122, 1, '2025-02-14 22:24:07', '2025-02-14 22:24:11', 0);
INSERT INTO `question_submit` VALUES (1891110156850245633, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{}', 0, 1880180436512104450, 1, '2025-02-16 12:59:30', '2025-02-16 12:59:30', 0);
INSERT INTO `question_submit` VALUES (1891123613876162562, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{}', 0, 1880180436512104450, 1, '2025-02-16 13:52:58', '2025-02-16 13:52:58', 0);
INSERT INTO `question_submit` VALUES (1891124342191894530, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":339}', 2, 1880180436512104450, 1, '2025-02-16 13:55:52', '2025-02-16 13:56:56', 0);
INSERT INTO `question_submit` VALUES (1891428508315435010, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":179}', 2, 1880180436512104450, 1, '2025-02-17 10:04:32', '2025-02-17 10:04:36', 0);
INSERT INTO `question_submit` VALUES (1891428981088993282, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":262}', 2, 1880180436512104450, 1, '2025-02-17 10:06:25', '2025-02-17 10:06:26', 0);
INSERT INTO `question_submit` VALUES (1891429158923288578, 'java', 'public class Main\n{\n    public static void main(String args[])\n    {\n        /**\n        输入示例：\n        int a = Integer.parseInt(args[0]);\n        */\n    }\n}', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":215}', 2, 1880180436512104450, 1, '2025-02-17 10:07:07', '2025-02-17 10:07:09', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1886478365837283330 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123456', '928b751f687815bea14aec5abb6f007b', NULL, NULL, '一个程序员', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/user_avatar/1/hhuW9MJx-20220731183853_afbb8.jpg', '嘿嘿哦哦', 'admin', '2025-01-10 19:02:51', '2025-01-18 22:16:14', 0);
INSERT INTO `user` VALUES (1886478365837283329, '1234567', 'ac75aee72019ddd0bbd2e87ae857c50f', NULL, NULL, '小屁孩嘿嘿', 'https://yyda-1333102418.cos.ap-beijing.myqcloud.com/user_avatar/1886478365837283329/Ccn5F6Nr-微信图片_20241213003008.jpg', '嘿嘿大象', 'user', '2025-01-10 19:02:51', '2025-02-06 21:48:58', 0);

SET FOREIGN_KEY_CHECKS = 1;
