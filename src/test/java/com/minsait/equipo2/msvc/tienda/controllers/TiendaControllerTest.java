package com.minsait.equipo2.msvc.tienda.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.equipo2.msvc.tienda.models.Producto;
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

        mvc.perform(MockMvcRequestBuilders.get("/tienda/lista").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombreProducto").value("Colita de cerdo"))
                .andExpect(jsonPath("$[1].nombreProducto").value("Codorniz Deshidratada"));

    }
    @Test
    void testFindById() throws Exception {
        when(tiendaService.findById(anyLong())).thenReturn(Datos.crearProducto1().get());

        mvc.perform(MockMvcRequestBuilders.get("/tienda/lista/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreProducto").value("Colita de cerdo"));
                //.andExpect(jsonPath("$[0].nombreProducto").value("Codorniz Deshidratada"));

    }
    @Test
    void testFindByIdIfDoesntExist() throws Exception{
        when(tiendaService.findById(1L)).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/tienda/lista/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardar() throws Exception{
        //Given
        Producto producto = new Producto(null, "Colita de cerdo", 10,10);
        when(tiendaService.save(any(Producto.class))).then(invocationOnMock -> {
            Producto productoTemporal = invocationOnMock.getArgument(0);
            productoTemporal.setId(3L);
            return productoTemporal;
        });

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/guardar").contentType(MediaType.APPLICATION_JSON)
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
        mvc.perform(delete("/tienda/borrar/1"))

                //Then
                .andExpect(status().isOk());
    }
    @Test
    void testBorrarSiNoExiste() throws Exception{
        //Given
        when(tiendaService.deleteById(1L)).thenThrow(NoSuchElementException.class);

        //When
        mvc.perform(delete("/tienda/borrar/1"))

                //Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInformacion() throws Exception {
        Tienda tienda = Datos.crearTienda().get();
        when(tiendaService.info(1L)).thenReturn(tienda);

        mvc.perform(MockMvcRequestBuilders.get("/tienda/envios/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$[0].nombreTienda").value("Fernanda Garcia"));
    }
    @Test
    public void testInformacionException() throws Exception {
        Tienda tienda = Datos.crearTienda().get();
        when(tiendaService.info(1L)).thenThrow(NoSuchElementException.class);

        mvc.perform(MockMvcRequestBuilders.get("/tienda/envios/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                //.andExpect(jsonPath("$[0].nombreTienda").value("Fernanda Garcia"));
    }

    @Test
    void testEnvio() throws Exception{
        //Given
        List<Producto> listaProducto = new ArrayList<>();
        Producto producto = new Producto(1L,"Colita de cerdo",10,20);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenReturn(3);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio").contentType(MediaType.APPLICATION_JSON)
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
        Producto producto = new Producto(1L,"Colita de cerdo",10,20);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenReturn(2);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio").contentType(MediaType.APPLICATION_JSON)
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
        Producto producto = new Producto(1L,"Colita de cerdo",10,20);
        listaProducto.add(producto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        when(tiendaService.envioDiferente(listaProducto)).thenReturn(1);

        //When
        mvc.perform(MockMvcRequestBuilders.post("/tienda/envio").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listaProducto)))


                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(response))
                );
    }


}