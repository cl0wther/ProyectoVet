package com.provet.mascota;

import com.provet.mascota.TestDataFactory;
import com.provet.mascota.client.DuenioClient;
import com.provet.mascota.dto.MascotaRequestDTO;
import com.provet.mascota.dto.MascotaResponseDTO;
import com.provet.mascota.model.Mascotas;
import com.provet.mascota.repository.MascotaRepository;
import com.provet.mascota.service.MascotaService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private DuenioClient duenioClient; // Suponiendo que validas si el dueño existe

    @InjectMocks
    private MascotaService mascotaService;

    @Test
    @DisplayName("Guardar mascota: éxito cuando los datos son válidos")
    void guardarMascota_debeRetornarResponseDTO() {
        // Arrange
        MascotaRequestDTO request = TestDataFactory.unaMascotaRequest();
        Mascotas mascotaGuardada = TestDataFactory.unaMascota();

        when(mascotaRepository.save(any(Mascotas.class))).thenReturn(mascotaGuardada);

        // Act
        MascotaResponseDTO response = mascotaService.guardar(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getNombre()).isEqualTo(mascotaGuardada.getNombre());
        verify(mascotaRepository, times(1)).save(any(Mascotas.class));
    }

    @Test
    @DisplayName("Listar mascotas: debe retornar una lista completa")
    void obtenerTodas_debeRetornarLista() {
        // Arrange
        when(mascotaRepository.findAll()).thenReturn(List.of(TestDataFactory.unaMascota(), TestDataFactory.unaMascota()));

        // Act
        List<MascotaResponseDTO> lista = mascotaService.obtenerTodas();

        // Assert
        assertThat(lista).hasSize(2);
        verify(mascotaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar por ID: debe lanzar excepción si la mascota no existe")
    void buscarPorId_cuandoNoExiste_lanzaExcepcion() {
        // Arrange
        when(mascotaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mascotaService.obtenerPorId(999L))
                .isInstanceOf(RuntimeException.class) // Cambia por tu excepción personalizada si tienes
                .hasMessageContaining("Mascota no encontrada");
    }

    @Test
    @DisplayName("Eliminar mascota: debe llamar al repositorio una vez")
    void eliminar_debeLlamarAlRepositorio() {
        // Arrange
        Long id = 1L;
        doNothing().when(mascotaRepository).deleteById(id);

        // Act
        mascotaService.eliminar(id);

        // Assert
        verify(mascotaRepository, times(1)).deleteById(id);
    }
}