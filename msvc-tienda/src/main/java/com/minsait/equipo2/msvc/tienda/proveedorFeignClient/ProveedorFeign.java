package com.minsait.equipo2.msvc.tienda.proveedorFeignClient;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "msvc-proveedor", url = "localhost:8020/proveedor")
public interface ProveedorFeign {
    @GetMapping("/listar_productos")
    List<ProductoProveedor> findAllProductos();

    @GetMapping("/listar_productos/{id}")
    ProductoProveedor findProductoById(@PathVariable Long id);

    @PostMapping("/envio")
    List<ProductoProveedor> pedidos(@RequestBody List<ProductoProveedor> producto);
}
