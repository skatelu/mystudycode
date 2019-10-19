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

 Date: 28/09/2018 14:49:38
*/


-- ----------------------------
-- Table structure for h_orderrec
-- ----------------------------
DROP TABLE IF EXISTS "public"."h_orderrec";
CREATE TABLE "public"."h_orderrec" (
  "serverid" int4,
  "ordersno" int4,
  "ordergroup" int4,
  "orderid" char(10) COLLATE "pg_catalog"."default",
  "orderdate" int4,
  "operdate" int4,
  "opertime" int4,
  "custid" int8,
  "custkind" char(1) COLLATE "pg_catalog"."default",
  "custgroup" char(1) COLLATE "pg_catalog"."default",
  "custname" char(16) COLLATE "pg_catalog"."default",
  "orgid" char(4) COLLATE "pg_catalog"."default",
  "brhid" char(4) COLLATE "pg_catalog"."default",
  "fundid" int8,
  "moneytype" char(1) COLLATE "pg_catalog"."default",
  "fundkind" char(1) COLLATE "pg_catalog"."default",
  "fundlevel" char(1) COLLATE "pg_catalog"."default",
  "fundgroup" char(1) COLLATE "pg_catalog"."default",
  "secuid" char(10) COLLATE "pg_catalog"."default",
  "rptsecuid" char(10) COLLATE "pg_catalog"."default",
  "market" char(1) COLLATE "pg_catalog"."default",
  "seat" char(6) COLLATE "pg_catalog"."default",
  "stkcode" char(8) COLLATE "pg_catalog"."default",
  "stkname" char(8) COLLATE "pg_catalog"."default",
  "stktype" char(1) COLLATE "pg_catalog"."default",
  "orderprice" numeric(9,3),
  "bondintr" numeric(12,8),
  "orderqty" int4,
  "reportqty" int4,
  "fundavl" numeric(19,2),
  "matchqty" int4,
  "cancelqty" int4,
  "tradefee" numeric(12,2),
  "orderfrzamt" numeric(19,2),
  "clearamt" numeric(19,2),
  "matchamt" numeric(19,2),
  "ordertype" char(1) COLLATE "pg_catalog"."default",
  "nightflag" char(1) COLLATE "pg_catalog"."default",
  "bsflag" char(2) COLLATE "pg_catalog"."default",
  "inputbs" char(1) COLLATE "pg_catalog"."default",
  "cancelflag" char(1) COLLATE "pg_catalog"."default",
  "combtype" char(1) COLLATE "pg_catalog"."default",
  "reporttime" int4,
  "orderstatus" char(1) COLLATE "pg_catalog"."default",
  "recnum" int4,
  "stkdiff" int4,
  "sourcetype" char(1) COLLATE "pg_catalog"."default",
  "bankcode" char(4) COLLATE "pg_catalog"."default",
  "bankid" varchar(32) COLLATE "pg_catalog"."default",
  "bankorderid" varchar(32) COLLATE "pg_catalog"."default",
  "bankfrzsno" varchar(64) COLLATE "pg_catalog"."default",
  "extdbfsno" int4,
  "bankbranch" char(6) COLLATE "pg_catalog"."default",
  "banknetplace" char(8) COLLATE "pg_catalog"."default",
  "brokerid" int8,
  "custmgrid" int8,
  "funduser0" int8,
  "funduser1" int4,
  "channel" varchar(8) COLLATE "pg_catalog"."default",
  "agentid" int8,
  "operid" int4,
  "operlevel" char(1) COLLATE "pg_catalog"."default",
  "operorg" char(4) COLLATE "pg_catalog"."default",
  "netaddr" char(15) COLLATE "pg_catalog"."default",
  "operway" char(1) COLLATE "pg_catalog"."default",
  "reportkind" varchar(128) COLLATE "pg_catalog"."default",
  "remark" varchar(32) COLLATE "pg_catalog"."default",
  "creditid" char(1) COLLATE "pg_catalog"."default",
  "creditflag" char(1) COLLATE "pg_catalog"."default",
  "remark1" varchar(255) COLLATE "pg_catalog"."default",
  "creditfrzqty" int8,
  "creditfrzamt" numeric(19,2),
  "fundmarginfrz" numeric(19,2),
  "funddebtsfrz" numeric(19,2),
  "orderqty2" int4,
  "orderprice2" numeric(9,3),
  "confernum" int4,
  "termtype" char(1) COLLATE "pg_catalog"."default",
  "gh_term" int4,
  "linkman" char(12) COLLATE "pg_catalog"."default",
  "linkway" varchar(30) COLLATE "pg_catalog"."default",
  "remaintext" varchar(100) COLLATE "pg_catalog"."default",
  "jysorderid" char(10) COLLATE "pg_catalog"."default",
  "secuid_src" char(10) COLLATE "pg_catalog"."default",
  "discratio" numeric(12,8),
  "seat_src" char(6) COLLATE "pg_catalog"."default",
  "rptseat" char(6) COLLATE "pg_catalog"."default",
  "matchcode" varchar(20) COLLATE "pg_catalog"."default",
  "promisno" varchar(8) COLLATE "pg_catalog"."default",
  "limitstkorder" char(1) COLLATE "pg_catalog"."default",
  "dzsaletype" char(1) COLLATE "pg_catalog"."default"
)
;
