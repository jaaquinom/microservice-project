package com.minsait.equipo2.msvc.cliente.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente implements Serializable {
    static private final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "pedidos")
    private Integer cantidad_pedidos;
}