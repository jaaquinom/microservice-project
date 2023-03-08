package com.minsait.equipo2.msvc.proveedor.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proveedores")
public class Proveedor implements Serializable {
    static private final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad_productos;
    @Column(name = "envios", nullable = false)
    private Integer envios;

    public void actualizarCantidadPedidos(){
        this.envios += 1;
    }
}
