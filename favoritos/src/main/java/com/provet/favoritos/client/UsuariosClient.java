package com.provet.favoritos.client;

import com.provet.favoritos.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

/**
 * UsuariosClient
 *
 * Clínica Veterinaria Provet
 *
 * Implementado con WebClient (Reactivo) para conectar con el microservicio 
 * de usuarios. Su propósito es validar si el dueño de la mascota existe 
 * en el sistema antes de permitirle agregar un medicamento a favoritos.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UsuariosClient {

    private final WebClient usuariosWebClient;

    public Optional<UsuarioDTO> buscarPorNombre(String nombre) {
        try {
            UsuarioDTO usuario = usuariosWebClient.get()
                    .uri("/api/usuarios/nombre/{nombre}", nombre)
                    .retrieve()
                    .bodyToMono(UsuarioDTO.class)
                    .block();
            return Optional.ofNullable(usuario);
        } catch (WebClientResponseException.NotFound e) {
            // Si el cliente no existe (404), retornamos un Optional vacío
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al conectar con provet_usuarios: {}", e.getMessage());
            throw new RuntimeException("El servicio de usuarios de Provet no está disponible en este momento");
        }
    }
}

