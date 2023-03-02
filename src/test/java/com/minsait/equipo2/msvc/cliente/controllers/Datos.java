package com.minsait.equipo2.msvc.cliente.controllers;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import java.util.Optional;

public class Datos {
    public static Optional<Cliente> crearCliente1(){
        return Optional.of(new Cliente(1L, "Yamani", "Estado de México", 0));
    }
    public static Optional<Cliente> crearCliente2(){
        return Optional.of(new Cliente(2L, "Jorge", "Acapulco", 0));
    }
}
