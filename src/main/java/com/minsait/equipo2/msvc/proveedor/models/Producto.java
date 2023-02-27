package com.minsait.equipo2.msvc.proveedor.models;

import com.minsait.equipo2.msvc.proveedor.exceptions.ProductoInsuficienteException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private Integer cantidad;

    public Integer consultarCantidad(){
        return this.cantidad;
    }

    public void restarCantidad(Integer monto){
        Integer total_actual = this.cantidad-monto;
        if(total_actual < 0){
            throw new ProductoInsuficienteException("Producto insuficiente");
        }
        this.cantidad = total_actual;
    }
}