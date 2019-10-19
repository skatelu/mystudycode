/*
 Navicat Premium Data Transfer

 Source Server         : 交易实时风控平台-postgres50
 Source Server Type    : PostgreSQL
 Source Server Version : 90609
 Source Host           : 10.29.182.50:5432
 Source Catalog        : ktrc_dev
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90609
 File Encoding         : 65001

 Date: 28/09/2018 14:48:30
*/


-- ----------------------------
-- Table structure for etf_cashcomponent
-- ----------------------------
DROP TABLE IF EXISTS "public"."etf_cashcomponent";
CREATE TABLE "public"."etf_cashcomponent" (
  "date" varchar(32) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "custid" int8,
  "custname" varchar(30) COLLATE "pg_catalog"."default",
  "orgname" varchar(50) COLLATE "pg_catalog"."default",
  "ykkje" numeric(18,2),
  "djje" numeric(18,2),
  "ydbzje" numeric(18,2),
  "kyye" numeric(18,2),
  "qsje" numeric(18,2),
  "yled" numeric(18,2),
  "jzrq" int4
)
;
COMMENT ON TABLE "public"."etf_cashcomponent" IS 'ETF_差额扣款当天信息表';

-- ----------------------------
-- Table structure for etf_cashcomponent_his
-- ----------------------------
DROP TABLE IF EXISTS "public"."etf_cashcomponent_his";
CREATE TABLE "public"."etf_cashcomponent_his" (
  "date" varchar(32) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "custid" int8,
  "custname" varchar(30) COLLATE "pg_catalog"."default",
  "orgname" varchar(50) COLLATE "pg_catalog"."default",
  "ykkje" numeric(18,2),
  "djje" numeric(18,2),
  "ydbzje" numeric(18,2),
  "kyye" numeric(18,2),
  "qsje" numeric(18,2),
  "yled" numeric(18,2),
  "jzrq" int4
)
;
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."date" IS '日期';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."fundid" IS '客户基金账户';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."custid" IS '客户代码';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."custname" IS '客户名称';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."orgname" IS '所属营业厅';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."ykkje" IS '应扣款金额';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."djje" IS '冻结金额';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."ydbzje" IS '预冻不足金额';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."kyye" IS '可用余额';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."qsje" IS '缺少金额';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."yled" IS '预留额度';
COMMENT ON COLUMN "public"."etf_cashcomponent_his"."jzrq" IS '截止日期';
COMMENT ON TABLE "public"."etf_cashcomponent_his" IS 'ETF_差额扣款历史信息表';

-- ----------------------------
-- Table structure for etf_cashcomponent_ydzjqk
-- ----------------------------
DROP TABLE IF EXISTS "public"."etf_cashcomponent_ydzjqk";
CREATE TABLE "public"."etf_cashcomponent_ydzjqk" (
  "date" varchar(32) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "custid" int8,
  "custname" varchar(30) COLLATE "pg_catalog"."default",
  "orgname" varchar(50) COLLATE "pg_catalog"."default",
  "stkcode" varchar(30) COLLATE "pg_catalog"."default",
  "ykkje" numeric(18,2),
  "djje" numeric(18,2),
  "ydbzje" numeric(18,2),
  "flbz" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."etf_cashcomponent_ydzjqk" IS 'ETF_T-1日预冻资金情况表';

-- ----------------------------
-- Table structure for etf_sg_sh_summary
-- ----------------------------
DROP TABLE IF EXISTS "public"."etf_sg_sh_summary";
CREATE TABLE "public"."etf_sg_sh_summary" (
  "serverid" int4,
  "orderdate" int4,
  "stkcode" varchar(12) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "custid" int8,
  "custname" varchar(30) COLLATE "pg_catalog"."default",
  "orgid" varchar(30) COLLATE "pg_catalog"."default",
  "matchqty" int8,
  "clearedamt" numeric(38,2),
  "flag" varchar(20) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for preetfbaseinfo
-- ----------------------------
DROP TABLE IF EXISTS "public"."preetfbaseinfo";
CREATE TABLE "public"."preetfbaseinfo" (
  "serverid" int4,
  "market" varchar(1) COLLATE "pg_catalog"."default",
  "ofcode" varchar(8) COLLATE "pg_catalog"."default",
  "ofname" varchar(16) COLLATE "pg_catalog"."default",
  "etftype" varchar(1) COLLATE "pg_catalog"."default",
  "etfprop" varchar(1) COLLATE "pg_catalog"."default",
  "etfcode0" varchar(8) COLLATE "pg_catalog"."default",
  "etfcode1" varchar(8) COLLATE "pg_catalog"."default",
  "etfcode2" varchar(8) COLLATE "pg_catalog"."default",
  "tradeunit" int4,
  "ecashcomponet" numeric(23,2),
  "maxcashratio" numeric(28,8),
  "publish" varchar(1) COLLATE "pg_catalog"."default",
  "tradestate" varchar(1) COLLATE "pg_catalog"."default",
  "recordnum" int4,
  "totalrecordnum" int4,
  "creationlimit" int8,
  "redemptionlimit" int8,
  "creationlimituser" int8,
  "redemptionlimituser" int8,
  "netcreationlimit" int8,
  "netredemptionlimit" int8,
  "netcreationlimitperuser" int8,
  "netredemptionlimitperuser" int8,
  "tradingday" int4,
  "pretradingday" int4,
  "cashcomponent" numeric(23,2),
  "preecashcomponet" numeric(23,2),
  "navpercu" numeric(23,2),
  "nav" numeric(15,4),
  "rtgsflag" varchar(1) COLLATE "pg_catalog"."default",
  "i_by" int4,
  "c_by" varchar(8) COLLATE "pg_catalog"."default",
  "remark" varchar(32) COLLATE "pg_catalog"."default"
)
;
