**server-ces**

<!--crewate time: 2022.07.18-->


# mysql 变更记录
### `20221217`
ALTER TABLE t_subject
DROP COLUMN level_name;
ALTER TABLE t_subject
DROP COLUMN level;
t_subject 表中删除 level 和 level_name两列

# 20230102  mysql 变更

新增表，用户关联题目与试卷数量，一般一行10条数据，pageIndex类增
-- ----------------------------
-- Table structure for t_text_content_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_text_content_paper`;
CREATE TABLE `t_text_content_paper`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `frame_text_content_id` int(11) NULL DEFAULT NULL,
  `page_index` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;


-- 删除年级

ALTER TABLE t_question
DROP COLUMN grade_level;

ALTER TABLE t_exam_paper
DROP COLUMN grade_level;

-- t_user_event_log 添加字段

ALTER TABLE t_user_event_log
add COLUMN score int(11) NULL DEFAULT NULL ;

ALTER TABLE t_user_event_log
add COLUMN times int(11) NULL DEFAULT NULL ;

ALTER TABLE t_user_event_log
add COLUMN finish int(11) NULL DEFAULT NULL ;


-- 添加用户状态，是否淘汰