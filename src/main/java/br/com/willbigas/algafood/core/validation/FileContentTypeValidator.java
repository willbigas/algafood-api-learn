package br.com.willbigas.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> tiposPermitidos;

    @Override
    public void initialize(FileContentType annotation) {
        this.tiposPermitidos = Arrays.asList(annotation.tiposPermitidos());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext contexto) {
        return multipartFile == null
                || this.tiposPermitidos.contains(multipartFile.getContentType());
    }
}
