package com.ForoHub.ForoHub.infra.errores;

import org.springframework.validation.FieldError;

public record Error400(String campo, String mensaje) {

    public Error400(FieldError fieldError) {
        this(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }
}
