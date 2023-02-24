package com.minsait.equipo2.msvc.tienda.services;



import com.minsait.equipo2.msvc.tienda.models.Producto;

import java.util.List;

public interface TiendaService {
    List<Producto> findAll();
    Producto findById(Long idProducto);
    Integer totalEnvios(Long idTienda);
    Integer totalProductos(Long idProducto);
    Producto save(Producto producto);

    boolean deleteById(Long idProducto);

}
