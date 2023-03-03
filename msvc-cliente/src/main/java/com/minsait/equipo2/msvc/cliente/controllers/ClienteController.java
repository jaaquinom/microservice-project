package com.minsait.equipo2.msvc.cliente.controllers;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import com.minsait.equipo2.msvc.cliente.models.Pedido;
import com.minsait.equipo2.msvc.cliente.services.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAllClientes() {
        return service.findAllClientes();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> findClienteById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = service.findClienteById(id);
            return ResponseEntity.ok(cliente);
        } catch (NoSuchElementException e) {
            response.put("status", "Not found");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteClienteById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            service.findClienteById(id);
            service.deleteClienteById(id);
            response.put("status", "OK");
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            response.put("status", "Not Found");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    public Cliente save(@RequestBody Cliente cliente){
        return service.save(cliente);
    }

    @PostMapping("/tienda/pedir")
    public ResponseEntity<?> pedido(@RequestBody List<Pedido> pedido){
        Map<String, String> response = new HashMap<>();
        List<Pedido> pedidoNuevo = service.pedido(pedido);
        response.put("status", "Pedido realizado");

        return new ResponseEntity<>(response,HttpStatus.OK);


    }
}