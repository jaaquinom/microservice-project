package com.minsait.equipo2.msvc.cliente.services;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.proveedor.models.Producto;
import java.util.List;

public interface ClienteService {
    List<Cliente> findAllClientes();
    Cliente findClienteById(Long id);
    boolean deleteClienteById(Long id);
    Cliente save(Cliente cliente);
}
