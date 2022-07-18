CREATE TABLE `algafood_learn`.`foto_produto`
(
    `produto_id`   BIGINT       NOT NULL,
    `nome_arquivo` VARCHAR(150) NOT NULL,
    `descricao`    VARCHAR(150) NOT NULL,
    `content_type` VARCHAR(80)  NOT NULL,
    `tamanho`      INT          NOT NULL,
    PRIMARY KEY (`produto_id`),
    CONSTRAINT `fk_foto_produto_produto`
        FOREIGN KEY (`produto_id`)
            REFERENCES `algafood_learn`.`produto` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);