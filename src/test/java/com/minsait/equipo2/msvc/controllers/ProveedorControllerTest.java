package com.minsait.equipo2.msvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.equipo2.msvc.proveedor.controllers.ProveedorController;
import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.services.ProveedorService;
import com.minsait.equipo2.msvc.tienda.exceptions.ProductoInsuficienteException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProveedorService service;
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAllProductos() throws Exception{
        when(service.findAllProductos()).thenReturn(List.of(Datos.crearProducto1().get(), Datos.crearProducto2().get()));
        mvc.perform(get("/proveedor/listarproductos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testFindProductoById() throws Exception{
        when(service.findProductoById(1L)).thenReturn(Datos.crearProducto1().get());
        mvc.perform(get("/proveedor/listarproductos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre", Matchers.is("Colita de cerdo")))
                .andExpect(jsonPath("$.cantidad").value(50));
    }

    @Test
    void testFindProductoByIdIfNotFound() throws Exception{
        when(service.findProductoById(1L)).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/proveedor/listarproductos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProductoById() throws Exception{
        Long deleteProductoId = 1L;
        when(service.deleteProductoById(deleteProductoId)).thenReturn(true);
        mvc.perform(delete("/proveedor/borrarproducto/{id}", deleteProductoId))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProductoByIdIfNotFound() throws Exception{
        Long deleteProductoId = 1L;
        when(service.deleteProductoById(deleteProductoId)).thenThrow(NoSuchElementException.class);
        mvc.perform(delete("/proveedor/borrarproducto/{id}", deleteProductoId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveProducto() throws Exception{
        Producto producto = new Producto(null, "Patitas de pollo", 35);
        when(service.save(any(Producto.class))).then(invocationOnMock -> {
            Producto productoTemporal = invocationOnMock.getArgument(0);
            productoTemporal.setId(3L);
            return productoTemporal;
        });
        mvc.perform(post("/proveedor/guardarproducto").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(producto)))
                        .andExpectAll(
                            jsonPath("$.id").value(3),
                            jsonPath("$.nombre", Matchers.is("Patitas de pollo")),
                            jsonPath("$.cantidad").value(35),
                            status().isCreated()
                        );
    }

    @Test
    void testPedido() throws Exception{
        Producto producto1 = new Producto(1L, "Colita de cerdo", 2);
        Producto producto2 = new Producto(2L, "Oreja de cerdo", 5);
        List<Producto> productosPedido = new ArrayList<>();
        productosPedido.add(producto1);
        productosPedido.add(producto2);
        when(service.pedidos(productosPedido)).thenReturn(true);
        mvc.perform(post("/proveedor/pedido").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(productosPedido)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void testPedidoIfNotFound() throws Exception{
        Producto producto1 = new Producto(1L, "Colita de cerdo", 20000);
        Producto producto2 = new Producto(2L, "Oreja de cerdo", 50000);
        List<Producto> productosPedido = new ArrayList<>();
        productosPedido.add(producto1);
        productosPedido.add(producto2);
        when(service.pedidos(productosPedido)).thenThrow(NoSuchElementException.class);
        mvc.perform(post("/proveedor/pedido").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productosPedido)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void testPedidoVacio() throws Exception{
        List<Producto> productosPedido = new ArrayList<>();
        when(service.pedidos(productosPedido)).thenReturn(false);
        mvc.perform(post("/proveedor/pedido").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productosPedido)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    void testInfo() throws Exception{
        Long infoId = 1L;
        when(service.info(infoId)).thenReturn(Datos.crearProveedor().get());
        mvc.perform(get("/proveedor/info/{id}", infoId).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.nombre", Matchers.is("Insumos y materia prima")),
                        jsonPath("$.cantidad_productos").value(0),
                        jsonPath("$.pedidos").value(0)
                );
    }

    @Test
    void testInfoIfNotFound() throws Exception{
        Long infoId = 10L;
        when(service.info(infoId)).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/proveedor/info/{id}", infoId).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound()
                );
    }
}