use s9_real

select mp1.Ordem_prod_serv,
	mp1.codigo_adicional1 ---, mp1.codigo_barras
	, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString(mp1.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),'+',' '),'-',' ')
	, mp1.Quantidade, mp1.Preco_Final, mp1.Preco_Unitario
	---, GETDATE (), DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1, eomonth(getdate(), -1)
	--- , Codigo_Fabricante, Data_Passou_Efetivacao_Estoque
 from View_Movimento_Prod_serv as mp1  inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento
 inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem
 inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv
 where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '1'
 and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' and mv.desefetivado_estoque = '0'
 and mp1.estoque_efetivado = '1' and mp1.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' 
 and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
 and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN')
 and p.ordem_fabricante = '98' and p.inativo = '0' 
 and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
 and mp1.Data_Desefetivacao_Estoque is null


 order by mp1.Ordem_prod_serv