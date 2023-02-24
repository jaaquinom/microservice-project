package com.minsait.equipo2.msvc.proveedor.controllers;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.services.ProveedorService;
import java.util.List;
import java.util.NoSuchElementException;
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

@RestController
@RequestMapping("api/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService service;

    @GetMapping("/listar/{id}")
    public ResponseEntity<Proveedor> findById(@PathVariable Long id) {
        try {
            Proveedor proveedor = this.service.findById(id);
            return ResponseEntity.ok(proveedor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return service.guardar(proveedor);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deletedById(@PathVariable Long id) {
        return this.service.deletedById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/inventario/{id}")
    public ResponseEntity<List<Producto>> revisarInventario(@PathVariable Long id) {
        try {
            List<Producto> producto = this.service.revisarInventario(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/vender")
    public String vender(@RequestBody Long idProveedor, @RequestBody Producto producto, @RequestBody Integer cantidad) {
        return service.vender(idProveedor, producto, cantidad) ? "Venta realizada con éxito" : "No se pudo realizar la venta";
    }
}
