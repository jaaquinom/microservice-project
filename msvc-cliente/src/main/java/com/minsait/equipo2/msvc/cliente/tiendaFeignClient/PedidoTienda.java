package com.minsait.equipo2.msvc.cliente.tiendaFeignClient;

import com.minsait.equipo2.msvc.cliente.models.Pedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "msvc-tienda", url = "localhost:8030/tienda")
public interface PedidoTienda {
    @PostMapping("/envio")
    List<Pedido> envio(@RequestBody List<Pedido> pedido);

}
