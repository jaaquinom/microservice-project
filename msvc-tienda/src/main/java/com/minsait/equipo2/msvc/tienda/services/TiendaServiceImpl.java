package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Proveedor;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
import com.minsait.equipo2.msvc.tienda.proveedor.proveedor;
import com.minsait.equipo2.msvc.tienda.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TiendaServiceImpl implements TiendaService{

    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private proveedor proveedor;
    @Override
    public List<Producto> findAll() {

        return productoRepository.findAll();
    }
    @Override
    public List<Producto> findAllProveedor() {
        List<Producto> productos = proveedor.findAllProductos();

        for (Producto producto: productos) {
            producto.setId(proveedor.findByID(producto.getId()).getId());
            producto.setNombreProducto(proveedor.findByID(producto.getId()).getNombre());
            producto.setCantidadProducto(proveedor.findByID(producto.getId()).getCantidad());
        }
        return productos;
    }

    @Override
    public Producto findById(Long idProducto) {
        return productoRepository.findById(idProducto).get();
    }
    @Override
    public Producto findByIdProveedor(Long idProducto) {

        Producto producto= new Producto();
        producto.setId(proveedor.findByID(idProducto).getId());
        producto.setNombreProducto(proveedor.findByID(idProducto).getNombre());
        producto.setCantidadProducto(proveedor.findByID(idProducto).getCantidad());
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
    public Integer envioDiferente(List<Producto> listProduct) {
        Producto producto= new Producto();

        tiendaRepository.findById(1L).get().envio();
        for (Producto produc: listProduct) {
            int pedido = produc.getCantidadProducto();
            Optional<Producto> p = productoRepository.findById(produc.getId());


            int existencia = p.get().getCantidadProducto();
            if(existencia >= pedido){
                p.get().setCantidadProducto(existencia - pedido);

                producto = findById(produc.getId());

                productoRepository.save(producto);


            }else {
                return 2;
            }
        }

        return 3;
    }
    public List<Proveedor> pedido(List<Proveedor> pedidos){
        List<Proveedor> pedido = proveedor.pedidos(pedidos);
        return pedido;
    }

}
