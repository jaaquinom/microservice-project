package com.minsait.equipo2.msvc.tienda.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos_tienda")
public class Producto implements Serializable {
    static private final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;
    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;
}