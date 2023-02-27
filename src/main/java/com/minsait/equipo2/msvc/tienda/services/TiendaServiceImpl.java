package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.models.Tienda;
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
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(Long idProducto) {
        return productoRepository.findById(idProducto).get();
    }

    @Override
    public Tienda info(Long idTienda){
        int cantidadProducto = productoRepository.findAll().size();
        tiendaRepository.findById(idTienda).get().setCantidadProductos(cantidadProducto);
        return tiendaRepository.findById(idTienda).get();
    }

    @Override
    public Integer totalEnvios(Long idTienda) {
        return tiendaRepository.findById(idTienda).get().getEnvios();
    }

    @Override
    public Integer totalProductos(Long idTienda) {
        return productoRepository.findById(idTienda).get().getCantidadProducto();
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
}
