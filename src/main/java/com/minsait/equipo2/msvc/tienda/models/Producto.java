package com.minsait.equipo2.msvc.tienda.models;

import com.minsait.equipo2.msvc.tienda.exceptions.ProductoInsuficienteException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
public class Producto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    @Column(name = "cantidad_insumo")
    private Integer cantidadInsumo;

    public Integer verCantidad(){
        return this.cantidadProducto;
    }
    public void restarCantidadProducto(Integer cantidadProducto){
        Integer nuevaCantidad = this.cantidadProducto - cantidadProducto;
        if(nuevaCantidad < 0){
            throw new ProductoInsuficienteException("Producto insuficiente");
        }
        this.cantidadProducto = nuevaCantidad;
    }
    public void sumarCantidadInsumo(Integer cantidadInsumo){
        this.cantidadInsumo = this.cantidadProducto + cantidadInsumo;
    }

}