USE [run]
GO

/****** Object:  Table [dbo].[stktrd]    Script Date: 2018/9/19 15:35:50 ******/
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[stktrd](
	[serverid] [dbo].[dtsno] NOT NULL,
	[market] [dbo].[dtkind] NOT NULL,
	[stkcode] [dbo].[dtstkcode] NOT NULL,
	[linkstk] [dbo].[dtstkcode] NOT NULL,
	[frzstk] [dbo].[dtstkcode] NOT NULL,
	[stkname] [dbo].[dtstkname] NOT NULL,
	[stktype] [dbo].[dtkind] NOT NULL,
	[trdid] [dbo].[dtkind] NOT NULL,
	[moneytype] [dbo].[dtkind] NOT NULL,
	[stopflag] [dbo].[dtkind] NOT NULL,
	[bondintr] [dbo].[dtrate] NOT NULL,
	[intrdate] [dbo].[dtdate] NOT NULL,
	[maxrisevalue] [dbo].[dtprice] NOT NULL,
	[maxdownvalue] [dbo].[dtprice] NOT NULL,
	[fixprice] [dbo].[dtprice] NOT NULL,
	[limitpriceflag] [dbo].[dtkind] NOT NULL,
	[defaultpricetype] [dbo].[dtkind] NOT NULL,
	[maxriserate] [dbo].[dtint] NOT NULL,
	[maxdownrate] [dbo].[dtint] NOT NULL,
	[pt_ordertype] [dbo].[dtchar8] NOT NULL,
	[keepmode] [dbo].[dtkind] NOT NULL,
	[ticketprice] [dbo].[dtamt] NOT NULL,
	[mtkcalflag] [dbo].[dtkind] NOT NULL,
	[bsflag] [dbo].[dtkind] NOT NULL,
	[cancelflag] [dbo].[dtkind] NOT NULL,
	[priceflag] [dbo].[dtkind] NOT NULL,
	[stkoverdraw] [dbo].[dtkind] NOT NULL,
	[fundrealback] [dbo].[dtkind] NOT NULL,
	[stockrealback] [dbo].[dtkind] NOT NULL,
	[reserveprop] [dbo].[dtkinds] NOT NULL,
	[settlefundays] [dbo].[dtint] NOT NULL,
	[settlestkdays] [dbo].[dtint] NOT NULL,
	[funduseflag] [dbo].[dtkind] NOT NULL,
	[stkusedflag] [dbo].[dtkind] NOT NULL,
	[priceunit] [dbo].[dtint] NOT NULL,
	[buyunit] [dbo].[dtint] NOT NULL,
	[saleunit] [dbo].[dtint] NOT NULL,
	[exchunit] [dbo].[dtint] NOT NULL,
	[maxqty] [dbo].[dtqty] NOT NULL,
	[minqty] [dbo].[dtqty] NOT NULL,
	[firstdate] [dbo].[dtdate] NOT NULL,
	[upddate] [dbo].[dtdate] NOT NULL,
	[minbuyqty] [dbo].[dtqty] NOT NULL,
	[totalqty] [dbo].[dtamt] NOT NULL,
	[cirqty] [dbo].[dtamt] NOT NULL,
	[lastlimitday] [dbo].[dtqty] NOT NULL,
	[limitday] [dbo].[dtqty] NOT NULL,
	[quitdate] [dbo].[dtint] NOT NULL,
	[memotext] [dbo].[dtkinds] NOT NULL,
	[chkmktvalueflag] [dbo].[dtkind] NOT NULL,
	[jyssettmode] [dbo].[dtkind] NOT NULL,
	[feestktype] [dbo].[dtkind] NOT NULL,
	[stkattr] [dbo].[dtkind] NOT NULL,
	[noprofit] [dbo].[dtkind] NOT NULL,
	[voterights] [dbo].[dtkind] NOT NULL,
 CONSTRAINT [stktrd_pk] PRIMARY KEY CLUSTERED 
(
	[market] ASC,
	[stkcode] ASC,
	[serverid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

