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
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private Integer cantidad_productos;
    @Column(name = "pedidos")
    private Integer pedidos;

    public void actualizarCantidadPedidos(){
        this.pedidos += 1;
    }
}
