package com.minsait.equipo2.msvc.cliente.tiendaFeignClient;

import com.minsait.equipo2.msvc.cliente.models.Pedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "msvc-tienda", url = "localhost:8030/tienda")
public interface PedidoTienda {
    @PostMapping("/envio_a_cliente")
    List<Pedido> envio(@RequestBody List<Pedido> pedido);

    @GetMapping("/listar_productos")
    List<Pedido> findAll();
}