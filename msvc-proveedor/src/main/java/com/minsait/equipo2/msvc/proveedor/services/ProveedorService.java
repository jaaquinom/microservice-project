package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import java.util.List;

public interface ProveedorService {
    /**
     * Métodos Proveedor
     */

    Proveedor info(Long id);

    /**
     * Métodos Producto
     */
    List<Producto> findAllProductos();
    Producto findProductoById(Long id);
    boolean deleteProductoById(Long id);
    Producto save(Producto producto);
    boolean pedido(Producto productoPedido);
    boolean pedidos(List<Producto> productosPedidos);
}
