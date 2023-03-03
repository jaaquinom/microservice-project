package com.minsait.equipo2.msvc.tienda.proveedor;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Proveedor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "msvc-proveedor",url = "localhost:8080/proveedor")
public interface proveedor {
    @GetMapping("/listarproductos")
    List<Producto> findAllProductos();

    @GetMapping("/listarproductos/{id}")
    Proveedor findByID(@PathVariable Long id);

    @PostMapping("/pedido")
    List<Proveedor> pedidos(@RequestBody List<Proveedor> producto);

}
