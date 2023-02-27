package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.repositories.ProductoRepository;
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
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElseThrow();
    }

    @Transactional
    public boolean deletedById(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        if (proveedor.isPresent()) {
            proveedorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }


    @Transactional(readOnly = true)
    public Integer revisarInventario(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        return proveedor.getCantidad_productos();
    }

    public boolean pedido(Long id, Integer cantidad) {
        if (proveedorRepository.findById(id).get().getCantidad_productos().equals(0)) {
            return false;
        } else if (proveedorRepository.findById(id).get().getCantidad_productos() < cantidad) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer totalPedidos(Long id) {
        return proveedorRepository.findById(id).get().getPedidos();
    }

    @Override
    public Proveedor info(Long id) {
        int total_producto = productoRepository.findAll().size();
        proveedorRepository.findById(id).get().setCantidad_productos(total_producto);
        return proveedorRepository.findById(id).get();
    }

    @Override
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findProductoById(Long id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public boolean deletedProductoById(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            productoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Integer totalProductos(Long id) {
        return productoRepository.findById(id).get().getCantidad();
    }
}