USE [S9_Real]
GO

SELECT distinct p.Codigo, p.codigo_adicional1, p.Codigo, f.codigo
		, Replace(Replace(mp.Utilizou_preco_promocional ,'1','02'),'0','01') as promo
		, Replace(pc.preco, ',','.') as Preco
		, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
		(p.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),'+',' ') as NomePro
		, replace(replace(p.Inativo, '1','02'),'0','01')		
  FROM Prod_Serv as p inner join Movimento_Prod_serv as mp
  on p.ordem = mp.ordem_prod_serv inner join prod_serv_precos as pc on p.Ordem = pc.ordem_prod_serv
  inner join Estoque_Atual as E on e.ordem_prod_serv = p.ordem 
  inner join Filiais as F on e.Ordem_Filial = F.Ordem 
  where pc.Ordem_Tabela_Preco = '2' and p.inativo = '0' 
  and p.ordem_fabricante = '98' and F.codigo = '1'
  and mp.data_efetivacao_estoque between DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
  and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
  order by p.codigo
GO


