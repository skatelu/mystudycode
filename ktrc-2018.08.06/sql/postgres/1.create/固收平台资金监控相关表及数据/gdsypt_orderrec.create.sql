USE [run]
GO

/****** Object:  Table [dbo].[gdsypt_orderrec]    Script Date: 2018/9/19 11:17:51 ******/
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[gdsypt_orderrec](
	[serverid] [dbo].[dtsno] NOT NULL,
	[ordersno] [dbo].[dtsno] NOT NULL,
	[orderid] [dbo].[dtorder] NOT NULL,
	[orderdate] [dbo].[dtdate] NOT NULL,
	[operdate] [dbo].[dtdate] NOT NULL,
	[opertime] [dbo].[dttime] NOT NULL,
	[custid] [dbo].[dtcustid] NOT NULL,
	[custname] [dbo].[dtname] NOT NULL,
	[orgid] [dbo].[dtorgid] NOT NULL,
	[brhid] [dbo].[dtbranch] NOT NULL,
	[fundid] [dbo].[dtfundid] NOT NULL,
	[moneytype] [dbo].[dtkind] NOT NULL,
	[fundkind] [dbo].[dtkind] NOT NULL,
	[fundlevel] [dbo].[dtkind] NOT NULL,
	[fundgroup] [dbo].[dtkind] NOT NULL,
	[secuid] [dbo].[dtsecuid] NOT NULL,
	[rptsecuid] [dbo].[dtsecuid] NOT NULL,
	[market] [dbo].[dtkind] NOT NULL,
	[seat_src] [dbo].[dtseat] NOT NULL,
	[seat] [dbo].[dtseat] NOT NULL,
	[rptseat] [dbo].[dtseat] NOT NULL,
	[stkcode] [dbo].[dtstkcode] NOT NULL,
	[stkname] [dbo].[dtstkname] NOT NULL,
	[stktype] [dbo].[dtkind] NOT NULL,
	[orderprice] [dbo].[dtprice] NOT NULL,
	[bondintr] [dbo].[dtrate] NOT NULL,
	[orderqty] [dbo].[dtqty] NOT NULL,
	[matchqty] [dbo].[dtqty] NOT NULL,
	[cancelqty] [dbo].[dtqty] NOT NULL,
	[reportqty] [dbo].[dtqty] NOT NULL,
	[matchamt] [dbo].[dtamt] NOT NULL,
	[orderfrzamt] [dbo].[dtamt] NOT NULL,
	[clearamt] [dbo].[dtamt] NOT NULL,
	[tradefee] [dbo].[dtfee] NOT NULL,
	[confernum] [dbo].[dtsno] NOT NULL,
	[targettrader] [dbo].[dtchar6] NOT NULL,
	[bsflag] [dbo].[dtchar2] NOT NULL,
	[cancelflag] [dbo].[dtkind] NOT NULL,
	[reporttime] [dbo].[dttime] NOT NULL,
	[orderstatus] [dbo].[dtkind] NOT NULL,
	[recnum] [dbo].[dtqty] NOT NULL,
	[ordersno_jys] [dbo].[dtsno] NOT NULL,
	[orderid_src] [dbo].[dtorder] NOT NULL
) ON [PRIMARY]
SET ANSI_PADDING ON
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [channel] [dbo].[dtchar8] NOT NULL
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [operid] [dbo].[dtoperid] NOT NULL
SET ANSI_PADDING OFF
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [netaddr] [dbo].[dtchar255] NOT NULL
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [operway] [dbo].[dtkind] NOT NULL
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [reportkind] [dbo].[dtchar8] NOT NULL
ALTER TABLE [dbo].[gdsypt_orderrec] ADD [remark] [dbo].[dtchar128] NOT NULL
 CONSTRAINT [gdsypt_orderrec_pk] PRIMARY KEY NONCLUSTERED 
(
	[ordersno] ASC,
	[orderdate] ASC,
	[serverid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

