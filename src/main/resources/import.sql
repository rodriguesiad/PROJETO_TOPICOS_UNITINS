-- This file allow to write SQL commands that will be emitted in test and dev.
-- login: maria, senha: 123

INSERT INTO  estado (datainclusao, sigla, descricao) VALUES( '2023-04-15', 'TO', 'Tocantins');
INSERT INTO  estado (datainclusao, sigla, descricao) VALUES( '2023-04-15', 'GO', 'Goiás');
INSERT INTO  estado (datainclusao, sigla, descricao) VALUES( '2023-04-15', 'SP', 'São Paulo');
INSERT INTO  estado (datainclusao, sigla, descricao) VALUES( '2023-04-15', 'RJ', 'Rio de Janeiro');
INSERT INTO  estado (datainclusao, sigla, descricao) VALUES( '2023-04-15', 'PA', 'Pará');

INSERT INTO  municipio (datainclusao, descricao, id_estado) VALUES('2023-04-15', 'Palmas', 1);
INSERT INTO  municipio (datainclusao, descricao, id_estado) VALUES('2023-04-15', 'Paraíso do Tocantins', 1);
INSERT INTO  municipio (datainclusao, descricao, id_estado) VALUES('2023-04-15', 'Porto Nacional', 1);
INSERT INTO  municipio (datainclusao, descricao, id_estado) VALUES('2023-04-15', 'Gurupi', 1);

INSERT INTO pessoa(nome) VALUES ('Maria');
INSERT INTO telefone(codigoArea, numero) VALUES ('63', '123456789');
INSERT INTO telefone(codigoArea, numero) VALUES ('63', '123456789');
INSERT INTO pessoafisica(id, cpf, email, data_nascimento) VALUES (1, '47652930090', 'maria@gmail', '2002-10-10');
INSERT INTO usuario(login, senha, id_telefone_celular, id_telefone_whatsapp, id_pessoa_fisica) VALUES ('maria', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 1, 2, 1);

INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'Admin');
INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'User');

INSERT INTO public.tipoproduto(datainclusao, descricao) VALUES ('2023-04-15', 'Barro queimado');
INSERT INTO public.tipoproduto(datainclusao, descricao) VALUES ('2023-04-15', 'Copo');
INSERT INTO public.artesao(datainclusao, descricao, nome) VALUES ('2023-04-15', 'Intagram: @artesanatoNato', 'Nato Artesão');
INSERT INTO public.produto(estoque, preco, dataalteracao, datainclusao, id, nome, descricao) VALUES (1, 10, null, '2023-05-26', 1, 'Panela', 'Panela de barro');
INSERT INTO public.artigoceramica(quantidadepecas, id, id_artesao) VALUES (1, 1, 1);
INSERT INTO public.artigo_tipo(id_artigo, id_tipo) VALUES (1, 1);

INSERT INTO public.endereco(
    principal, cep, dataalteracao, datainclusao, id, id_municipio, id_usuario, titulo, bairro, logradouro, numero, complemento)
VALUES (true, '77060182', '2023-04-15', '2023-04-15', 1, 1, 1, 'Casa', 'Aureny II', 'Goias', '15', null);

INSERT INTO public.cartao(
    dataalteracao, datainclusao, id, id_usuario, numerocartao, nometitular, hash)
VALUES ('2023-04-15', '2023-04-15', 1, 1, '123', 'Maria Aparecisa', 'JOXZUIpzapsZwLJsg1/jHQ/GcVTMcv7wTLCpXnN6kgpKwvWpQjBnOq/i3XA6HphyUh74eC3S0vme+Lnu55rWOQ==');

INSERT INTO public.enderecocompra(
    cep, dataalteracao, datainclusao, id, id_municipio, bairro, logradouro, numero, complemento)
VALUES ('77060182', '2023-04-15', '2023-04-15', 1, 1, 'Aureny II', 'Goias', '15', null);

INSERT INTO public.enderecocompra(
    cep, dataalteracao, datainclusao, id, id_municipio, bairro, logradouro, numero, complemento)
VALUES ('77060182', '2023-04-15', '2023-04-15', 2, 1, 'Aureny II', 'Goias', '15', null);

INSERT INTO public.compra(
    status, total_compra, dataalteracao, datainclusao, datapagamento, id, id_endereco, id_metodo_pagamento, id_usuario)
VALUES (1, 20, '2023-04-15', '2023-04-15', null, 1, 1, null, 1);

INSERT INTO public.compra(
    status, total_compra, dataalteracao, datainclusao, datapagamento, id, id_endereco, id_metodo_pagamento, id_usuario)
VALUES (1, 20, '2023-04-15', '2023-04-15', null, 2, 2, null, 1);

INSERT INTO public.itemcompra(
    preco, quantidade, dataalteracao, datainclusao, id, id_compra, id_produto)
VALUES (20, 2, '2023-04-15', '2023-04-15', 1, 1, 1);

INSERT INTO public.itemcompra(
    preco, quantidade, dataalteracao, datainclusao, id, id_compra, id_produto)
VALUES (20, 2, '2023-04-15', '2023-04-15', 2, 2, 1);

INSERT INTO public.historicoentrega(
    data, dataalteracao, datainclusao, id, id_compra, titulo, descricao)
VALUES ('2023-06-09', '2023-04-15', '2023-04-15' , 1, 2, 'Despachado', 'Produto despachado');

INSERT INTO public.metododepagamento(
    dataalteracao, datainclusao, id, id_compra)
VALUES ('2023-06-09', '2023-06-09', 1, 2);

INSERT INTO public.cartaocredito(
    quantidade_parcelas, valor_parcelas, id, id_cartao)
VALUES (2, 10, 1, 1);

UPDATE public.compra
SET status = 4, datapagamento = '2023-06-09',  id_metodo_pagamento= 1
WHERE id = 2;


