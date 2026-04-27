package com.club.ms_socios.controller;

import com.club.ms_socios.model.dto.SocioRequestDTO;
import com.club.ms_socios.model.entity.Socio;
import com.club.ms_socios.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // esta clase responderá con JSON
@RequestMapping("/api/socios") // Esta es la ruta base
public class SocioController {

    private final SocioService socioService;

    // Inyección de dependencias
    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    //POST para crear un socio
    @PostMapping
    public ResponseEntity<Socio> crearSocio(@Valid @RequestBody SocioRequestDTO dto) {
        Socio nuevoSocio = socioService.registrarSocio(dto);

        // Retornamos el socio creado con un código HTTP 201
        return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
    }
}