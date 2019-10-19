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

 Date: 28/09/2018 14:49:13
*/


-- ----------------------------
-- Table structure for fundasset
-- ----------------------------
DROP TABLE IF EXISTS "public"."fundasset";
CREATE TABLE "public"."fundasset" (
  "serverid" int4,
  "orgid" char(4) COLLATE "pg_catalog"."default",
  "fundseq" int4,
  "fundid" int8,
  "moneytype" char(1) COLLATE "pg_catalog"."default",
  "custid" int8,
  "fundlastbal" numeric(19,2),
  "fundbal" numeric(19,2),
  "fundavl" numeric(19,2),
  "overdraw" numeric(19,2),
  "fundbuy" numeric(19,2),
  "fundsale" numeric(19,2),
  "funduncomebuy" numeric(19,2),
  "funduncomesale" numeric(19,2),
  "fundfrz" numeric(19,2),
  "fundunfrz" numeric(19,2),
  "fundtrdfrz" numeric(19,2),
  "fundtrdunfrz" numeric(19,2),
  "fundnightfrz" numeric(19,2),
  "fundloan" numeric(19,2),
  "creditbal" numeric(19,2),
  "creditbuysale" numeric(19,2),
  "fundflag" char(1) COLLATE "pg_catalog"."default",
  "marketvalue" numeric(19,2),
  "fundstandby" numeric(19,2),
  "fundbuysale" numeric(19,2),
  "fundbrkbuy" numeric(19,2),
  "fundrealbuy" numeric(19,2),
  "fundbalprefrz" numeric(19,2),
  "fundcashpro" numeric(19,2),
  "fundavlprefrz" numeric(19,2)
)
;
