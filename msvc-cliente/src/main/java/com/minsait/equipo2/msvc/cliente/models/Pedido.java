package com.minsait.equipo2.msvc.cliente.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pedido {
    private Long Id;
    private String nombreProducto;
    private Integer cantidadProducto;
}
