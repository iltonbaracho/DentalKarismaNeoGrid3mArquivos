use s9_real
--- SELECT top 50 MNFe.[Ordem_Movimento],MNFe.[Data],MNFe.[Sequencia],MNFe.[Cli_For_Codigo],MNFe.[Preco_Final_Somado],MNFe.[Numero],MNFe.[Data_Emissao],MNFe.[Identificacao_Documento] as serie,MNFe.[Tipo_Documento]  FROM [dbo].[View_Movimento_NFe_Relatorio] as MNFe
--- 
SELECT MNFe.data_autorizacao, mv.apagado, mp1.inativo, '02' as TiReg, '01' as TiFat, MNFe.Numero, REPLICATE('0', 3 - LEN(MNFe.Identificacao_Documento)) + RTrim(MNFe.Identificacao_Documento) as Serie 
	  , Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'DEV', '02'), 'CAN', '03') as TipoNF
	  , Replace(Replace(Replace(Convert(VarChar(16),MNFe.[Data_Emissao],120),' ',''),'-',''),':',''), MP1.[Codigo_Vendedor]
	  , c.CNPJ_Sem_Literais, F.UF, F.Cep, C.Estado, C.CEP, 'CIF' as TipoFrete, '07' as Dias, c.CPF_Sem_Literais, c.fisica_juridica
 FROM View_Movimento_Prod_serv as MP1 inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento
 inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem
 inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv
 where mp1.codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '1'
 and mv.apagado <> '1' and mp1.inativo <> '1'
 and MNFe.Entrada_Saida = 'S'
 ---and MNFe.data_autorizacao  between DATEADD(DAY, -90 , GETDATE()) AND getdate()
 and mv.desefetivado_financeiro = '0' and mv.desefetivado_estoque = '0'
 and mv.efetivado_financeiro = '1' and mv.efetivado_estoque = '1'
 and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN')
 and p.ordem_fabricante = '98' and p.inativo = '0' 
 and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0')
 and C.CEP <> '' and C.CEP <>'0'

 ---and codigo_vendedor = '75'
 --- and MNFe.Numero = 18352
  
--- select top 50 * from View_Movimento_Resumo_NFe where Protocolo_Autorizacao = '333160092311819'
--- select * from NFe_Destinadas_Notas where Codigo_Situacao_NFe <> '0'
--- select top 20 * from View_Movimento_Vendas_Relatorio where Ordem = 100
--- select top 20 * from View_Financeiro_Resumo_Geral_Vendas_Relatorio
--- select top 20 * from View_Movimento_Vendas_Por_Cliente_Relatorio
--- select top 20 * from Movimento where Desefetivado_Estoque = '0' and Desefetivado_Financeiro = '0' and Apagado = '0' and Tipo_Operacao = 'VND' and Documento_Cancelado = '0' and codigo_fabricante = '156'
