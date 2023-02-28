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

    /*
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Proveedor> findAll(){
        return service.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Proveedor> findById(@PathVariable Long id) {
        try {
            Proveedor proveedor = service.findById(id);
            return ResponseEntity.ok(proveedor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deletedById(@PathVariable Long id) {
        return service.deletedById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor save(@RequestBody Proveedor proveedor) {
        return service.save(proveedor);
    }

   @GetMapping("/inventario/{id}")
    public Integer revisarInventario(@PathVariable Long id) {
        try {
            service.findById(id);
            return service.revisarInventario(id);
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @PostMapping("/pedido/{id}/{cantidad}")
    public String pedido(@PathVariable Long id, @PathVariable Integer cantidad) {
        if (service.pedido(id, cantidad)){
            return "Venta realizada con éxito";
        }else return "No se pudo realizar la venta";
    }
     */

    /**
     * Métodos Producto
     */

    @GetMapping("/listarproductos")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAllProductos(){
        return service.findAllProductos();
    }

    @GetMapping("/listarproductos/{id}")
    public ResponseEntity<?> findProductoById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            Producto producto = service.findProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            response.put("status", "Not Found");
            response.put("message","Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borrarproducto/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.findProductoById(id);
            service.deleteProductoById(id);
            response.put("status", "OK");
            response.put("message", "Deleted");
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            response.put("status", "Not Found");
            response.put("message", "Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardarproducto")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto) {
        return service.save(producto);
    }
    /*
    @PostMapping("/pedido")
    public ResponseEntity<?> pedido(@RequestBody Producto producto){
        service.pedido(producto);
        return ResponseEntity.ok(producto);
    }
    */
    @PostMapping("/pedido")
    public ResponseEntity<?> pedidos(@RequestBody List<Producto> producto){
        Map<String, String> response = new HashMap<>();
        if (producto.isEmpty()){
            response.put("status", "Not Found");
            response.put("message", "Pedido vacío");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
            response.put("status", "Not Found");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Métodos Proveedor
     */

    @GetMapping("/info/{id}")
    public ResponseEntity<?> info(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Proveedor proveedor = service.info(id);
            return ResponseEntity.ok(proveedor);
        }catch (NoSuchElementException e){
            response.put("status", "Not Found");
            response.put("message", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}