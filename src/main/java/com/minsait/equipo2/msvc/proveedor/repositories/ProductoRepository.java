package com.minsait.equipo2.msvc.proveedor.repositories;

import com.minsait.equipo2.msvc.proveedor.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
