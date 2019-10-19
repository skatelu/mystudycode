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

 Date: 28/09/2018 14:49:04
*/


-- ----------------------------
-- Table structure for etfbaseinfo
-- ----------------------------
DROP TABLE IF EXISTS "public"."etfbaseinfo";
CREATE TABLE "public"."etfbaseinfo" (
  "serverid" int4,
  "market" char(1) COLLATE "pg_catalog"."default",
  "ofcode" char(8) COLLATE "pg_catalog"."default",
  "ofname" varchar(16) COLLATE "pg_catalog"."default",
  "etftype" char(1) COLLATE "pg_catalog"."default",
  "etfprop" char(1) COLLATE "pg_catalog"."default",
  "etfcode0" char(8) COLLATE "pg_catalog"."default",
  "etfcode1" char(8) COLLATE "pg_catalog"."default",
  "etfcode2" char(8) COLLATE "pg_catalog"."default",
  "tradeunit" int4,
  "ecashcomponet" numeric(19,2),
  "maxcashratio" numeric(12,8),
  "publish" char(1) COLLATE "pg_catalog"."default",
  "tradestate" char(1) COLLATE "pg_catalog"."default",
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
  "cashcomponent" numeric(19,2),
  "preecashcomponet" numeric(19,2),
  "navpercu" numeric(19,2),
  "nav" numeric(7,4),
  "rtgsflag" char(1) COLLATE "pg_catalog"."default",
  "i_by" int4,
  "c_by" varchar(8) COLLATE "pg_catalog"."default",
  "remark" varchar(32) COLLATE "pg_catalog"."default"
)
;
