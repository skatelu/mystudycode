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

 Date: 28/09/2018 14:49:26
*/


-- ----------------------------
-- Table structure for eccodesign
-- ----------------------------
DROP TABLE IF EXISTS "public"."eccodesign";
CREATE TABLE "public"."eccodesign" (
  "serverid" int4,
  "custid" int8,
  "fundid" int8,
  "fundcode" char(8) COLLATE "pg_catalog"."default",
  "taacc" char(12) COLLATE "pg_catalog"."default",
  "transacc" char(17) COLLATE "pg_catalog"."default",
  "ectype" char(1) COLLATE "pg_catalog"."default",
  "sno" int4,
  "dbtid" varchar(8) COLLATE "pg_catalog"."default",
  "fullname" varchar(120) COLLATE "pg_catalog"."default",
  "acctype" char(1) COLLATE "pg_catalog"."default",
  "idtype" char(1) COLLATE "pg_catalog"."default",
  "idno" char(32) COLLATE "pg_catalog"."default",
  "orderdate" int4,
  "opertime" int4,
  "operway" char(1) COLLATE "pg_catalog"."default",
  "netaddr" char(15) COLLATE "pg_catalog"."default",
  "operorg" char(4) COLLATE "pg_catalog"."default",
  "remark" varchar(32) COLLATE "pg_catalog"."default",
  "ecsno" varchar(32) COLLATE "pg_catalog"."default",
  "operid" int4,
  "orgid" char(4) COLLATE "pg_catalog"."default",
  "dealflag" char(1) COLLATE "pg_catalog"."default",
  "status" char(1) COLLATE "pg_catalog"."default",
  "reorderdate" int4,
  "reopertime" int4,
  "expand1" varchar(64) COLLATE "pg_catalog"."default",
  "expand2" varchar(64) COLLATE "pg_catalog"."default",
  "expand3" varchar(64) COLLATE "pg_catalog"."default",
  "expand4" varchar(64) COLLATE "pg_catalog"."default",
  "riskmatch" char(1) COLLATE "pg_catalog"."default",
  "risklevel" char(1) COLLATE "pg_catalog"."default",
  "telno" varchar(32) COLLATE "pg_catalog"."default",
  "mobil" varchar(32) COLLATE "pg_catalog"."default",
  "email" varchar(32) COLLATE "pg_catalog"."default",
  "postid" varchar(16) COLLATE "pg_catalog"."default",
  "addr" varchar(120) COLLATE "pg_catalog"."default",
  "notassignflag" char(1) COLLATE "pg_catalog"."default",
  "redeemcontract" char(1) COLLATE "pg_catalog"."default",
  "reservfund" numeric(19,2),
  "dyreservfund" numeric(19,2),
  "resfundexpdate" int4,
  "multisettstatus" char(1) COLLATE "pg_catalog"."default",
  "multisettexp" int4,
  "multisettupdate" int4,
  "lastconvertvol" numeric(19,2),
  "lastdivate" int4,
  "isnewsign" char(1) COLLATE "pg_catalog"."default",
  "beforconvertvol" numeric(19,2),
  "updatedate" int4,
  "specialctrl" varchar(32) COLLATE "pg_catalog"."default",
  "prereservfund" numeric(19,2),
  "prereservfunddate" int4
)
;
