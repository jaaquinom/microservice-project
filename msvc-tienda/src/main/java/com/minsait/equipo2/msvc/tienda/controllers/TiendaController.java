package com.minsait.equipo2.msvc.tienda.controllers;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.services.TiendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequestMapping("/tienda")
@Slf4j
@RestController
public class TiendaController {
    @Autowired
    private TiendaService tiendaService;

    @GetMapping("/listar_productos")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAll(){
        return tiendaService.findAll();
    }

    @GetMapping("/productos_en_provedor")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoProveedor> findAllProveedor(){
        return tiendaService.findAllProveedor();
    }

    @GetMapping("/producto_en_tienda/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long idProducto){
        Map<String, String> response = new HashMap<>();
        try{
            Producto producto = tiendaService.findById(idProducto);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e){
            response.put("status","Not found");
            response.put("message","Not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/producto_en_proveedor/{id}")
    public ResponseEntity<?> findByIdProveedor(@PathVariable("id") Long idProducto){
        Map<String, String> response = new HashMap<>();
        try{
            ProductoProveedor producto = tiendaService.findByIdProveedor(idProducto);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e){
            response.put("status","Not found");
            response.put("message","Not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/informacion/{id}")
    public ResponseEntity<?> envios (@PathVariable("id") Long idTienda){
        Map<String, String> response = new HashMap<>();
        try{
            Tienda tienda = tiendaService.info(idTienda);
            return ResponseEntity.ok(tienda);
        }catch (NoSuchElementException e){
            response.put("status","Not found");
            response.put("message","Not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/guardar_producto")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto){
        return tiendaService.save(producto);
    }

    @DeleteMapping("/borrar_producto/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long idProducto){
        Map<String,Object> response = new HashMap<>();
        try{
            tiendaService.findById(idProducto);
            tiendaService.deleteById(idProducto);
            response.put("status", "OK");
            response.put("mensaje", "Deleted");

        }catch (NoSuchElementException e){
            response.put("status", "Not found");
            response.put("mensaje", "Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/envio_a_cliente")
    public ResponseEntity<?> envio(@RequestBody List<Producto> listaProducto){
        Map<String,Object> response = new HashMap<>();

        if (listaProducto.isEmpty()){
            response.put("status", "OK");
            response.put("message", "Pedido Vacío");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            if (tiendaService.envio(listaProducto)==3)
                return ResponseEntity.ok(listaProducto);
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

    @PostMapping("/proveedor/pedir")
    public ResponseEntity<?> pedir(@RequestBody List<ProductoProveedor> pedido){
        HashMap<String, Object> response = new HashMap<>();
        List<ProductoProveedor> pedidos = tiendaService.pedido(pedido);
        response.put("status","OK");
        response.put("message", "Pedido Realizado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
