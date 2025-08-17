USE [Bot_Of_Holding_DEV]
GO

/****** Object:  Table [dbo].[ITEM]    Script Date: 6/13/2025 11:46:31 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ITEM](
	[item_id] [numeric](9, 0) IDENTITY(1,1) NOT NULL,
	[item_nme] [nvarchar](50) NOT NULL,
	[item_desc] [nvarchar](250) NULL,
	[CRE_ID] [numeric](9, 0) NULL,
	[cre_dttm] [datetime] NOT NULL,
	[LST_MDFD_ID] [numeric](9, 0) NULL,
	[LST_MDFD_DTTM] [datetime] NOT NULL,
	[SRVR_ID] [numeric](18, 0) NULL,
 CONSTRAINT [PK_ITEM] PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ITEM] ADD  CONSTRAINT [DF_ITEM_cre_dttm]  DEFAULT (getdate()) FOR [cre_dttm]
GO

ALTER TABLE [dbo].[ITEM] ADD  CONSTRAINT [DF_ITEM_LST_MDFD_DTTM]  DEFAULT (getdate()) FOR [LST_MDFD_DTTM]
GO


