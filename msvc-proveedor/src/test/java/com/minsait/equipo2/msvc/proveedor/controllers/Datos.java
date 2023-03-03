package com.minsait.equipo2.msvc.proveedor.controllers;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;

import java.util.Optional;

public class Datos {
    public static Optional<Producto> crearProducto1(){
        return Optional.of(new Producto(1L, "Colita de cerdo", 50));
    }

    public static Optional<Producto> crearProducto2(){
        return Optional.of(new Producto(2L, "Oreja de cerdo", 40));
    }

    public static Optional<Proveedor> crearProveedor(){
        return Optional.of(new Proveedor(1L, "Insumos y materia prima", 0, 0));
    }
}
