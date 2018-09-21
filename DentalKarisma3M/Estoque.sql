USE [S9_Real]
GO

SELECT p.codigo,
replace((case when Qtde_Estoque_Atual < 0 then 0 else qtde_estoque_atual end),',','.') as QtdeEstoque
  FROM View_Estoque_Atual_Filial_Prod_Serv as E inner join Prod_Serv as p on e.ordem_prod_serv = p.ordem
  where e.codigo_filial = 1 and e.Codigo_Prod_Serv <> '0'
  and e.Data_Alteracao between DATEADD(DAY, -90 , GETDATE()) AND getdate()
  and p.ordem_fabricante = '98' and p.inativo = '0'
  and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
  order by Nome_Prod_Serv;
GO

/*
select * from View_Estoque_Atual_Filial_Prod_Serv
where codigo_filial =1 and Codigo_Prod_Serv <> '0' 
 and Data_Alteracao between DATEADD(DAY, -90 , GETDATE()) AND getdate() 
*/

