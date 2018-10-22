use S9_Real

SELECT distinct '03' as TiReg, MNFe.Numero,
REPLICATE('0', 3 - LEN(MNFe.Identificacao_Documento)) + RTrim(MNFe.Identificacao_Documento) as Serie 
, Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'Dev', '02'), 'Can', '03') as TipoNF , MP1.[Codigo]
,format(MP1.[Quantidade], '#.00000'), Replace(MP1.[Preco_Unitario], ',','.'),'N' as Boni
,Replace(MP1.[Preco_Total_Sem_Desconto], ',','.'),Replace(MP1.[Preco_Total_Com_Desconto], ',','.'), Replace(MP1.[IPI_Valor], ',','.'), 
Replace(MP1.[PIS_Normal_Valor], ',','.'), Replace(MP1.[ICMS_Subst_Valor], ',','.'), Replace(MP1.[ICMS_Normal_Valor], ',','.'), Replace(MP1.[Desconto_Valor], ',','.') 
FROM View_Movimento_Prod_serv as MP1 inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento 
inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem 
inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '1' and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mp1.estoque_efetivado = '1' and mp1.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN') and p.ordem_fabricante = '98'
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
