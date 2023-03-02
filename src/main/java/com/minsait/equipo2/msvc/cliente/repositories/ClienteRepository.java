package com.minsait.equipo2.msvc.cliente.repositories;

import com.minsait.equipo2.msvc.cliente.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
