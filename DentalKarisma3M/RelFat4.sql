use s9_real

select DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 ,GETDATE())) +1 as Dia1Mes, eomonth(getdate(), -1) as DiaFimMes
, mp1.codigo_barras, mp1.codigo_adicional1, mp1.Nome

, isNull (( Select Sum(mpVnd.Quantidade)
from View_Movimento_Prod_serv as mpVnd inner join movimento as MV on MV.ordem = mpVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'VND' and mpVnd.Codigo_Fabricante = '156' and mpVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpVnd.estoque_efetivado = '1' and mpVnd.efetivado_financeiro = '1' and mpVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpVnd.Ordem_prod_serv ), 0) QtdVend

, isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpPreVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'VND' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpPreVnd.Ordem_prod_serv ), 0) PreVend 
 
,isnull (( Select Sum(mpDev.Quantidade)
from View_Movimento_Prod_serv as mpDev inner join movimento as MV on MV.ordem = mpDev.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpDev.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'DEV' and mpDev.Codigo_Fabricante = '156' and mpDev.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpDev.estoque_efetivado = '1' and mpDev.efetivado_financeiro = '1' and mpDev.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpDev.Ordem_prod_serv ), 0) QtdDev

, ( isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpPreVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'DEV' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpPreVnd.Ordem_prod_serv ), 0)
 ) PreDev 

, '0,00' as QtdBoni, '0,00' as VlrBoni

,( isNull (( Select Sum(mpVnd.Quantidade)
from View_Movimento_Prod_serv as mpVnd inner join movimento as MV on MV.ordem = mpVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'VND' and mpVnd.Codigo_Fabricante = '156' and mpVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpVnd.estoque_efetivado = '1' and mpVnd.efetivado_financeiro = '1' and mpVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpVnd.Ordem_prod_serv ), 0) - 
isNull(( Select Sum(mpDev.Quantidade)
from View_Movimento_Prod_serv as mpDev inner join movimento as MV on MV.ordem = mpDev.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpDev.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'DEV' and mpDev.Codigo_Fabricante = '156' and mpDev.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpDev.estoque_efetivado = '1' and mpDev.efetivado_financeiro = '1' and mpDev.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpDev.Ordem_prod_serv ), 0)
)TotQtd

, ( isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpPreVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'VND' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpPreVnd.Ordem_prod_serv ), 0) -
isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
inner join [View_Movimento_NFe_Relatorio] as MNFe on mpPreVnd.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv 
where MV.Tipo_operacao = 'DEV' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and F.codigo = '1'
and MV.Sequencia = MNFe.Sequencia and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')
group by mpPreVnd.Ordem_prod_serv ), 0)
 ) TotPre 
 
 from View_Movimento_Prod_serv as mp1  inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento
 inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem
 inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv

 where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '1' and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' 
and mv.desefetivado_estoque = '0' and mp1.estoque_efetivado = '1' and mp1.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' 
and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
and p.inativo = '0' and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0')

group by mp1.Ordem_prod_serv, mp1.Nome, mp1.codigo_barras, mp1.codigo_adicional1
order by mp1.Ordem_prod_serv, mp1.codigo_barras