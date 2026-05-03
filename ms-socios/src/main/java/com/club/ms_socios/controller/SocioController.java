package com.club.ms_socios.controller;

import com.club.ms_socios.model.dto.EmailUpdateDTO;
import com.club.ms_socios.model.dto.SocioRequestDTO;
import com.club.ms_socios.model.entity.Socio;
import com.club.ms_socios.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Endpoint GET para listar socios
    @GetMapping
    public ResponseEntity<List<Socio>> listarSocios() {
        List<Socio> lista = socioService.listarTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK); // 200 OK
    }

    // Rutas literales antes de /{id} para evitar conflictos de enrutado
    @GetMapping("/activos")
    public ResponseEntity<List<Socio>> listarActivos() {
        List<Socio> lista = socioService.listarActivos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    // Endpoint Get para contar socios activos 
    @GetMapping("/estadisticas/activos")
    public ResponseEntity<Long> contarActivos() {
        long total = socioService.contarSociosActivos();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
   // Endpoint Get para buscar un socio por su RUT
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Socio> obtenerPorRut(@PathVariable String rut) {
        Socio socio = socioService.buscarPorRut(rut);
        return new ResponseEntity<>(socio, HttpStatus.OK);
    }

    // GET por ID: http://localhost:8080/api/socios/1
    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerPorId(@PathVariable Long id) {
        Socio socio = socioService.buscarPorId(id);
        return new ResponseEntity<>(socio, HttpStatus.OK);
    }

    // PUT por ID: http://localhost:8080/api/socios/1
    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizar(@PathVariable Long id, @Valid @RequestBody SocioRequestDTO dto) {
        Socio socioActualizado = socioService.actualizarSocio(id, dto);
        return new ResponseEntity<>(socioActualizado, HttpStatus.OK);
    }


   // Put para reactivar un socio
    @PutMapping("/{id}/reactivar")
    public ResponseEntity<Socio> reactivar(@PathVariable Long id) {
        Socio socio = socioService.reactivarSocio(id);
        return new ResponseEntity<>(socio, HttpStatus.OK);
    }
   // Patch para actualizar el email de un socio
    @PatchMapping("/{id}/email")
    public ResponseEntity<Socio> actualizarEmail(@PathVariable Long id, @Valid @RequestBody EmailUpdateDTO dto) {
        Socio socio = socioService.actualizarEmail(id, dto);
        return new ResponseEntity<>(socio, HttpStatus.OK);
    }

    // DELETE por ID: http://localhost:8080/api/socios/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Socio> eliminar(@PathVariable Long id) {
        Socio socioDesactivado = socioService.desactivarSocio(id);

        // Retornamos el socio con el nuevo estado y un código 200 OK
        return new ResponseEntity<>(socioDesactivado, HttpStatus.OK);
    }
}
