alter table restaurante add ativo tinyint(1) DEFAULT 1 not null;
update restaurante set ativo = 1;