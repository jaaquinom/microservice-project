package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import java.util.List;

public interface ProveedorService {
    Proveedor findById(Long idProveedor);

    List<Producto> revisarInventario(Long idProveedor);

    boolean vender(Long idProveedor, Producto producto, Integer cantidad);

    boolean deletedById(Long idProveedor);

    Proveedor guardar(Proveedor proveedor);
}
