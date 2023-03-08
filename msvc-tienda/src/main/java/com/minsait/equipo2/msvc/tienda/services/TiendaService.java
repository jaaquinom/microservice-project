package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import java.util.List;

public interface TiendaService {
    List<Producto> findAll();
    List<ProductoProveedor> findAllProveedor();
    Producto findById(Long idProducto);

    ProductoProveedor findByIdProveedor(Long idProducto);

    Tienda info(Long idTienda);

    Producto save(Producto producto);

    boolean deleteById(Long idProducto);

    Integer envio(List<Producto> listProduct);

    List<ProductoProveedor> pedido(List<ProductoProveedor> pedidos);
}