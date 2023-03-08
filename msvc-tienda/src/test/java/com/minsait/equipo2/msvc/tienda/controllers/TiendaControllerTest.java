package com.minsait.equipo2.msvc.tienda.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.services.TiendaService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TiendaController.class)
class TiendaControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TiendaService tiendaService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper =new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        when(tiendaService.findAll()).thenReturn(List.of(Datos.crearProducto1().get(),Datos.crearProducto2().get()));

        mvc.perform(MockMvcRequestBuilders.get("/tienda/listar_productos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombreProducto").value("Colita de cerdo"))
                .andExpect(jsonPath("$[1].nombreProducto").value("Codorniz Deshidratada"));

    }
    /*
    @Test
    void testFindAllProveedor() throws Exception {
        when(tiendaService.findAllProveedor()).thenReturn(List.of(Datos.crearProducto1().get(),Datos.crearProducto2().get()));

        mvc.perform(MockMvcRequestBuilders.get("/tienda/productos_en_provedor").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombreProducto").value("Colita de cerdo"))
                .andExpect(jsonPath("$[1].nombreProducto").value("Codorniz Deshidratada"));

    }*/
    @Test
    void testFindById() throws Exception {
        when(tiendaService.findById(anyLong())).thenReturn(Datos.crearProducto1().get());

        mvc.perform(MockMvcRequestBuilders.get("/tienda/producto_en_tienda/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreProducto").value("Colita de cerdo"));
                //.andExpect(jsonPath("$[0].nombreProducto").value("Codorniz Deshidratada"));
    }
    /*
    @Test
    void testFindByIdProveedor() throws Exception {
        when(tiendaService.findByIdProveedor(anyLong())).thenReturn(Datos.crearProducto1().get());

        mvc.perform(MockMvcRequestBuilders.get("/tienda/producto_en_proveedor/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreProducto").value("Colita de cerdo"));
                //.andExpect(jsonPath("$[0].nombreProducto").value("Codorniz Deshidratada"));
    }*/
    /*
    @Test
    void testFindByIdIfDoesntExist() throws Exception{
        when(tiendaService.findById(1L)).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/tienda/producto_en_tienda/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByIdIfDoesntExistProveedor() throws Exception{
        when(tiendaService.findByIdProveedor(1L)).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/tienda/producto_en_proveedor/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGuardar() throws Exception{
        //Given
        Producto producto = new Producto(null, "Colita de cerdo", 10);
        when(tiendaService.save(any(Producto.class))).then(invocationOnMock -> {
            Producto productoTemporal = invocationOnMock.getArgument(0);
            productoTemporal.setId(3L);
            return productoTemporal;
        });

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/guardar_producto").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(producto)))

                //Then
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(3)),
                        jsonPath("$.nombreProducto", Matchers.is("Colita de cerdo")),
                        jsonPath("$.cantidadProducto", Matchers.is(10)),
                        status().isCreated()
                );
    }
    @Test
    void testBorrar() throws Exception{
        //Given
        when(tiendaService.deleteById(1L)).thenReturn(true);

        //When
        mvc.perform(delete("/tienda/borrar_producto/1"))

                //Then
                .andExpect(status().isOk());
    }
    @Test
    void testBorrarSiNoExiste() throws Exception{
        //Given
        when(tiendaService.deleteById(1L)).thenThrow(NoSuchElementException.class);

        //When
        mvc.perform(delete("/tienda/borrar_producto/1"))

                //Then
                .andExpect(status().isOk());
    }

    @Test
    public void testInformacion() throws Exception {
        Tienda tienda = Datos.crearTienda().get();
        when(tiendaService.info(1L)).thenReturn(tienda);

        mvc.perform(MockMvcRequestBuilders.get("/tienda/informacion/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testInformacionException() throws Exception {
        Tienda tienda = Datos.crearTienda().get();
        when(tiendaService.info(1L)).thenThrow(NoSuchElementException.class);

        mvc.perform(MockMvcRequestBuilders.get("/tienda/informacion/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testEnvio() throws Exception{
        //Given
        List<Producto> listaProducto = new ArrayList<>();
        Producto producto = new Producto(1L,"Colita de cerdo",10);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenReturn(3);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio_a_cliente").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listaProducto)))

                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(response))
                );
    }

    @Test
    void testEnvioProductoInsuficiente() throws Exception{
        //Given
        List<Producto> listaProducto = new ArrayList<>();
        Producto producto = new Producto(1L,"Colita de cerdo",10);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenReturn(2);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio_a_cliente").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listaProducto)))

                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(response))
                );
    }

    @Test
    void testEnvioProductoNoEncontrado() throws Exception{
        //Given
        List<Producto> listaProducto = new ArrayList<>();
        Producto producto = new Producto(1L,"Colita de cerdo",10);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenThrow(NoSuchElementException.class);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio_a_cliente").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listaProducto)))

                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(response))
                );
    }
    @Test
    void testEnvioVacio() throws Exception{
        List<Producto> listaProducto = new ArrayList<>();
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio_a_cliente").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listaProducto)))
                //Then
                .andExpectAll(
                        status().isOk()
                );


    }
    @Test
    void testPedir() throws Exception{
        List<ProductoProveedor> pedido = new ArrayList<>();
        when( tiendaService.pedido(pedido)).thenReturn(pedido);
        mvc.perform(MockMvcRequestBuilders.post("/tienda/proveedor/pedir").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pedido)))
                //Then
                .andExpectAll(
                        status().isOk()
                );
    }*/
}