package com.minsait.equipo2.msvc.cliente.services;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.cliente.models.Pedido;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAllClientes();
    Cliente findClienteById(Long id);
    boolean deleteClienteById(Long id);
    Cliente save(Cliente cliente);

    List<Pedido> pedido(List<Pedido> pedido);
}
