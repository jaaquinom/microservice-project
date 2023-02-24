package com.minsait.equipo2.msvc.tienda.exceptions;

public class ProductoInsuficienteException extends RuntimeException{
    public ProductoInsuficienteException(String mensaje){
        super(mensaje);
    }
}
