package com.inditex.msprices.exception;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
    ERROR_INTERNO(ErrorMessage.builder()
            .code("MSPRI-001")
            .message("Ha ocurrido un error interno")
            .key("api.error.mspri.001")
            .build()),
    ERROR_INICIALIZACION_DATOS(ErrorMessage.builder()
            .code("MSPRI-002")
            .message("No fue posible inicializar los datos")
            .key("api.error.mspri.002")
            .build()),
    ERROR_PARSEO_FECHA_APLICACION(ErrorMessage.builder()
            .code("MSPRI-003")
            .message("Error al parsear fecha desde request")
            .key("api.error.mspri.003")
            .build()),
    ERROR_SIN_DATOS(ErrorMessage.builder()
            .code("MSPRI-004")
            .message("Sin datos para el criterio de busqueda de tarifas")
            .key("api.error.mspri.004")
            .build());

    private ErrorMessage error;

    ErrorCatalog(ErrorMessage error) {
        this.error = error;
    }

    public String getLogError() {
        return String.format("%s - %s",error.getCode(), error.getMessage());
    }
}
