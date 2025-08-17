-- ================================================
-- Template generated from Template Explorer using:
-- Create Trigger (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- See additional Create Trigger templates for more
-- examples of different Trigger statements.
--
-- This block of comments will not be included in
-- the definition of the function.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER dbo.trg_CNTNR_TYPE_UpdateLastModifiedDatetime
   ON  dbo.CNTNR_TYPE
   AFTER UPDATE
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	UPDATE CTN_T
	SET CTN_T.LST_MDFD_DTTM = GETDATE()
	FROM dbo.CNTNR_TYPE CTN_T
	JOIN inserted INS ON INS.CNTNR_TYPE_ID = CTN_T.CNTNR_TYPE_ID

END
GO
