-- MySQL dump 10.13  Distrib 8.0.15, for macos10.14 (x86_64)
--
-- Host: localhost    Database: iiacs
-- ------------------------------------------------------
-- Server version	8.0.16-commercial

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_exam_paper`
--

DROP TABLE IF EXISTS `t_exam_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_exam_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '试卷名称',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科',
  `paper_type` int(11) DEFAULT NULL COMMENT '试卷类型(1固定试卷 4.时段试卷 6.任务试卷)',
  `score` int(11) DEFAULT NULL COMMENT '试卷总分(千分制)',
  `question_count` int(11) DEFAULT NULL COMMENT '题目数量',
  `suggest_time` int(11) DEFAULT NULL COMMENT '建议时长(分钟)',
  `limit_start_time` datetime DEFAULT NULL COMMENT '时段试卷 开始时间',
  `limit_end_time` datetime DEFAULT NULL COMMENT '时段试卷 结束时间',
  `frame_text_content_id` int(11) DEFAULT NULL COMMENT '试卷框架 内容为JSON',
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `task_exam_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exam_paper`
--

LOCK TABLES `t_exam_paper` WRITE;
/*!40000 ALTER TABLE `t_exam_paper` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_exam_paper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_exam_paper_answer`
--

DROP TABLE IF EXISTS `t_exam_paper_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_exam_paper_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int(11) DEFAULT NULL,
  `paper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '试卷名称',
  `paper_type` int(11) DEFAULT NULL COMMENT '试卷类型(1固定试卷 4.时段试卷 6.任务试卷)',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科',
  `system_score` int(11) DEFAULT NULL COMMENT '系统判定得分',
  `user_score` int(11) DEFAULT NULL COMMENT '最终得分(千分制)',
  `paper_score` int(11) DEFAULT NULL COMMENT '试卷总分',
  `question_correct` int(11) DEFAULT NULL COMMENT '做对题目数量',
  `question_count` int(11) DEFAULT NULL COMMENT '题目总数量',
  `do_time` int(11) DEFAULT NULL COMMENT '做题时间(秒)',
  `status` int(11) DEFAULT NULL COMMENT '试卷状态(1待判分 2完成)',
  `create_user` int(11) DEFAULT NULL COMMENT '学生',
  `create_time` datetime DEFAULT NULL COMMENT '提交时间',
  `task_exam_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exam_paper_answer`
--

LOCK TABLES `t_exam_paper_answer` WRITE;
/*!40000 ALTER TABLE `t_exam_paper_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_exam_paper_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_exam_paper_question_customer_answer`
--

DROP TABLE IF EXISTS `t_exam_paper_question_customer_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_exam_paper_question_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL COMMENT '题目Id',
  `exam_paper_id` int(11) DEFAULT NULL COMMENT '答案Id',
  `exam_paper_answer_id` int(11) DEFAULT NULL,
  `question_type` int(11) DEFAULT NULL COMMENT '题型',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科',
  `customer_score` int(11) DEFAULT NULL COMMENT '得分',
  `question_score` int(11) DEFAULT NULL COMMENT '题目原始分数',
  `question_text_content_id` int(11) DEFAULT NULL COMMENT '问题内容',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '做题答案',
  `text_content_id` int(11) DEFAULT NULL COMMENT '做题内容',
  `do_right` bit(1) DEFAULT NULL COMMENT '是否正确',
  `create_user` int(11) DEFAULT NULL COMMENT '做题人',
  `create_time` datetime DEFAULT NULL,
  `item_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_exam_paper_question_customer_answer`
--

LOCK TABLES `t_exam_paper_question_customer_answer` WRITE;
/*!40000 ALTER TABLE `t_exam_paper_question_customer_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_exam_paper_question_customer_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL,
  `send_user_id` int(11) DEFAULT NULL COMMENT '发送者用户ID',
  `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送者用户名',
  `send_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送者真实姓名',
  `receive_user_count` int(11) DEFAULT NULL COMMENT '接收人数',
  `read_count` int(11) DEFAULT NULL COMMENT '已读人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message_user`
--

DROP TABLE IF EXISTS `t_message_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_message_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL COMMENT '消息内容ID',
  `receive_user_id` int(11) DEFAULT NULL COMMENT '接收人ID',
  `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接收人用户名',
  `receive_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接收人真实姓名',
  `readed` bit(1) DEFAULT NULL COMMENT '是否已读',
  `create_time` datetime DEFAULT NULL,
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message_user`
--

LOCK TABLES `t_message_user` WRITE;
/*!40000 ALTER TABLE `t_message_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_message_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question`
--

DROP TABLE IF EXISTS `t_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type` int(11) DEFAULT NULL COMMENT '1.单选题  2.多选题  3.判断题 4.填空题 5.简答题',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科',
  `score` int(11) DEFAULT NULL COMMENT '题目总分(千分制)',
  `difficult` int(11) DEFAULT NULL COMMENT '题目难度',
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '正确答案',
  `info_text_content_id` int(11) DEFAULT NULL COMMENT '题目  填空、 题干、解析、答案等信息',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `status` int(11) DEFAULT NULL COMMENT '1.正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question`
--

LOCK TABLES `t_question` WRITE;
/*!40000 ALTER TABLE `t_question` DISABLE KEYS */;
INSERT INTO `t_question` VALUES (1,2,1,10,1,'A',1,2,1,'2023-02-20 11:11:23',_binary '\0'),(2,2,1,10,1,'B',2,2,1,'2023-02-20 11:13:32',_binary '\0'),(3,2,1,10,2,'C',3,2,1,'2023-02-20 11:15:33',_binary '\0'),(4,2,1,10,1,'D',4,2,1,'2023-02-20 11:16:20',_binary '\0'),(5,2,1,10,1,'A',5,2,1,'2023-02-20 11:17:18',_binary '\0'),(6,2,1,10,1,'C',6,2,1,'2023-02-20 11:18:01',_binary '\0'),(7,2,1,10,1,'A,C',7,2,1,'2023-02-20 11:19:03',_binary '\0'),(8,2,1,10,1,'A,B,C,D',8,2,1,'2023-02-20 11:21:13',_binary '\0'),(9,2,1,10,1,'B',9,2,1,'2023-02-20 11:22:44',_binary '\0'),(10,2,1,10,1,'A,B',10,2,1,'2023-02-20 11:24:05',_binary '\0'),(11,2,1,10,1,'A,B,C',11,2,1,'2023-02-20 11:24:50',_binary '\0'),(12,2,1,10,1,'B,C',12,2,1,'2023-02-20 11:25:33',_binary '\0');
/*!40000 ALTER TABLE `t_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_subject`
--

DROP TABLE IF EXISTS `t_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '语文 数学 英语 等',
  `item_order` int(11) DEFAULT NULL COMMENT '排序',
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_subject`
--

LOCK TABLES `t_subject` WRITE;
/*!40000 ALTER TABLE `t_subject` DISABLE KEYS */;
INSERT INTO `t_subject` VALUES (1,'互联网审核员竞赛试题',NULL,_binary '\0');
/*!40000 ALTER TABLE `t_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task_exam`
--

DROP TABLE IF EXISTS `t_task_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_task_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade_level` int(11) DEFAULT NULL COMMENT '级别',
  `frame_text_content_id` int(11) DEFAULT NULL COMMENT '任务框架 内容为JSON',
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task_exam`
--

LOCK TABLES `t_task_exam` WRITE;
/*!40000 ALTER TABLE `t_task_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_task_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task_exam_customer_answer`
--

DROP TABLE IF EXISTS `t_task_exam_customer_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_task_exam_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_exam_id` int(11) DEFAULT NULL,
  `create_user` int(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `text_content_id` int(11) DEFAULT NULL COMMENT '任务完成情况(Json)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task_exam_customer_answer`
--

LOCK TABLES `t_task_exam_customer_answer` WRITE;
/*!40000 ALTER TABLE `t_task_exam_customer_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_task_exam_customer_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_text_content`
--

DROP TABLE IF EXISTS `t_text_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_text_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_text_content`
--

LOCK TABLES `t_text_content` WRITE;
/*!40000 ALTER TABLE `t_text_content` DISABLE KEYS */;
INSERT INTO `t_text_content` VALUES (1,'{\"titleContent\":\"<p>(单选题)第一道单选题，答案是A</p><br/><img src=\\\"null\\\" alt=\\\"1.jpg\\\"/><p><br/></p><br/><br/>\",\"analyze\":\"<p>1</p>\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"<p>答A</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"<p>答b</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"答c\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"<p>答d</p>\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:11:23'),(2,'{\"titleContent\":\"<p>(单选题)第二道单选题，答案是B<br/><img src=\\\"null\\\" alt=\\\"1.jpg\\\"/></p>\",\"analyze\":\"11\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"<p>错</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"错\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"错\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:13:32'),(3,'{\"titleContent\":\"<p>(单选题)第三道单选题，答案C</p>\",\"analyze\":\"11\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"cuo\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"<p>错</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"错\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:15:33'),(4,'{\"titleContent\":\"<p>(单选题)第四道单选题，答案D</p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"xx\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"xxx\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:16:20'),(5,'{\"titleContent\":\"<p>(单选题)第五到单选题，答案A</p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"xx\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"xxx\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:17:18'),(6,'{\"titleContent\":\"(单选题)第六道单选题，答案C\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"xx\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"错\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:18:01'),(7,'{\"titleContent\":\"<p>(多选题)第一道多选题，答案AC</p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"错\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"xx\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:19:03'),(8,'{\"titleContent\":\"(多选题)第二道多选题，答案abcd\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:21:13'),(9,'{\"titleContent\":\"<p>(多选题)第三道多选题，答案B</p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"xxxxx\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"<p>错错错</p>\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:22:44'),(10,'{\"titleContent\":\"<p>(多选题)第四道多选题，答案ab<br/><img src=\\\"null\\\" alt=\\\"1.jpg\\\"/></p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"dui\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"xx\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:24:05'),(11,'{\"titleContent\":\"<p>(多选题)第五道多选题，答案abc</p>\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"xxx\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:24:50'),(12,'{\"titleContent\":\"(多选题)第六道多选题，答案bc\",\"analyze\":\"1\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"x\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"<p>对</p>\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"对\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"xxx\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2023-02-20 11:25:33');
/*!40000 ALTER TABLE `t_text_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_text_content_paper`
--

DROP TABLE IF EXISTS `t_text_content_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_text_content_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL,
  `frame_text_content_id` int(11) DEFAULT NULL,
  `page_index` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_text_content_paper`
--

LOCK TABLES `t_text_content_paper` WRITE;
/*!40000 ALTER TABLE `t_text_content_paper` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_text_content_paper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名',
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '1.男 2女',
  `birth_day` datetime DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL COMMENT '学生年级(1-12)',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` int(11) DEFAULT NULL COMMENT '1.学生 2.老师 3.管理员',
  `status` int(11) DEFAULT NULL COMMENT '1.启用 2禁用',
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'d2d29da2-dcb3-4013-b874-727626236f47','student','D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=','学生',18,1,'2019-09-01 16:00:00',1,'19171171610',1,1,'https://www.mindskip.net:9008/image/ba607a75-83ba-4530-8e23-660b72dc4953/头像.jpg','2019-09-07 18:55:02','2020-02-04 08:26:54',NULL,_binary '\0',NULL),(2,'52045f5f-a13f-4ccc-93dd-f7ee8270ad4c','admin','D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=','管理员',30,1,'2019-09-07 18:56:07',NULL,NULL,3,1,NULL,'2019-09-07 18:56:21',NULL,NULL,_binary '\0',NULL),(3,'7c2500e5-2db2-46b8-9db9-aee709e72ec6','user1','Uc1M0c81QtoVFaOnqgRvRbHhMIUyQRo9br0t31b1ap/i0Zuo+Fu7qDcQOTg53DXrJfc/EphQlxRTpVlJ2HWzszHMh276fM0ngUlmHPGvqGhlLAPJeNn3KNIjtf83Tkte6Cf4lRfAW9kjQTKV+cEO4a9T/KIQSAclheR+TONWPfM=','user1',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:56:49',NULL,'2023-02-20 10:56:49',_binary '\0',NULL),(4,'21b9a1dc-7b35-4228-b175-d336cab13110','user2','E/SaBni4ANdU/qArxdGh/j0ehnH9aLr/VA7Znj30/66IWPJ9vOwnKjanPKHIcQ913LTn8wIVHxQ60hjN+yf3VbYZSX4Rhh20B/jZguC53wLM5cgI3Qcvb9xLqf8EeU3ZUqH/nairXJutKlzmgrtLvOZQf6+qHCSwZA60nzzCdBY=','user2',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:57:00',NULL,'2023-02-20 10:57:00',_binary '\0',NULL),(5,'8bbb94e9-cb91-4829-93cd-aaa2156620be','user3','mSYrJYmIXk9aOCpGFBoAQLgzjGlcx/NfpPpeNaHjC0h5FqskiW5+GFBy33Hi//SAIgAnfnXW9sWtefKw3+jp4MIJ9ABKw90ObAs2TAHMA2uhAYK7NSGhP4pe58rQUhYNSYy5K+arE6Idf7SEXBOV+H9xJzTdVgfijn0SKJDJoOo=','user3',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:57:13',NULL,'2023-02-20 10:57:13',_binary '\0',NULL),(6,'b9429e82-503e-486a-b5f7-8bc542f22ad0','user4','Tp7Ou8S9ciA38gmp20YG7MaV5htmW4IxFmbduGdefFAowpvZmfoJGnuP/Kcc4gRUYgIFt7n0RmAAXKeJy0vQGfekje5+6guCGntclWjJu8VOTVuOfDWjmfZkW+Y5OM63CstF/Y00z5AoS/28bV9yyTyBTlZ6T6giGaQuYgy5N2Y=','user4',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:57:24',NULL,'2023-02-20 10:57:24',_binary '\0',NULL),(7,'e9bbbd36-25a6-4a59-84b0-8e0e820c8243','user5','MfBMJ3/EqgzCxNgMTOxqxnNWBLcoOHQXrLS+Mr/raSbG0r9QLeZaCKCZ0XNf0inJdW9pRfKYkbP5ZRIOyu13UKAFEg4guldhVdlDUGZdFY/+CEjLN9UoyFStG5wH8pXslN1oUSRsrfzEgj8yjhwKA97Vokb8XMyAnvV9tp1nAG8=','user5',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:57:39',NULL,'2023-02-20 10:57:39',_binary '\0',NULL),(8,'ba8f5806-9a3e-4e4c-aa33-a9eed44c16bb','user6','gcwa7jVpncF84AjmkIM4AUTxSoWQZwB9QkA76z158bBaWK/K0zjYeUSnr0qGlkdFG8KM51CQhP3MJKmFF5T9W6kUq2RvxqdPJoeJ67Tye/zuGrGPQg6Z9bzlN7SMLfpr6gr1GQHWwmqvpG+sAocPIO2F70VV2yaZFnObBWbX3/A=','user6',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:57:49',NULL,'2023-02-20 10:57:49',_binary '\0',NULL),(9,'34ce892d-98a1-44c4-bfc3-5edcb7a240fe','user7','bvPRoU3LehUnfiKd/jHQtGdkckZa1TJ86reOyYgmN4ZreUeKqnq1DL0KPiBUSJ2g75WPNUVgSFEwW9r++G+ArYa9/lpMI+qCQBNQN08Fb7huajKeYqg4lHZgl4OiRW/lLcm4WmDEUfkg2r//Tz5ppvumKGd87QAxRL5DopVHs1M=','user7',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:00',NULL,'2023-02-20 10:58:00',_binary '\0',NULL),(10,'f692ce50-4d8f-434b-8cae-259c9974a7d2','user8','TYFBeYRyKWJjF5b6PA5pw3qIfekcWdpPrgiuBth6V3dVmqGACBKVv44I2WzA95UmhyZpJArGFHre9mVkWU9IrbacXk18CRkiNkWlpepIcT33Hva2P2Taw/ue1lybpZgL42dimGrZ6YNEd0ktRLfqUYoMbtq5bM4mhVKBD3fOIO0=','user8',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:18',NULL,'2023-02-20 10:58:18',_binary '\0',NULL),(11,'a29a5bec-3312-4156-9a7d-7c4ab28cf17d','user9','K4AaOwEWJP2jORbsAekI/Ql30nNa6k0zP+XxZN3sbSCIF7V+Krqm1goYNbZGr3D4escQNU0bE5xEXfznFUPzTdxdMR96Uj4m5qaCADge72mwf2eQ2azZDn8lL/RSWAybF2PK/rnAdhFUY0dkqukdBT7Mf7hSqCPnazsGkvilpjc=','user9',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:28',NULL,'2023-02-20 10:58:28',_binary '\0',NULL),(12,'0a57f692-6b58-431c-88c4-add24b7b2019','user10','i2PIlb5fzHM90itBGcRD3HrvyJqAlcLZZaSB3CsZ2vuaiOanr3jIl0cjRmuFJzjPprOyMDnPg/8jvZB1SQPzSWM7jMpHDxzR9zXo4eQHN3WZURvgIQcYGA94tg2Rm867f36BEWTS6beomCTsDiBUxWIA5IlTZunYKBockVMN3sU=','user10',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:38',NULL,'2023-02-20 10:58:38',_binary '\0',NULL),(13,'d1339583-87b9-444a-9317-85d57d2c9279','user11','n5rIoaeOCcUazxP486btavynmvZ+DoA1LLL68e8ROKk1d23/mFVd9EYFGnKFUHKvSDQp4WaqXfje11La+TEr0kIPQMWBVFhE/RgKEhGuhkeLBVzBs2UmVZTNVzK3DmPSJhUkHavX5K8z7YZF8+n5TrCT9i6pwZfIqiZ2bDUTysw=','user11',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:49',NULL,'2023-02-20 10:58:49',_binary '\0',NULL),(14,'a829468e-6cd7-4421-bcf1-69e4dfc88a31','user12','dCCAxgd4j2/L5f/DhOAIZDGd51JnaA+YmwerNKCkbN9Vtpf3dwdI4osrNw/vNpVgdTn/LZAyHzM+0wwJk4HWJHazbeMxwGMtP82Dqli3Zot1lwpZ//JypkbsUa1cnxDuVJO/+JFoEp4j2To5/SOWTyBSdBGWfwv1BJRq1utyFuI=','user12',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:58:59',NULL,'2023-02-20 10:58:59',_binary '\0',NULL),(15,'0e50c90b-fdd6-4a27-9fdb-3f766baa6906','user13','jK9lOh6/ir9LeK1afs4/fNyUb7Xe2Vc5xoANMDge+cz02GU536FJj9NUauj7osxkaP5ZZn5UnlcLX27GcY8qwEnQ2k9ZIkBwLcCeu5cgagAl42TrRPONcYmsd9ee8FBbsb+f7aYMiMVrnhzsDJJc5aqDvgtJQHu4TfyHlvnmAlw=','user13',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:59:08',NULL,'2023-02-20 10:59:08',_binary '\0',NULL),(16,'bf12ea94-0d1a-49ba-b7bb-b343f23aea83','user14','Q9IbtKWgoGTjakyp/EgQiM5zMOSPZ3Icq2KjNyxxf+pEjsYPaKy2dSpJUub0zt1c0vXmZOCQeQTuMwSLqGLUdhjxccfGYKFfU7vffv7/1qIauDMqfBIZsg927oclz2RO5L9GXMx67jxqTv0VH5vc19YmikiUNg+SvOeQ5+Rt+eU=','user14',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:59:17',NULL,'2023-02-20 10:59:17',_binary '\0',NULL),(17,'313d347f-c2e1-4101-831b-2b1c464928a9','user15','I6xlT+0lRjzyqIJpMKonq0ZNd6RUAmd5qqtza0rN3pqACJUvnRb89lgu/NTXvX0rxOSR099pMdK9i2nCLJY+lq52FCf9GuHUrqfoT4W+3xF1ZWVtRarYxUUGozMk4SA4pjng2kIKtLOGiqcSqFUVvv4nDnFf9Jgz7Vq3BxRF0Sg=','user15',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:59:31',NULL,'2023-02-20 10:59:31',_binary '\0',NULL),(18,'eaab455d-2a4c-47f8-bd21-da510b3d6ed4','user16','VGZ/By1KWr+AdDqXh8efVjI9NN+yT43Dx13qeE/aod+AVzjrJirg1G9ah83+gOXKCRpKVk9wnpksxSySHpwDCFR+Z6kNGmbnI/74CDMA2KG+HkYRDvcOZBz5CM5G4khjv/++tNCLr0ExdLR+dER531nkEl3p1RzunnDB/8kH5R8=','user16',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:59:41',NULL,'2023-02-20 10:59:41',_binary '\0',NULL),(19,'0c4f6ced-b763-4c69-beb9-f3fb7b1c5eac','user17','WgHk10TCP1ybRAMXF8A3JbvrBjSHUPCmymZlrc1LG9vYtMDKyxVe71qs0hkJWUO6goC+0Gc46Ni3l1iHURggFzlxLrfCVh2rETBV4Yzs6OK0m1b3cDuiREYOdKXMcLXbfmjEUrVB4GunhG8ESFGCl/gG6JQ324kQz1ozWHFTEk4=','user17',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 10:59:51',NULL,'2023-02-20 10:59:51',_binary '\0',NULL),(20,'afc21826-04a7-40bc-99cc-498069bfa058','user18','nn50wVppHVidNb6nl93KnJ70WsDo8r6qV8OqjhWwuF1Q+e9bM0iUcqi7kH7/C/D8UCCgWAG6NohbrNi/I8MUcVGXEn3Pkq24aOTbDGDBHJkrFUqo7jkzNLiaU4oK/bcxcIsA7Em6tuiclpYnBtHKIq2XIbxc3jxiLMOscioA3IY=','user18',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:00:04',NULL,'2023-02-20 11:00:04',_binary '\0',NULL),(21,'a7c90174-5f05-442c-9710-df8236101938','user19','OXt1rrshQ29/IzbcBFw1Ig2ROlYVSjl+Be9BMOJ4yNmgK8UHsG+ULw7PBzxah0i3a3emI8dM4IQaxbidRZq+0c39TfaoMYvr9K7/vamOG4jU6mYNBJByfCkZluJaB9+HEy6cs7/puJDe1Eu02i6+YqCdH78iey7kpuLu+/jdxuY=','user19',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:00:23',NULL,'2023-02-20 11:00:23',_binary '\0',NULL),(22,'3e4abfb2-e3fc-43bf-8dd5-898d83a1ea55','user20','Us5NhxBVqTjaK29lHhXSmCbMvS68Lp8XLmHt3zv4RxT2VDKDPP8UOLyxja2plNOpbzyu2GjDHcX37fgNffagSexHupXVsPsI2Ba9XT+ImNc4nMsQiIdm03V0fiX/2F1qS1rEaCtR53Wzr2AETngUFb9C0GqRjhgpWlQyVdfhkvk=','user20',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:00:41',NULL,'2023-02-20 11:00:41',_binary '\0',NULL),(23,'0b28bf1e-1a43-4cd9-ad53-88911d530e6c','user21','h5k9yptceXUvZWKpkox7tsQxEkineJ3g2PyPIiAhJ/4xYiSJdbBOPSfdXtGqn910aVCAjNKJNVvm4PYE372aA85PcvEreoEGVG4NwGqJh5a7lQuYoYPP9GvO3dUGHuJdJuMkYY1aT22JgmW2OMZYwn/IixIKj8trKOhFfgRi8x8=','user21',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:10',NULL,'2023-02-20 11:01:10',_binary '\0',NULL),(24,'01910e35-9059-421b-96c5-3439dec23adc','user22','o2g62PHfUVrwpwuX5JZD8dl+h/pliLasubpqmfrO2pb7pYRcP9tg+Br0T8vU98SnMGtnhKTJKxoDzK7jDKEe3GkAgY0tGV8FSfn80Z1R/bovFEvS7NqT9wpuoExpO+BpQzqQPIjzOWRBeCXPf2KyRFGwwmNTwjFOIGTCahFQiVA=','user22',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:18',NULL,'2023-02-20 11:01:18',_binary '\0',NULL),(25,'238237ae-b3a1-4702-af69-a9196697884e','user23','QTApk+y8SFgTZ1aB1+INFuH9CyTGV/Lu7FXW3UZS/IWzgaDGE81HHt559pLMehSgr8V1Ufuw23uD4cJmaW8/V8iS6lvBCx4boUdjMUviSoOuk037XiTdygPcYK4e5iFxFpgZWkeQwaF/oyFtsiMrHb1Mg69tFt8a9AAxZzDkLQ0=','user23',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:26',NULL,'2023-02-20 11:01:26',_binary '\0',NULL),(26,'1b33e974-309e-47f5-a7e7-5153cd04dc08','user24','K+wazkIs2jU0oAKjWa6AlbfViwLdVLiDTiCPC5dMPjhzg4n8jpWmNvpHeyRY/arFwaJKuW+4sUxBFs5IJsq+XT5kAwmdIjooFZ156mxgh/7qZv7Dkls22JB9BNKMx+s7me+K/an7FP7vrWxo0iRfCs9Qz4PI9NLQNFWTARCR3ns=','user24',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:35',NULL,'2023-02-20 11:01:35',_binary '\0',NULL),(27,'3adf32dd-e10c-4c14-a0ac-4aa5f2b8f1e9','user25','A1+sNvTwMEDxineUj9c2YFbm+2tZwmJJYRvqwEftLX73dWZBLq6mwAxnpOTZyqOg3m84yZugKux0sos2oY103G1eNfAAWMM7M7xkZd6NOYSJaBub7ONAXsy9KUOKNkLFq0c+3zezWE2i+aXs66gbCsT2J89WXeP0ffqcc5K0exw=','user25',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:48',NULL,'2023-02-20 11:01:48',_binary '\0',NULL),(28,'4e12a4fe-f190-45da-9ce2-bd423fef9241','user26','Ke1/BqrYi9UksqPxwpzHFC1MmykfCfQ7ebj2G0JPvTGRVVf37XlL4RophsGXhWM/Splq1uss+v2E4QEIbeHrnB2EOBwv3WmNWsVGPU84wUWdKIkuYzZxtvprex09LS/rPjMBcEhP9iub4Ulz5FTlDhQHkULtl/7ZWXqYe8U/rio=','user26',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:01:57',NULL,'2023-02-20 11:01:57',_binary '\0',NULL),(29,'35c8a4a9-2146-4278-b77c-c59b78774cb4','user27','Y38lMrW3aiq3m7TmHobdgDgMqIwa2fQ9EeHR3uXbOnNYBjb2jXNS2Jxj0/U5rtaqxkHp3SAQWvVjgy91HMvwuQM+oqX7iQ+uEouAk6JijwxttR2mTgeVjtjvcHtfFVMM++XscLsbJT9rZxMz1gXaEajWAQ2JGFa0KFIbLun6voU=','user27',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:06',NULL,'2023-02-20 11:02:06',_binary '\0',NULL),(30,'0f18c09b-fd89-4a67-90c2-15c3616873ce','user28','Kaw49ZjdrvzrGfGFTOQzqViyWxYe6adtzVC86KXCF+yeq0KOHSR8FaBd0bzYpI6/Gml0YhWpwTIUaZRuirDhr9DI+yCORW9tQ8qaH2HVYTpg0WAaE0oyGZmuyBshtWxe6wB8tsINQkBuRQC08fTQk8209Ukix58LdVf1h+BwpHA=','user28',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:15',NULL,'2023-02-20 11:02:15',_binary '\0',NULL),(31,'2dc17918-690b-4eb2-b84b-fa2ba1b0cb5c','user29','YUBtmNpLDkAjkHM4tuIoHT71cVoXNfbLZOcOA5Ig/N0NQslWv7sfT/sAlsTwhUrOUOcX6dGF33+U/fSutsK6rskitjyvM/Gkv+aCaGJKkRzFsB0XohTT5o/V2r+SnGRWgPfCZH2YD/uHcWeMi4VvJXgCKPAqt+em+tnGBssITlE=','user29',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:26',NULL,'2023-02-20 11:02:26',_binary '\0',NULL),(32,'cffa5624-cb6b-4af2-985c-fbabd4f1243e','user30','SkEFyjyW9W6EmWcOIwK4to6OV1pnBURmSoF7QqVWKqRfJ1GK5RzAy6lenU3l2L+B0VFxhbq6dxts9lD8czUU9yPsELBYMrZQ/ZonmmWyhpLCu8KNd5j2aB6USLXeR6mAkeGY526KPIU9w8DqzO/QAbJnFEEz397m3WjcV8dpy/Y=','user30',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:39',NULL,'2023-02-20 11:02:39',_binary '\0',NULL),(33,'9edc5076-7f90-4c1a-861c-2de23412d54e','user31','T2ztLOAQhUVQDnUqAid0WJTmrj3EnSCIsvvDy6mdVr/nVtBbaBSLsMtk4+FKe/5n9Mv3/IWKwBJzgo4u0wtzj9TlsEZdl1Bh8CIGEV19uOVoePZWN/DsYib4omzhX20HyuJ4ogIER+oUfzcARwCEe8qHgWliAC6fUkEPd5+nxoc=','user31',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:48',NULL,'2023-02-20 11:02:48',_binary '\0',NULL),(34,'d0d0a71a-c8ff-4a52-8aeb-a41ec166de80','user32','SBemIrAt1Ww7097ghnPQRoXl09JXVJhEMcZaSJmHGQgwtqeOO9N8fxhnOn6jbAOKKgyk2e85tnhyVcCuo4NbmIBdIQZJjK4bb/nTkaI/I3t2p7hK84OgQKgt7mxYv7I2iwmQqjQqmnr2R4PBs/70t2N8PaOkfqXROfxMfo1mlJ0=','user32',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:02:56',NULL,'2023-02-20 11:02:56',_binary '\0',NULL),(35,'6ddf4fbe-8725-41a4-a02b-ff8194109bb3','user33','A39DaUIklfIL9CevtTAAXWtE8SaRoDWv4/ao/jdUSWPXkSLoi2+7GH699R6bzReX4EQ6T0KSuka+JIG8sFu1qGkeAL2YVm+VaLegJNhVaL/7Tl4q92mvYUtmaMSbWoOpVMqoK1riRUzIUCL1YDXT+sbKefoH/AVO5pTwjuMWctw=','user33',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:05',NULL,'2023-02-20 11:03:05',_binary '\0',NULL),(36,'b6de282a-893d-4597-9cc0-312393be8d48','user34','lnQMo00tnOCWPayu6oYyWl6m6krVc7s6dbbLxbRfY7BiB9dwZdE2Y6gDvTo5H6Zb8STi7KEuAQgkXgGoOtOgLnbOONjH3hR1oWh8Nt9GNPjJ/z+rdNHUSjX+jX2VkVHPWtzmkZzE7QGRnKxZiuCck/VXEk1ErtEp+oFF8MdNvBE=','user34',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:13',NULL,'2023-02-20 11:03:13',_binary '\0',NULL),(37,'5fcf7b28-ad21-4804-972d-660ca6f4ae7a','user35','lN/N480HSJhO10bWxJinfURuYU9voSHCKaggVacr3x9/WGCF5muVlzhD2ueptw89G6WjWQ4X3ICTcmA0ryHXACnPmf7RLIK3eOiyDCIf8XcBrslvG15OHoNcyk824wI1ok16hKu/xptwtnKNuF/8sgcZUNQovdyj7cqKzHMs40o=','user35',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:22',NULL,'2023-02-20 11:03:22',_binary '\0',NULL),(38,'9da071a9-7228-4dfa-b9de-c5eccdd8d539','user36','LLy56yIbr5kYM0tl2VmkGUj/8YEK+COz80UKwUTSRB1y7teUoNbAPey/GbMaxBf5KrFtc3GX7SezR/bCGitqMK97Ki+3oazaWkI8zr8wzsXlK2vZ4H7+Ph+1iDIGl9WO4EdsIZSmfnxNJdcD66gBgmsx10K0OuYK6zJny3/FPuw=','user36',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:33',NULL,'2023-02-20 11:03:33',_binary '\0',NULL),(39,'e6a6bfc0-191a-4d76-a917-686222d53fac','user37','DqNR5mzYr4V84LJ2REiKwj4kexHxKJY9LoCTrgaNLY33qO5ta7nY35SdvoEZbnPYcFjhuXHsBfEPiSKWHKkH1l3+TiyVn7CkKOhG8t+50gmro9XwE51arNCSpOhGVAyL0+7eBR0UOAizJsK5qyQR6XNLtBD5tCEGLuhRItKbvBA=','user37',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:42',NULL,'2023-02-20 11:03:42',_binary '\0',NULL),(40,'879780fb-b82c-4fde-bbb8-b1e3caadfd8c','user38','QwISKSI7nQWx73xe3OcH4lw+T+f0a0e0D7dQIITRVDu0yQCQp18wM8Kg0F0d0u/Ea+l0RRf6Qt+0mZ9jCCC2uPD0REkWaHSrlT39BiSFMUjpLHBEmh/kMPmkqFC1kk8E3VcOUIixAQcP0anSiBmMwk4TnRzz7mvVm7tBAoCiwoA=','user38',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:03:51',NULL,'2023-02-20 11:03:51',_binary '\0',NULL),(41,'02a4d3b9-f6f5-47cf-88b0-ffe51db746a9','user39','gUWLj+gmT8ALeI+kuqYcGX6jDHFv0G86z0wsD5PrHHgZeCM4fqx9YrtZUGIeP+HeSRTStxcQW5BTECfSCG9Fq3JSA92sBQBFkyM+rhIFqYL16ZeXPaEXDqmq200uQBZT0dQdFEW1OcEskbIu2NizTIu3/8oFa8D2Kqk2rsaUbQQ=','user39',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:00',NULL,'2023-02-20 11:04:00',_binary '\0',NULL),(42,'79b1efeb-8749-4556-9a50-9ea6728a9729','user40','iFr1/BtEFYlZ1NmjQeYXGVDN/qrgn16h+LG/ZM+vpRUFMl4YCdHmurKiH+lexEEkeJRXaR68CkstSgiqZi1FwjGVBBDWz4/N0kAYSHo0aYnsQoPgvjj92Ivoj/DvFydv4MmQIEpOhQYU36WmKjqTJZzFZ1g5nk8mhJjqS1l3nD0=','user40',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:14',NULL,'2023-02-20 11:04:14',_binary '\0',NULL),(43,'919fdf82-11a2-48d5-b495-0a6e37e612c9','user41','kMfkiCkIWWqQqiR65j1be+H34o/0q/z2aDE62vUl5iG6C73jIx0lvSavd6QIxDVHnk9UhdBNG2gNiAGH9HMXBYLJn8pWFLGfoe9Cr2mOOq2RuoieNISfEraHRdiB41mM6+ZJ5i/k+yg3U8rCgq+6tsc+sSVnU0tZlQDRRcOaC3s=','user41',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:23',NULL,'2023-02-20 11:04:23',_binary '\0',NULL),(44,'b281b0a8-05b2-453c-8a88-fcfa44d1330d','user42','esXlRzCEcNKN8FWi85t9Ox3hc9C3VqjTw5LshBZVqm3kxaxpWqxW+8c2CyjnEQKJ62Gtcx/GThkj/C9FpZ9U4WizB/hYT427SKAeKVgXR48vSj2sC6yxWru14uyhm+QGJiKsk60soWXyzXd6iu9k4oOfxSMKxdXMPljaHK3tsE8=','user42',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:31',NULL,'2023-02-20 11:04:31',_binary '\0',NULL),(45,'c34f9a97-ad52-4bb3-8bf8-11d1b005e856','user43','Ik2lseuXUR3kgFMSZ6EU3pxt6bRoCf9zVynNe/e6+mpm2G9L1mJPYo3k4f+IXGIWTmOucb7J7HIK8qFpgorR9jZSnnStmkg5bqmPZFiFHDBs+V2DoWrsueKxGd/CyLUQAlofR3pnyyizkRuyoUiQBT4FeOmNIyVI97pDFmLp1K4=','user43',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:40',NULL,'2023-02-20 11:04:40',_binary '\0',NULL),(46,'64539497-4b84-483a-92a0-cbc2a376a9ec','user44','OaL7iR4MGI+nfFudtv90MUqL9SWqIKJl7EaiN2IbXT255HLzlfRzKb6TKIto3E4yvvRxIMuy1kg2hbwHTcsiysAw7B6rdM/Ol0EmRwbtkb8ls4AiSTK77OWnpOBcLJaYe8pjam9A632oz/tIDTH8HZN/ZzVUqx2Oii2SUe5iEUA=','user44',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:48',NULL,'2023-02-20 11:04:48',_binary '\0',NULL),(47,'9c25f69f-187d-48e4-89da-6b0bb05d7a7e','user45','orV46XZ0RIdpyzjAfZJaETq7lWOnwh5HIUWwgaTwNfGunGb1AOOOCYNXYZilxvUjAWcBbCA3iOZZw1PlEvWX/mynxnzDzlhoyNejtIxqdv6FIUFwU5E7m5Qm4dfVROxgdNlGiRj/sczbD6Qbkpg1dWIudklHg0GJ1hIYVECWyqw=','user45',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:04:57',NULL,'2023-02-20 11:04:57',_binary '\0',NULL),(48,'5d1fccd8-e700-42ae-9463-4641dc088146','user46','VD7q5fCJo4DREYodXgJu1mfPDp9m+RX41w/KtUP/1d/KX7QmDweS6HbVZazuwUguUj6WkWF58Rtocz9illXuxYs8hcwrQdizZ7ZrLnDFtDz99kogUmxC4T6S27TfRFebta8mBxgx57xEeW6GmxezZjQ9ZAr9zRrm46OciNKsF7w=','user46',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:05:06',NULL,'2023-02-20 11:05:06',_binary '\0',NULL),(49,'dece5c00-6585-4c0c-a2be-8cea7d344c15','user47','gIzgpB3gHDZTd3IbdN0LYy5nSHad8gRVMdMty2WU1QzowwiIJGxRRO/CUWy9hDfKp5340LHNwhlV9nAN6xwnPm4eOXzTGeQ1vr0Hklm8QTwoNpM+rkZh4ocJDVxayyaYiYvYuQZ6qrIxQT0MOXWjS5s2LiBgRddWU1ZNJgEIJU4=','user47',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:05:16',NULL,'2023-02-20 11:05:16',_binary '\0',NULL),(50,'e4648490-ed98-4da1-af94-edb648dfb632','user48','h6smHXpxmOwCpPdOz3l4g3CusSIteMyZYDnOBIwF/NRZ4tdDjbe9r8OWoXYko/koqbWmM/lWfH0u+7qy/N5Usm3HIoMsmSKbhsefH7UvVI6FRxXUcSHq/yEPhA/26vavuiYG7Sv/msYnNO1NgtyLkLfKusnIx2PxP3KZkAG5B/E=','user48',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:05:27',NULL,'2023-02-20 11:05:27',_binary '\0',NULL),(51,'9bd3e810-07b6-4081-918d-f0482a254906','user49','hrF3RPyjxBU38pYpUjsYBDKPvo8nn7Gb95+LFv7nGSKeBtGdjVxtbYCImzIFiamf7mg8t8mlVZAf4tN5LVrJ+Q/KKQ6LMospnIMXFI0Ip9yqlJAd9j8lgdfrm0s41uNWqwLECyA3eSU/wbASp8Yq9sAkRJu2r05wRoKebneQ6TY=','user49',NULL,NULL,NULL,NULL,NULL,1,1,NULL,'2023-02-20 11:05:36',NULL,'2023-02-20 11:05:36',_binary '\0',NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_event_log`
--

DROP TABLE IF EXISTS `t_user_event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_user_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `score` int(11) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `finish` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_event_log`
--

LOCK TABLES `t_user_event_log` WRITE;
/*!40000 ALTER TABLE `t_user_event_log` DISABLE KEYS */;
INSERT INTO `t_user_event_log` VALUES (1,2,'admin','管理员','admin 登录了colligate exam system系统','2023-02-20 10:56:22',NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_user_event_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_token`
--

DROP TABLE IF EXISTS `t_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_user_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openId',
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_token`
--

LOCK TABLES `t_user_token` WRITE;
/*!40000 ALTER TABLE `t_user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'iiacs'
--

--
-- Dumping routines for database 'iiacs'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-20 11:26:13
