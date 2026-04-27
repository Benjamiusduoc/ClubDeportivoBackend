package com.club.ms_socios.service;

import com.club.ms_socios.model.dto.SocioRequestDTO;
import com.club.ms_socios.model.entity.Socio;
import com.club.ms_socios.repository.SocioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service //esta clase es un servicio que maneja lógica
public class SocioService {

    // SLF4J para cumplir con el registro de logs estructurados
    private static final Logger log = LoggerFactory.getLogger(SocioService.class);

    private final SocioRepository socioRepository;

    // Inyección de dependencias a través del constructor
    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public Socio registrarSocio(SocioRequestDTO dto) {
        log.info("Iniciando registro de nuevo socio con RUT: {}", dto.getRut());

        //Valida si el RUT ya existe en la Base de Datos
        Optional<Socio> socioExistenteRut = socioRepository.findByRut(dto.getRut());
        if (socioExistenteRut.isPresent()) {
            log.error("Validación fallida: El RUT {} ya se encuentra registrado", dto.getRut());
            throw new RuntimeException("El RUT ingresado ya pertenece a un socio existente.");
        }

        //Valida si el Email ya existe
        Optional<Socio> socioExistenteEmail = socioRepository.findByEmail(dto.getEmail());
        if (socioExistenteEmail.isPresent()) {
            log.error("Validación fallida: El correo {} ya se encuentra registrado", dto.getEmail());
            throw new RuntimeException("El correo ingresado ya pertenece a un socio existente.");
        }

        // Si pasa la validacion, transformamos el DTO en una Entidad
        Socio nuevoSocio = new Socio();
        nuevoSocio.setNombre(dto.getNombre());
        nuevoSocio.setRut(dto.getRut());
        nuevoSocio.setEmail(dto.getEmail());

        // Asignamos los datos internos que el usuario no debe enviar por seguridad
        nuevoSocio.setFechaInscripcion(LocalDate.now());
        nuevoSocio.setActivo(true); //socio nuevo entra como activo por defecto

        // Guardamos físicamente en la tabla de MySQL
        Socio socioGuardado = socioRepository.save(nuevoSocio);

        log.info("Socio registrado exitosamente con ID: {}", socioGuardado.getId());

        return socioGuardado;
    }
}