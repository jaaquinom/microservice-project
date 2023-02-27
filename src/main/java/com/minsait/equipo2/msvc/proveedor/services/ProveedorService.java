package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import java.util.List;

public interface ProveedorService {
    /**
     * Métodos Proveedor
     */
    List<Proveedor> findAll();
    Proveedor findById(Long id);
    boolean deletedById(Long id);
    Proveedor save(Proveedor proveedor);
    Integer revisarInventario(Long id);
    boolean pedido(Long id, Integer cantidad);
    Integer totalPedidos(Long id);
    Proveedor info(Long id);

    /**
     * Métodos Producto
     */
    List<Producto> findAllProductos();
    Producto findProductoById(Long id);
    boolean deletedProductoById(Long id);
    Producto save(Producto producto);
    Integer totalProductos(Long id);
}
