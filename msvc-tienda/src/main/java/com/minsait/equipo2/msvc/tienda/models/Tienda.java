package com.minsait.equipo2.msvc.tienda.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
    @Column(name = "nombre_tienda")
    private String nombreTienda;
    @Column(name = "direccion_tienda")
    private String direccionTienda;
    /*@Column(name = "cantidad_producto")
    private Producto cantidadProducto;*/
    @Column(name = "cantidad_productos")
    private Integer cantidadProductos;
    @Column(name = "envios")
    private int envios;

   public void envio(){

       this.envios+=1;
    }


}
