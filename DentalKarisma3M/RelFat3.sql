use s9_real on mpDev.ordem_movimento = MNFe.ordem_movimento 
inner join Filiais as F on MV.Ordem_Filial = F.Ordem inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem  
inner join Prod_Serv as p on p.ordem = mp1.or

select DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 ,GETDATE())) +1 as Dia1Mes, eomonth(getdate(), -1) as DiaFimMes, mp1.codigo_barras, mp1.Nome

, ( Select Sum(mpVnd.Quantidade)
from View_Movimento_Prod_serv as mpVnd inner join movimento as MV on MV.ordem = mpVnd.Ordem_Movimento
where MV.Tipo_operacao = 'VND' and mpVnd.Codigo_Fabricante = '156' and mpVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpVnd.estoque_efetivado = '1' and mpVnd.efetivado_financeiro = '1' and mpVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpVnd.Ordem_prod_serv ) QtdVend

, ( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
where MV.Tipo_operacao = 'VND' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpPreVnd.Ordem_prod_serv ) PreVend 
 
,isnull (( Select Sum(mpDev.Quantidade)
from View_Movimento_Prod_serv as mpDev inner join movimento as MV on MV.ordem = mpDev.Ordem_Movimento
where MV.Tipo_operacao = 'DEV' and mpDev.codigo_fabricante = '156' and mpDev.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpDev.estoque_efetivado = '1' and mpDev.efetivado_financeiro = '1' and mpDev.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpDev.Ordem_prod_serv ), 0) QtdDev

, ( isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
where MV.Tipo_operacao = 'DEV' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpPreVnd.Ordem_prod_serv ), 0)
 ) PreDev 

, '0,00' as QtdBoni, '0,00' as VlrBoni

,( ( Select Sum(mpVnd.Quantidade)
from View_Movimento_Prod_serv as mpVnd inner join movimento as MV on MV.ordem = mpVnd.Ordem_Movimento
where MV.Tipo_operacao = 'VND' and mpVnd.Codigo_Fabricante = '156' and mpVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpVnd.estoque_efetivado = '1' and mpVnd.efetivado_financeiro = '1' and mpVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpVnd.Ordem_prod_serv ) - 
isNull(( Select Sum(mpDev.Quantidade)
from View_Movimento_Prod_serv as mpDev inner join movimento as MV on MV.ordem = mpDev.Ordem_Movimento
where MV.Tipo_operacao = 'DEV' and mpDev.codigo_fabricante = '156' and mpDev.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpDev.estoque_efetivado = '1' and mpDev.efetivado_financeiro = '1' and mpDev.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpDev.Ordem_prod_serv ), 0)
)TotQtd

, ( ( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
where MV.Tipo_operacao = 'VND' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpPreVnd.Ordem_prod_serv ) -
isNull (( Select Sum(mpPreVnd.Preco_Final)
from View_Movimento_Prod_serv as mpPreVnd inner join movimento as MV on MV.ordem = mpPreVnd.Ordem_Movimento
where MV.Tipo_operacao = 'DEV' and mpPreVnd.Codigo_Fabricante = '156' and mpPreVnd.Ordem_prod_serv=mp1.Ordem_prod_serv
and mpPreVnd.estoque_efetivado = '1' and mpPreVnd.efetivado_financeiro = '1' and mpPreVnd.Estoque_Desefetivado = '0' 
and MV.Data_Passou_Efetivacao_Estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1)
group by mpPreVnd.Ordem_prod_serv ), 0)
 ) TotPre 
 
 from View_Movimento_Prod_serv as mp1  inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento
 inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem
 inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv
 where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '2'
 and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' and mv.desefetivado_estoque = '0'
 and mp1.estoque_efetivado = '1' and mp1.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' 
 and MNFe.data_autorizacao between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
 and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN')
 and p.ordem_fabricante = '98' and p.inativo = '0' 
 and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'
 and mp1.Data_Desefetivacao_Estoque is null

group by mp1.Nome, mp1.codigo_barras, mp1.Ordem_prod_serv
order by mp1.Ordem_prod_serv