package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.ProductoProveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.proveedorFeignClient.ProveedorFeign;
import com.minsait.equipo2.msvc.tienda.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@Service
public class TiendaServiceImpl implements TiendaService{
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProveedorFeign proveedor;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<ProductoProveedor> findAllProveedor() {
        List<ProductoProveedor> productos = proveedor.findAllProductos();
        for (ProductoProveedor producto: productos) {
            producto.setId(proveedor.findProductoById(producto.getId()).getId());
            producto.setNombre(proveedor.findProductoById(producto.getId()).getNombre());
            producto.setCantidad(proveedor.findProductoById(producto.getId()).getCantidad());
        }
        return productos;
    }

    @Override
    public Producto findById(Long idProducto) {
        return productoRepository.findById(idProducto).get();
    }

    @Override
    public ProductoProveedor findByIdProveedor(Long idProducto) {
        ProductoProveedor producto= new ProductoProveedor();
        producto.setId(proveedor.findProductoById(idProducto).getId());
        producto.setNombre(proveedor.findProductoById(idProducto).getNombre());
        producto.setCantidad(proveedor.findProductoById(idProducto).getCantidad());
        return producto;
    }

    @Override
    public Tienda info(Long idTienda){
        int cantidadProducto = productoRepository.findAll().size();
        tiendaRepository.findById(idTienda).get().setCantidadProductos(cantidadProducto);
        return tiendaRepository.findById(idTienda).get();
    }

    @Override
    public Producto save(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public boolean deleteById(Long idProducto) {
        Optional<Producto> producto =productoRepository.findById(idProducto);
        if(producto.isPresent()){
            productoRepository.deleteById(idProducto);
            return true;
        }
        return false;
    }

    @Override
    public Integer envio(List<Producto> listProduct) {
        tiendaRepository.findById(1L).get().actualizarEnvio();
        for (Producto produc: listProduct) {
            int pedido = produc.getCantidadProducto();
            Optional<Producto> p = productoRepository.findById(produc.getId());

            int existencia = p.get().getCantidadProducto();
            if(existencia >= pedido){
                p.get().setCantidadProducto(existencia - pedido);
                Producto producto = findById(produc.getId());
                productoRepository.save(producto);
            }else {
                return 2;
            }
        }
        return 3;
    }

    public List<ProductoProveedor> pedido(List<ProductoProveedor> pedidos){
        List<ProductoProveedor> pedido = proveedor.pedidos(pedidos);
        return pedido;
    }
}