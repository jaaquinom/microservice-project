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
@Table(name = "tienda")
public class Tienda implements Serializable {
    static private final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_tienda", nullable = false)
    private String nombreTienda;
    @Column(name = "direccion_tienda", nullable = false)
    private String direccionTienda;
    @Column(name = "cantidad_productos", nullable = false)
    private Integer cantidadProductos;
    @Column(name = "envios", nullable = false)
    private int envios;

   public void actualizarEnvio(){
       this.envios+=1;
    }
}