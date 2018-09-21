use s9_real
SELECT distinct
Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
(f.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),
f.Codigo FROM Funcionarios as f
inner join View_Movimento_Prod_serv as MP1 on f.codigo = mp1.codigo_vendedor
where f.codigo > 0 and mp1.codigo_fabricante = '156' and mp1.inativo = '0'
and mp1.data_efetivacao_estoque  between DATEADD(DAY, -90 , GETDATE()) AND getdate()
 and mp1.estoque_desefetivado = '0'
 and mp1.Efetivado_Financeiro = '1' and mp1.estoque_efetivado = '1';