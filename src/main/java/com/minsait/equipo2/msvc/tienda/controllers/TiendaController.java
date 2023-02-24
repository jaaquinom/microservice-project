package com.minsait.equipo2.msvc.tienda.controllers;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.services.TiendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long idProducto){
        try{
            Producto producto = tiendaService.findById(idProducto);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e){
            return  ResponseEntity.notFound().build();
        }

    }

}
