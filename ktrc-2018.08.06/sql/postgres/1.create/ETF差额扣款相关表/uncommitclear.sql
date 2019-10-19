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

 Date: 28/09/2018 14:50:13
*/


-- ----------------------------
-- Table structure for uncommitclear
-- ----------------------------
DROP TABLE IF EXISTS "public"."uncommitclear";
CREATE TABLE "public"."uncommitclear" (
  "serverid" int4,
  "sno" int4,
  "market" char(1) COLLATE "pg_catalog"."default",
  "orderdate" int4,
  "matchdate" int4,
  "rptbsflag" char(4) COLLATE "pg_catalog"."default",
  "orderid" char(10) COLLATE "pg_catalog"."default",
  "matchcode" varchar(20) COLLATE "pg_catalog"."default",
  "moneytype" char(1) COLLATE "pg_catalog"."default",
  "bpsecuid" varchar(32) COLLATE "pg_catalog"."default",
  "stkcode" varchar(12) COLLATE "pg_catalog"."default",
  "linkstk" varchar(12) COLLATE "pg_catalog"."default",
  "stkname" char(16) COLLATE "pg_catalog"."default",
  "stktype" char(1) COLLATE "pg_catalog"."default",
  "trdid" char(1) COLLATE "pg_catalog"."default",
  "settlefundays" int4,
  "settlestkdays" int4,
  "funduseflag" char(1) COLLATE "pg_catalog"."default",
  "stkusedflag" char(1) COLLATE "pg_catalog"."default",
  "biztype" char(1) COLLATE "pg_catalog"."default",
  "busitype" char(4) COLLATE "pg_catalog"."default",
  "matchtime" int4,
  "matchqty" int8,
  "matchprice" numeric(9,3),
  "bondintr" numeric(12,8),
  "matchamt" numeric(19,2),
  "mateno" varchar(32) COLLATE "pg_catalog"."default",
  "seat" char(6) COLLATE "pg_catalog"."default",
  "pathid" varchar(16) COLLATE "pg_catalog"."default",
  "dbfrec" int4,
  "secuid" varchar(32) COLLATE "pg_catalog"."default",
  "custname" char(16) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "fundkind" char(1) COLLATE "pg_catalog"."default",
  "fundlevel" char(1) COLLATE "pg_catalog"."default",
  "fundgroup" char(1) COLLATE "pg_catalog"."default",
  "orgid" char(4) COLLATE "pg_catalog"."default",
  "brhid" char(4) COLLATE "pg_catalog"."default",
  "custid" int8,
  "agentid" int8,
  "custkind" char(1) COLLATE "pg_catalog"."default",
  "custgroup" char(1) COLLATE "pg_catalog"."default",
  "orderqty" int8,
  "orderprice" numeric(9,3),
  "ordertime" int4,
  "bsflag" varchar(2) COLLATE "pg_catalog"."default",
  "operway" char(1) COLLATE "pg_catalog"."default",
  "operid" int4,
  "operlevel" char(1) COLLATE "pg_catalog"."default",
  "operorg" char(4) COLLATE "pg_catalog"."default",
  "netaddr" char(15) COLLATE "pg_catalog"."default",
  "sourcetype" char(1) COLLATE "pg_catalog"."default",
  "bankcode" char(4) COLLATE "pg_catalog"."default",
  "bankorderid" char(10) COLLATE "pg_catalog"."default",
  "bankid" varchar(32) COLLATE "pg_catalog"."default",
  "status" char(1) COLLATE "pg_catalog"."default",
  "remark" varchar(32) COLLATE "pg_catalog"."default",
  "clearedamt" numeric(19,2),
  "fee_jsxf" numeric(12,2),
  "fee_sxf" numeric(12,2),
  "fee_yhs" numeric(12,2),
  "fee_ghf" numeric(12,2),
  "fee_qsf" numeric(12,2),
  "fee_jygf" numeric(12,2),
  "fee_jsf" numeric(12,2),
  "fee_zgf" numeric(12,2),
  "fee_qtf" numeric(12,2),
  "feefront" numeric(12,2),
  "privilege" numeric(19,2),
  "bb_matchdate" int4,
  "bb_matchclearamt" numeric(19,2),
  "matchtimes" int4,
  "ordersno" int4,
  "bankbranch" char(6) COLLATE "pg_catalog"."default",
  "banknetplace" char(8) COLLATE "pg_catalog"."default",
  "brokerid" int8,
  "custmgrid" int8,
  "funduser0" int8,
  "funduser1" int4,
  "reportkind" varchar(128) COLLATE "pg_catalog"."default",
  "creditid" char(1) COLLATE "pg_catalog"."default",
  "creditflag" char(1) COLLATE "pg_catalog"."default",
  "fee_one_yhs" numeric(12,2),
  "fee_one_ghf" numeric(12,2),
  "fee_one_qsf" numeric(12,2),
  "fee_one_jygf" numeric(12,2),
  "fee_one_jsf" numeric(12,2),
  "fee_one_zgf" numeric(12,2),
  "fee_one_qtf" numeric(12,2),
  "fee_one_fxj" numeric(12,2),
  "prodcode" varchar(12) COLLATE "pg_catalog"."default",
  "discratio" numeric(12,8),
  "bizprop" int4
)
;
