package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.controllers.Datos;
import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.proveedor.repositories.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceImplTest {
    @Mock
    ProveedorRepository proveedorRepository;
    @Mock
    ProductoRepository productoRepository;
    @InjectMocks
    ProveedorServiceImpl service;

    /**
     * Métodos Proveedor
     */
    @Test
    void testInfo(){
        given(proveedorRepository.findById(1L)).willReturn(Optional.of(Datos.crearProveedor().get()));
        Proveedor proveedor = service.info(1L);
        assertTrue(proveedor.getId().equals(Datos.crearProveedor().get().getId()));
        assertTrue(proveedor.getNombre().equals(Datos.crearProveedor().get().getNombre()));
        assertTrue(proveedor.getCantidad_productos().equals(Datos.crearProveedor().get().getCantidad_productos()));
        assertTrue(proveedor.getEnvios().equals(Datos.crearProveedor().get().getEnvios()));
    }

    /**
     * Métodos Producto
     */
    @Test
    void testFindAllProductos(){
        when(service.findAllProductos()).thenReturn(List.of(Datos.crearProducto1().get(), Datos.crearProducto2().get()));
        List<Producto> productos = service.findAllProductos();
        assertTrue(!productos.isEmpty());
        verify(productoRepository, atLeastOnce()).findAll();
    }

    @Test
    void testFindProductoById(){
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        Producto producto = service.findProductoById(1L);
        assertTrue(producto.getId().equals(Datos.crearProducto1().get().getId()));
        assertTrue(producto.getNombre().equals(Datos.crearProducto1().get().getNombre()));
        assertTrue(producto.getCantidad().equals(Datos.crearProducto1().get().getCantidad()));
    }

    @Test
    void testDeleteProductoById(){
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        boolean deleted = service.deleteProductoById(1L);
        assertTrue(deleted);
    }

    @Test
    void testDeleteProductoByIdIfNotFound(){
        boolean deleted = service.deleteProductoById(3L);
        assertFalse(deleted);
    }

    @Test
    void testSave(){
        Producto producto = new Producto(3L, "Oreja de res", 90);
        given(productoRepository.save(producto)).willReturn(producto);
        Producto productoGuardado = service.save(producto);
        assertTrue(productoGuardado.getId().equals(producto.getId()));
    }

    @Test
    void testEnvio(){
        Producto productoPedido = new Producto(1L, "Colita de cerdo", 2);
        given(proveedorRepository.findById(1L)).willReturn(Optional.of(Datos.crearProveedor().get()));
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        boolean resultadoEnvio = service.pedido(productoPedido);
        assertTrue(resultadoEnvio);
    }

    @Test
    void testEnvioError(){
        Producto productoPedido = new Producto(1L, "Colita de cerdo", 2000);
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        boolean resultadoEnvio = service.pedido(productoPedido);
        assertFalse(resultadoEnvio);
    }

    @Test
    void testEnvios(){
        Producto producto1 = new Producto(1L, "Colita de cerdo", 2);
        Producto producto2 = new Producto(2L, "Oreja de cerdo", 5);
        List<Producto> productosPedido = new ArrayList<>();
        productosPedido.add(producto1);
        productosPedido.add(producto2);
        given(proveedorRepository.findById(1L)).willReturn(Optional.of(Datos.crearProveedor().get()));
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        given(productoRepository.findById(2L)).willReturn(Optional.of(Datos.crearProducto2().get()));
        boolean resultadoEnvios = service.pedidos(productosPedido);
        assertTrue(resultadoEnvios);
    }

    @Test
    void testEnviosError(){
        Producto producto1 = new Producto(1L, "Colita de cerdo", 2);
        Producto producto2 = new Producto(2L, "Oreja de cerdo", 5000);
        List<Producto> productosPedido = new ArrayList<>();
        productosPedido.add(producto1);
        productosPedido.add(producto2);
        given(proveedorRepository.findById(1L)).willReturn(Optional.of(Datos.crearProveedor().get()));
        given(productoRepository.findById(1L)).willReturn(Optional.of(Datos.crearProducto1().get()));
        given(productoRepository.findById(2L)).willReturn(Optional.of(Datos.crearProducto2().get()));
        boolean resultadoEnvios = service.pedidos(productosPedido);
        assertFalse(resultadoEnvios);
    }
}
