USE [run]
GO

/****** Object:  Table [dbo].[gdsypt_match]    Script Date: 2018/9/19 11:17:29 ******/
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[gdsypt_match](
	[serverid] [dbo].[dtsno] NOT NULL,
	[matchsno] [dbo].[dtsno] NOT NULL,
	[operdate] [dbo].[dtdate] NOT NULL,
	[trddate] [dbo].[dtdate] NOT NULL,
	[custid] [dbo].[dtcustid] NOT NULL,
	[fundid] [dbo].[dtfundid] NOT NULL,
	[moneytype] [dbo].[dtkind] NOT NULL,
	[fundkind] [dbo].[dtkind] NOT NULL,
	[fundlevel] [dbo].[dtkind] NOT NULL,
	[fundgroup] [dbo].[dtkind] NOT NULL,
	[orgid] [dbo].[dtorgid] NOT NULL,
	[brhid] [dbo].[dtbranch] NOT NULL,
	[secuid] [dbo].[dtsecuid] NOT NULL,
	[rptsecuid] [dbo].[dtsecuid] NOT NULL,
	[bsflag] [dbo].[dtchar2] NOT NULL,
	[rptbs] [dbo].[dtchar4] NOT NULL,
	[matchtype] [dbo].[dtkind] NOT NULL,
	[ordersno] [dbo].[dtsno] NOT NULL,
	[orderid] [dbo].[dtorder] NOT NULL,
	[market] [dbo].[dtkind] NOT NULL,
	[stkcode] [dbo].[dtstkcode] NOT NULL,
	[stkname] [dbo].[dtstkname] NOT NULL,
	[stktype] [dbo].[dtkind] NOT NULL,
	[trdid] [dbo].[dtkind] NOT NULL,
	[orderprice] [dbo].[dtprice] NOT NULL,
	[bondintr] [dbo].[dtrate] NOT NULL,
	[orderqty] [dbo].[dtqty] NOT NULL,
	[seat] [dbo].[dtseat] NOT NULL,
	[matchtime] [dbo].[dtdate] NOT NULL,
	[matchprice] [dbo].[dtprice] NOT NULL,
	[matchqty] [dbo].[dtqty] NOT NULL,
	[matchamt] [dbo].[dtamt] NOT NULL,
	[matchcode] [dbo].[dtmatchcode] NOT NULL,
	[clearamt] [dbo].[dtamt] NOT NULL,
	[tradefee] [dbo].[dtfee] NOT NULL,
	[operid] [dbo].[dtoperid] NOT NULL,
	[operlevel] [dbo].[dtkind] NOT NULL,
	[operorg] [dbo].[dtorgid] NOT NULL,
	[operway] [dbo].[dtkind] NOT NULL,
	[recnum] [dbo].[dtqty] NOT NULL,
	[remark] [dbo].[dtchar32] NOT NULL,
 CONSTRAINT [gdsypt_match_pk] PRIMARY KEY NONCLUSTERED 
(
	[matchsno] ASC,
	[trddate] ASC,
	[serverid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

