package com.provet.citas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.provet.citas.dto.DuenioDTO;

@FeignClient(name = "duenio-service", url = "${duenio.service.url}")
public interface DuenioClient {

    @GetMapping("/api/duenio/{id}")
    DuenioDTO obtenerDuenioPorId(@PathVariable("id") Long id);
}

