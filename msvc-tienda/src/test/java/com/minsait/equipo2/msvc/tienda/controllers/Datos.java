package com.minsait.equipo2.msvc.tienda.controllers;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import java.util.Optional;

public class Datos {
    public static Optional<Tienda> crearTienda(){
        return Optional.of(new Tienda(1L, "Tienda 1","Domicilio conocido", 0,0));
    }
    public static Optional<Producto> crearProducto1(){
        return Optional.of(new Producto(1L,"Colita de cerdo",10));

    }public static Optional<Producto> crearProducto2(){
        return Optional.of(new Producto(1L,"Codorniz Deshidratada",10));
    }
}
