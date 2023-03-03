package com.minsait.equipo2.msvc.cliente.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.cliente.services.ClienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.NoSuchElementException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ClienteService service;
    ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAllClientes() throws Exception{
        when(service.findAllClientes()).thenReturn(List.of(Datos.crearCliente1().get(), Datos.crearCliente2().get()));
        mvc.perform(get("/cliente/listar").contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$[0].id").value(1),
                        jsonPath("$[1].id").value(2)
                );
    }

    @Test
    void testFindClienteById() throws Exception{
        when(service.findClienteById(1L)).thenReturn(Datos.crearCliente1().get());
        mvc.perform(get("/cliente/listar/1").contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.id").value(1),
                jsonPath("$.nombre", Matchers.is("Yamani")),
                jsonPath("$.direccion", Matchers.is("Estado de México"))
        );
    }

    @Test
    void testFindClienteByIdNotFound() throws Exception{
        when(service.findClienteById(1L)).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/cliente/listar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteClienteById() throws Exception{
        when(service.deleteClienteById(1L)).thenReturn(true);
        mvc.perform(delete("/cliente/borrar/1")).andExpect(status().isOk());
    }

    @Test
    void testDeleteClienteByIdNotFound() throws Exception{
        when(service.deleteClienteById(1L)).thenThrow(NoSuchElementException.class);
        mvc.perform(delete("/cliente/borrar/1")).andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception{
        Cliente cliente = new Cliente(null, "Richie", "CDMX", 0);
        when(service.save(any(Cliente.class))).then(invocationOnMock -> {
            Cliente clienteTemporal = invocationOnMock.getArgument(0);
            clienteTemporal.setId(3L);
            return clienteTemporal;
        });
        mvc.perform(post("/cliente/guardar").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cliente)))
                .andExpectAll(
                        jsonPath("$.id").value(3),
                        jsonPath("$.nombre", Matchers.is("Richie")),
                        jsonPath("$.direccion", Matchers.is("CDMX")),
                        jsonPath("$.cantidad_pedidos").value(0)
                );
    }
}