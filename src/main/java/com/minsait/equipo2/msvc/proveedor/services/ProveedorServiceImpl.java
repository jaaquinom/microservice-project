package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.repositories.ProveedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public Proveedor findById(Long idProveedor) {
        return proveedorRepository.findById(idProveedor).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Producto> revisarInventario(Long idProveedor) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor).orElseThrow();
        return proveedor.getProductos();
    }

    public boolean vender(Long idProveedor, Producto producto, Integer cantidad) {
        if (proveedorRepository.findById(idProveedor).get().getProductos().isEmpty()) {
            return false;
        } else {
            return proveedorRepository.findById(idProveedor).get().getProductos().size() >= cantidad;
        }
    }

    @Transactional
    public boolean deletedById(Long idProveedor) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(idProveedor);
        if (proveedor.isPresent()) {
            proveedorRepository.deleteById(idProveedor);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}
