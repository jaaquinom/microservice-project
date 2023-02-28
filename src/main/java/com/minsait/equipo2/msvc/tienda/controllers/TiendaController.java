package com.minsait.equipo2.msvc.tienda.controllers;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import com.minsait.equipo2.msvc.tienda.services.TiendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("tienda")
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

    }@GetMapping("/envios/{id}")
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
        //tiendaService.envioDiferente(listaProducto);
        Map<String,Object> response = new HashMap<>();
        if(tiendaService.envioDiferente(listaProducto)){
            response.put("status", "OK");
            response.put("mensaje", "Producto descontado");
            return new ResponseEntity<>(response, HttpStatus.OK);//git
        }
        response.put("status", "OK");
        response.put("mensaje", "Producto insuficiente");
        return new ResponseEntity<>(response, HttpStatus.OK);//git
    }


}
