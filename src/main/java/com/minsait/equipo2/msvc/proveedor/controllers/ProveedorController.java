package com.minsait.equipo2.msvc.proveedor.controllers;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.services.ProveedorService;
import java.util.List;
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
     * Métodos Proveedor
     */
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

    /**
     * Métodos Producto
     */

    @GetMapping("/listarproductos")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAllProductos(){
        return service.findAllProductos();
    }

    @GetMapping("/listarproductos/{id}")
    public ResponseEntity<Producto> findProductoById(@PathVariable Long id) {
        try {
            Producto producto = service.findProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrarproducto/{id}")
    public ResponseEntity<?> deletedProductoById(@PathVariable Long id) {
        return service.deletedProductoById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/guardarproducto")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto) {
        return service.save(producto);
    }
}