USE [S9_Real]
GO

SELECT p.codigo,
replace((case when Qtde_Estoque_Atual < 0 then 0 else qtde_estoque_atual end),',','.') as QtdeEstoque
  FROM Estoque_Atual as E inner join Prod_Serv as p on e.ordem_prod_serv = p.ordem
  where e.ordem_filial = 1 and p.Codigo <> '0'
  and e.Data_Alteracao between DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
  and p.ordem_fabricante = '98' and p.inativo = '0'
  and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
  order by p.codigo;
GO

/*
select * from View_Estoque_Atual_Filial_Prod_Serv
where codigo_filial =1 and Codigo_Prod_Serv <> '0' 
 and Data_Alteracao between DATEADD(DAY, -90 , GETDATE()) AND getdate() 
*/

