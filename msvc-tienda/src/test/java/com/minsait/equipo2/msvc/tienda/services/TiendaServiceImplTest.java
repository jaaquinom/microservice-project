package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.controllers.Datos;
import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.proveedorFeignClient.ProveedorFeign;
import com.minsait.equipo2.msvc.tienda.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TiendaServiceImplTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private TiendaRepository tiendaRepository;
    @Mock
    private ProveedorFeign proveedor;
    @InjectMocks
    private TiendaServiceImpl tiendaService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAll() {
        List<Producto> productos = List.of(Datos.crearProducto1().get(),Datos.crearProducto2().get());
        when(productoRepository.findAll()).thenReturn(productos);
        List<Producto> listaProducto = tiendaService.findAll();
        assertEquals(listaProducto,productos);
    }

    /*@Test
    void testFindAllProveedor(){
        ProductoProveedor producto = new ProductoProveedor();
        producto.setId(Datos.crearProducto1().get().getId());
        producto.setNombre(Datos.crearProducto1().get().getNombreProducto());
        producto.setCantidad(Datos.crearProducto1().get().getCantidadProducto());
        when(proveedor.findAllProductos()).thenReturn(List.of(producto));
        List<ProductoProveedor> productos = proveedor.findAllProductos();
        for (ProductoProveedor pro: productos){
            pro.setId(Datos.crearProducto1().get().getId());
            pro.setNombre(Datos.crearProducto1().get().getNombreProducto());
            pro.setCantidad(Datos.crearProducto1().get().getCantidadProducto());
        }
        assertFalse(productos.isEmpty());
        verify(proveedor, atLeastOnce()).findAllProductos();
    }*/
/*
    @Test
    void findAllProductosProveedor(){
         List<ProductoProveedor> productosPedido = new ArrayList<>();
         ProductoProveedor producto1 = new ProductoProveedor();
         producto1.setId(1L);
         producto1.setNombre("Colita de cerdo");
         producto1.setCantidad(2);
         Optional<List<ProductoProveedor>> optional = Optional.of(productosPedido);
         when(tiendaService.findAllProveedor()).thenReturn(optional.get());
         given(tiendaService.findAllProveedor()).willReturn(optional.get());
         given(proveedor.findAllProductos()).willReturn(optional.get());
         List<ProductoProveedor> resultadoEnvios = proveedor.findAllProductos();
         assertNotNull(resultadoEnvios);
    }*/

    @Test
    void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(Datos.crearProducto1().get()));
        Producto producto = tiendaService.findById(1L);
        assertEquals(producto,Datos.crearProducto1().get());
    }

    @Test
    void testFindByIdProveedor(){
        ProductoProveedor producto1 = new ProductoProveedor();
        producto1.setId(1L);
        producto1.setNombre("Colita de cerdo");
        producto1.setCantidad(2);
        Optional<ProductoProveedor> optional = Optional.of(producto1);
        when(proveedor.findProductoById(1L)).thenReturn(optional.get());
        ProductoProveedor producto = tiendaService.findByIdProveedor(1L);
        assertEquals(producto.getId(), producto1.getId());
        assertEquals(producto.getCantidad(), producto1.getCantidad());
        assertEquals(producto.getNombre(), producto1.getNombre());
    }

    @Test
    void testInfo() {
        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(Datos.crearTienda().get()));
        Tienda tienda = Datos.crearTienda().get();
        tiendaRepository.save(tienda);
        Optional<Tienda> tiendaReal = Optional.ofNullable(tiendaService.info(1L));
        tienda.setCantidadProductos(tienda.getCantidadProductos());
        assertEquals(tienda, tiendaReal.get());
    }

    @Test
    void testSave() {
        Producto producto = Datos.crearProducto1().get();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto productoGuardado = tiendaService.save(producto);
        assertEquals(producto,productoGuardado);
    }

    @Test
    void testDeleteById() {
        Producto producto = Datos.crearProducto1().get();
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        boolean productoEliminado = tiendaService.deleteById(1L);
        assertEquals(productoEliminado,true);
    }

    @Test
    void testDeleteByIdNotFound(){
        boolean productoEliminado = tiendaService.deleteById(5L);
        assertFalse(productoEliminado);
    }
    /*
    @Test
    void testEnvio() {
        Producto producto1 = Datos.crearProducto1().get();
        Producto producto2 = Datos.crearProducto2().get();
        List<Producto> listaProducto = new ArrayList<>();
        listaProducto.add(producto1);
        listaProducto.add(producto2);
        when(tiendaService.envio(listaProducto)).thenReturn(1);
        Integer envioCorrecto = tiendaService.envio(listaProducto);
        assertEquals(1, envioCorrecto);
    }*/
}