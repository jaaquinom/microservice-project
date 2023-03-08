package com.minsait.equipo2.msvc.cliente.services;

import com.minsait.equipo2.msvc.cliente.controllers.Datos;
import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.cliente.models.Pedido;
import com.minsait.equipo2.msvc.cliente.repositories.ClienteRepository;
import com.minsait.equipo2.msvc.cliente.tiendaFeignClient.PedidoTienda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
    @Mock
    ClienteRepository clienteRepository;
    @Mock
    PedidoTienda pedidoTienda;
    @InjectMocks
    ClienteServiceImpl service;

    @Test
    void testFindAllClientes(){
        when(clienteRepository.findAll()).thenReturn(List.of(Datos.crearCliente1().get(), Datos.crearCliente2().get()));
        List<Cliente> clientes = service.findAllClientes();
        assertFalse(clientes.isEmpty());
        verify(clienteRepository, atLeastOnce()).findAll();
    }

    @Test
    void testFindClienteById(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(Datos.crearCliente1().get()));
        Cliente cliente = service.findClienteById(1L);
        assertEquals(cliente, Datos.crearCliente1().get());
    }

    @Test
    void testDeleteClienteById(){
        Cliente cliente = Datos.crearCliente1().get();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        boolean clienteDelete = service.deleteClienteById(1L);
        assertTrue(clienteDelete);
    }

    @Test
    void testDeleteClienteByIdNotFound(){
        boolean clienteDelete = service.deleteClienteById(5L);
        assertFalse(clienteDelete);
    }

    @Test
    void testSave(){
        Cliente cliente = Datos.crearCliente1().get();
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente clienteGuardado = service.save(cliente);
        assertEquals(clienteGuardado, cliente);
    }

    @Test
    void testPedido(){
        Pedido pedidoNuevo = new Pedido();
        pedidoNuevo.setId(1L);
        pedidoNuevo.setNombreProducto("Colita de cerdo");
        pedidoNuevo.setCantidadProducto(10);
        List<Pedido> listaPedido = List.of(pedidoNuevo);
        when(pedidoTienda.envio(listaPedido)).thenReturn(listaPedido);
        List<Pedido> pedidoReal = service.pedido(List.of(pedidoNuevo));
        assertEquals(listaPedido, pedidoReal);
    }

    @Test
    void testFindAllProductosTienda() {
        Pedido pedidoNuevo = new Pedido();
        pedidoNuevo.setId(1L);
        pedidoNuevo.setNombreProducto("Colita de cerdo");
        pedidoNuevo.setCantidadProducto(10);
        List<Pedido> listaPedido = List.of(pedidoNuevo);
        when(pedidoTienda.findAll()).thenReturn(listaPedido);
        List<Pedido> pedidoReal = service.findAll();
        assertEquals(listaPedido, pedidoReal);
    }
}