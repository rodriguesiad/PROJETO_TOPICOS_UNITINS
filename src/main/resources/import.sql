-- This file allow to write SQL commands that will be emitted in test and dev.

insert into estado (datainclusao, sigla, descricao) values( '2023-04-15', 'TO', 'Tocantins');
insert into estado (datainclusao, sigla, descricao) values( '2023-04-15', 'GO', 'Goiás');
insert into estado (datainclusao, sigla, descricao) values( '2023-04-15', 'SP', 'São Paulo');
insert into estado (datainclusao, sigla, descricao) values( '2023-04-15', 'RJ', 'Rio de Janeiro');
insert into estado (datainclusao, sigla, descricao) values( '2023-04-15', 'PA', 'Pará');

insert into municipio (datainclusao, descricao, id_estado) values('2023-04-15', 'Palmas', 1);
insert into municipio (datainclusao, descricao, id_estado) values('2023-04-15', 'Paraíso do Tocantins', 1);
insert into municipio (datainclusao, descricao, id_estado) values('2023-04-15', 'Porto Nacional', 1);
insert into municipio (datainclusao, descricao, id_estado) values('2023-04-15', 'Gurupi', 1);

INSERT INTO public.tipoproduto(datainclusao, descricao) VALUES ('2023-04-15', 'Barro queimado');
INSERT INTO public.tipoproduto(datainclusao, descricao) VALUES ('2023-04-15', 'Copo');

INSERT INTO public.artesao(datainclusao, descricao, nome) VALUES ('2023-04-15', 'Intagram: @artesanatoNato', 'Nato Artesão');