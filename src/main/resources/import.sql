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
