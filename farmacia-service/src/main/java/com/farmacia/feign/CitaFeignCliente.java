package com.farmacia.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "citas-service")
public interface CitaFeignCliente {

    @GetMapping("/api/citas/{id}")
    CitaFeign obtenerCitaPorId(@PathVariable("id") Integer id);
}