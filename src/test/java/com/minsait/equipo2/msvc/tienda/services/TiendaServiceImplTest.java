package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.controllers.Datos;
import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TiendaServiceImplTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaServiceImpl tiendaService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Producto> productos = List.of(Datos.crearProducto1().get(),Datos.crearProducto2().get());

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> listaProducto = tiendaService.findAll();

        assertEquals(listaProducto,productos);
    }

    @Test
    void findById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(Datos.crearProducto1().get()));

        Producto producto = tiendaService.findById(1L);

        assertEquals(producto,Datos.crearProducto1().get());
    }

    @Test
    void info() {
        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(Datos.crearTienda().get()));

        Tienda tienda = Datos.crearTienda().get();
        tiendaRepository.save(tienda);
        Optional<Tienda> tiendaReal = Optional.ofNullable(tiendaService.info(1L));


        //when(productoRepository.findAll().size()).thenReturn(0);

        tienda.setCantidadProductos(tienda.getCantidadProductos());

        Tienda tienda2 = tiendaRepository.findById(1L).get();

        System.out.println("tienda2 = " + tienda2);
        System.out.println("tiendaReal = " + tiendaReal);
        System.out.println("tienda = " + tienda);

        assertEquals(tienda, tienda2);
    }


    @Test
    void save() {
        Producto producto = Datos.crearProducto1().get();

        when(productoRepository.save(producto)).thenReturn(producto);

        Producto productoGuardado = tiendaService.save(producto);

        assertEquals(producto,productoGuardado);
    }

    @Test
    void deleteById() {
        Producto producto = Datos.crearProducto1().get();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

       // when(tiendaService.deleteById(1L)).thenReturn(true);

        boolean productoEliminado = tiendaService.deleteById(1L);

        assertEquals(productoEliminado,true);
    }
    @Test
    void deleteByIdNotFound() {


        boolean productoEliminado = tiendaService.deleteById(3L);

        assertFalse(productoEliminado);
    }
    @Test
    void envioDiferente() {
        List<Producto> listaProducto = List.of(Datos.crearProducto1().get());
        Integer envioCorrecto = tiendaService.envioDiferente(listaProducto);
         assertEquals(1,envioCorrecto);

        Integer envioVacio = tiendaService.envioDiferente(List.of());
        assertEquals(3,envioVacio);





    }
}