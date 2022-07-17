package br.com.willbigas.algafood.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize tamanhoMaximo;

    @Override
    public void initialize(FileSize annotation) {
        this.tamanhoMaximo = DataSize.parse(annotation.max());
    }

    @Override
    public boolean isValid(MultipartFile valor, ConstraintValidatorContext contexto) {
        return valor == null || valor.getSize() <= this.tamanhoMaximo.toBytes();
    }
}
