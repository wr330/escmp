create   function  F_getSubjectNo
(  
   @oid nvarchar(100) --科目ID
)
returns varchar(100)
as
begin
  declare @final varchar(100)    --科目序号
  declare @parentNode nvarchar(100) --父节点
  declare @orderNo varchar(100)     --科目序号
  SELECT    @parentNode = ParentNode,@orderNo = OrderNo  FROM Fly_Subject where  Oid = @oid
  if(LEN(@orderNo)=1)
    begin
      set @orderNo = '0'+@orderNo  --不足两位补0，防止排序不准（不然'2'会排在'10'后面）
    end
  if(@parentNode is null)
    begin
      set @final = @orderNo
    end
  else
    begin
       set @final = dbo.F_getSubjectNo(@parentNode)+'.'+@orderNo --递归向上查询
    end
  return @final
end