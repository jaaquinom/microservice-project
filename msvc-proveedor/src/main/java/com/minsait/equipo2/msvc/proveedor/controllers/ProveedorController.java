package com.minsait.equipo2.msvc.proveedor.controllers;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.services.ProveedorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService service;

    /**
     * Métodos Producto
     */

    @GetMapping("/listar_productos")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAllProductos(){
        return service.findAllProductos();
    }

    @GetMapping("/listar_productos/{id}")
    public ResponseEntity<?> findProductoById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            Producto producto = service.findProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            response.put("status", "OK");
            response.put("message","Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borrar_producto/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.findProductoById(id);
            service.deleteProductoById(id);
            response.put("status", "OK");
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            response.put("status", "OK");
            response.put("message", "Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar_producto")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto) {
        return service.save(producto);
    }

    @PostMapping("/envio")
    public ResponseEntity<?> pedidos(@RequestBody List<Producto> producto){
        Map<String, String> response = new HashMap<>();
        if (producto.isEmpty()){
            response.put("status", "OK");
            response.put("message", "Pedido Vacío");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            if (service.pedidos(producto))
                return ResponseEntity.ok(producto);
            else {
                response.put("status", "OK");
                response.put("message", "Producto Insuficiente");
                return ResponseEntity.ok(response);
            }
        }catch (NoSuchElementException e){
            response.put("status", "Not found");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Métodos Proveedor
     */

    @GetMapping("/informacion/{id}")
    public ResponseEntity<?> info(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Proveedor proveedor = service.info(id);
            return ResponseEntity.ok(proveedor);
        }catch (NoSuchElementException e){
            response.put("status", "OK");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}