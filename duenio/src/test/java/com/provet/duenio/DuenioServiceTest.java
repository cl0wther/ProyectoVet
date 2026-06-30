package com.provet.duenio;

import com.provet.duenio.dto.DuenioRequestDTO;
import com.provet.duenio.dto.DuenioResponseDTO;
import com.provet.duenio.model.Duenio;
import com.provet.duenio.repository.DuenioRepository;
import com.provet.duenio.services.DuenioService;
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
public class DuenioServiceTest {

    @Mock
    private DuenioRepository duenioRepository;

    @InjectMocks
    private DuenioService duenioService;

    @Test
    @DisplayName("1. Guardar Dueño: Éxito al persistir datos")
    void guardarDuenio_debeRetornarResponseDTO() {
        DuenioRequestDTO request = TestDataFactory.unDuenioRequest();
        Duenio duenioGuardado = TestDataFactory.unDuenio();

        when(duenioRepository.save(any(Duenio.class))).thenReturn(duenioGuardado);

        DuenioResponseDTO response = duenioService.guardar(request);

        assertThat(response.getNombre()).isEqualTo(duenioGuardado.getNombre());
        verify(duenioRepository, times(1)).save(any(Duenio.class));
    }

    @Test
    @DisplayName("2. Listar Dueños: Debe retornar todos los registros")
    void obtenerTodos_debeRetornarLista() {
        when(duenioRepository.findAll()).thenReturn(List.of(TestDataFactory.unDuenio(), TestDataFactory.unDuenio()));

        List<DuenioResponseDTO> resultado = duenioService.obtenerTodos();

        assertThat(resultado).hasSize(2);
        verify(duenioRepository, times(1)).findAll();
    }
@Test
    @DisplayName("3. Buscar Dueño: Retorna datos si el ID existe")
    void buscarPorId_existe_retornaDuenio() {
        Duenio duenio = TestDataFactory.unDuenio();
        when(duenioRepository.findById(1L)).thenReturn(Optional.of(duenio));

        java.util.Optional<DuenioResponseDTO> responseOptional = duenioService.obtenerPorId(1L);
        
        assertThat(responseOptional).isPresent();
        assertThat(responseOptional.get().getNombre()).isEqualTo(duenio.getNombre());
    }

    @Test
    @DisplayName("4. Buscar Dueño: Lanza excepción si el ID no existe")
    void buscarPorId_noExiste_lanzaExcepcion() {
        when(duenioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> duenioService.obtenerPorId(99L))
                .isInstanceOf(RuntimeException.class) // Asegúrate de que tu servicio lance esta excepción
                .hasMessageContaining("Dueño no encontrado");
    }
}