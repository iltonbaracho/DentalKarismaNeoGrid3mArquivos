USE [S9_Real]
GO

SELECT distinct p.[Codigo], p.codigo_adicional1, e.Codigo_Prod_Serv, e.codigo_filial
		, Replace(Replace([Promocao],'1','02'),'0','01') as promo
		, Replace(pc.preco, ',','.') as Preco
		, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
		(p.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),'+',' ') as NomePro
		, replace(replace(p.Inativo, '1','02'),'0','01')		
  FROM [dbo].[Prod_Serv] as p inner join View_Movimento_Prod_serv as mp
  on p.ordem = mp.ordem_prod_serv inner join prod_serv_precos as pc on p.Ordem = pc.ordem_prod_serv
  inner join View_Estoque_Atual_Filial_Prod_Serv as E on e.ordem_prod_serv = p.ordem
  where pc.Ordem_Tabela_Preco = '2' and p.inativo = '0' and ordem_fabricante = '98' and e.codigo_filial =1
  and mp.data_efetivacao_estoque between DATEADD(DAY, -90 , GETDATE()) AND getdate()
  and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
  order by p.codigo
GO


