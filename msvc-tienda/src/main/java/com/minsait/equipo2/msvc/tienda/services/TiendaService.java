package com.minsait.equipo2.msvc.tienda.services;



import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Proveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;

import java.util.List;

public interface TiendaService {
    List<Producto> findAll();
    List<Producto> findAllProveedor();
    Producto findById(Long idProducto);

    Producto findByIdProveedor(Long idProducto);
    Tienda info(Long idTienda);

    Producto save(Producto producto);

    boolean deleteById(Long idProducto);

    Integer envioDiferente(List<Producto> listProduct);

    List<Proveedor> pedido(List<Proveedor> pedidos);

}
