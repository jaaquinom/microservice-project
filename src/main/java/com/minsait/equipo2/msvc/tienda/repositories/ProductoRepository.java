package com.minsait.equipo2.msvc.tienda.repositories;

import com.minsait.equipo2.msvc.tienda.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
