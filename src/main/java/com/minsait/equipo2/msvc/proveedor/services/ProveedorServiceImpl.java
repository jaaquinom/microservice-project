package com.minsait.equipo2.msvc.proveedor.services;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import com.minsait.equipo2.msvc.proveedor.models.Proveedor;
import com.minsait.equipo2.msvc.proveedor.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.proveedor.repositories.ProveedorRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Métodos Proveedor
     */
    /*
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
    */

    @Override
    public Proveedor info(Long id) {
        int total_producto = productoRepository.findAll().size();
        proveedorRepository.findById(id).get().setCantidad_productos(total_producto);
        return proveedorRepository.findById(id).get();
    }

    /**
     * Métodos Producto
     */

    @Override
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findProductoById(Long id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public boolean deleteProductoById(Long id) {
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
    public boolean pedido(Producto productoPedido) {
        int productoCantidad = productoPedido.getCantidad();
        int existencia = productoRepository.findById(productoPedido.getId()).get().getCantidad();
        Producto producto = findProductoById(productoPedido.getId());

        if (existencia > productoCantidad){
            productoRepository.findById(productoPedido.getId()).get().setCantidad(existencia - productoCantidad);
            productoRepository.save(producto);
            return true;
        }
        else return false;
    }

    @Override
    public boolean pedidos(List<Producto> productosPedidos) {
        boolean response = false;
        for (Producto producto : productosPedidos){
            response = pedido(producto);
            if (!response){
                return false;
            }
        }
        return response;
    }
}