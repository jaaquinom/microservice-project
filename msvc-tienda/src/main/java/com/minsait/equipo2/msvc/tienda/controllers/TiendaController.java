package com.minsait.equipo2.msvc.tienda.controllers;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Proveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
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


    @GetMapping("/lista")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAll(){
        log.info("Todo bien");
        return tiendaService.findAll();
    }
    @GetMapping("/listaprovedor")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAllProveedor(){
        log.info("Todo bien");
        return tiendaService.findAllProveedor();
    }
    @GetMapping("/lista/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long idProducto){
        Map<String, String> response = new HashMap<>();
        try{
            Producto producto = tiendaService.findById(idProducto);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e){
            response.put("status","Not Found");
            response.put("message","Product not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/listaproveedor/{id}")
    public ResponseEntity<?> findByIdProveedor(@PathVariable("id") Long idProducto){
        Map<String, String> response = new HashMap<>();
        try{
            Producto producto = tiendaService.findByIdProveedor(idProducto);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e){
            response.put("status","Not Found");
            response.put("message","Product not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/envios/{id}")
    public ResponseEntity<?> envios (@PathVariable("id") Long idTienda){
        Map<String, String> response = new HashMap<>();
        try{
            Tienda tienda = tiendaService.info(idTienda);
            return ResponseEntity.ok(tienda);
        }catch (NoSuchElementException e){
            response.put("status","Not Found");
            response.put("message","Store not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto){
        return tiendaService.save(producto);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long idProducto){
        Map<String,Object> response = new HashMap<>();
        try{
            tiendaService.findById(idProducto);
            tiendaService.deleteById(idProducto);
            response.put("status", "Ok");
            response.put("mensaje", "Deleted");

        }catch (NoSuchElementException e){
            response.put("status", "Not Found");
            response.put("mensaje", "Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/envio")
    public ResponseEntity<?> envio(@RequestBody List<Producto> listaProducto){
        Map<String,Object> response = new HashMap<>();

        if (listaProducto.isEmpty()){
            response.put("status", "Not Found");
            response.put("message", "Pedido vacío");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            if (tiendaService.envioDiferente(listaProducto)==3)
                return ResponseEntity.ok(listaProducto);
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

    @PostMapping("/proveedor/pedir")
    public ResponseEntity<?> pedir(@RequestBody List<Proveedor> pedido){
        HashMap<String, Object> response = new HashMap<>();
        List<Proveedor> pedidos = tiendaService.pedido(pedido);
        response.put("status","Pedido realizado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
