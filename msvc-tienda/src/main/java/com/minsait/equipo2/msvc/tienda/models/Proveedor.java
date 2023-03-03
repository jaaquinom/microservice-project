package com.minsait.equipo2.msvc.tienda.models;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Proveedor {
    private Long id;

    private String nombre;

    private Integer cantidad;
}
