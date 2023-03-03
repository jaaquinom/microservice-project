package com.minsait.equipo2.msvc.cliente.services;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.cliente.models.Pedido;
import com.minsait.equipo2.msvc.cliente.repositories.ClienteRepository;

import com.minsait.equipo2.msvc.cliente.tiendaFeignClient.PedidoTienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoTienda pedidoTienda;

    @Override
    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findClienteById(Long id) {
        return clienteRepository.findById(id).get();
    }

    @Override
    public boolean deleteClienteById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()){
            clienteRepository.deleteById(id);
            return true;
        }else return false;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Pedido> pedido(List<Pedido> pedido) {
        List<Pedido> pedidoNuevo = pedidoTienda.envio(pedido);

        return pedidoNuevo;
    }


}
