ALTER TABLE restaurante ADD aberto TINYINT(1) DEFAULT 1 NOT NULL;
update restaurante set aberto = false;