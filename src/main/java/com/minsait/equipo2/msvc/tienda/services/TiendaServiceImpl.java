package com.minsait.equipo2.msvc.tienda.services;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import com.minsait.equipo2.msvc.tienda.repositories.ProductoRepository;
import com.minsait.equipo2.msvc.tienda.repositories.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Integer totalEnvios(Long idTienda) {
        return null;
    }

    @Override
    public Integer totalProductos(Long idProducto) {
        return productoRepository.findById(idProducto).get().getCantidadProducto();
    }

    @Override
    public Producto save(Producto producto) {

        return null;
    }

    @Override
    public boolean deleteById(Long idProducto) {
        return false;
    }
}
