
USE S9_REAL

select distinct mp.data_efetivacao_estoque, c.fisica_juridica, c.CEP, c.Estado,
                Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace( Replace(Replace(Replace(Replace(SubString
                (c.Cidade,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),',', ' '),
				Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace( Replace(Replace(Replace(Replace(SubString
                (c.Endereco,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),',', ' '),
                Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
                (c.Bairro,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),
                Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
                (c.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i')
                , c.fax, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString
                (c.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i')
                 , c.cnpj_sem_literais, c.cpf_sem_literais
				 from cli_for as c inner join Movimento as mv on c.ordem = mv.ordem_cli_for 
				 inner join Movimento_Prod_serv as mp on mv.ordem = mp.Ordem_Movimento 
                 inner join prod_serv as p on mp.Ordem_Prod_Serv = p.ordem
				 inner join View_Estoque_Atual_Filial_Prod_Serv as e on e.ordem_prod_serv = p.ordem
                 where ordem_fabricante = '98' and e.codigo_filial =1 and c.cep <> '' and c.cep <> '0'
                 ---and mp.data_efetivacao_estoque between DATEADD(DAY, -390 , GETDATE()) AND getdate()
				 and c.codigo > 0
				 and (c.cnpj_sem_literais <> '' or c.cpf_sem_literais <> '') and (c.cnpj_sem_literais <> '0' or c.cpf_sem_literais <> '0')
				 and cpf_sem_literais not in ('0')
				 order by 7
		
	
/*
--- SELECT top 20 * FROM prod_serv where codigo_fabricante = '156'
--- select * from fabricantes
 select distinct
  c.codigo, c.fisica_juridica, c.cnpj_sem_literais, c.cpf_sem_literais, c.Nome, c.CEP, c.Estado, c.Cidade, p.ordem_fabricante
   from cli_for as c inner join Movimento as mv
 on c.ordem = mv.ordem_cli_for inner join Movimento_Prod_serv as mp on mv.ordem = mp.Ordem_Movimento 
 inner join prod_serv as p on mp.Ordem_Prod_Serv = p.ordem
 where ordem_fabricante = '98' 
   and data_efetivado_estoque between DATEADD(DAY, -90 , GETDATE()) AND getdate()
--- select top 20 * from cli_for
*/