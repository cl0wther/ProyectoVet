package com.provet.citas;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provet.citas.client.MascotaClient;
import com.provet.citas.dto.CitaRequestDTO;
import com.provet.citas.dto.CitaResponseDTO;
import com.provet.citas.dto.MascotaDTO;
import com.provet.citas.model.Cita;
import com.provet.citas.repository.CitaRepository;
import com.provet.citas.services.CitaService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    @Mock private CitaRepository citaRepository;
    @Mock private MascotaClient mascotaClient;
    @InjectMocks private CitaService citaService;

    @Test
    @DisplayName("1. Guardar Cita: Éxito cuando mascota existe")
    void guardarCita_Exito() {
        CitaRequestDTO request = TestDataFactory.unaCitaRequest();
        Cita citaEntidad = TestDataFactory.unaCita();
        
        when(mascotaClient.obtenerMascotaPorId(anyLong())).thenReturn(new MascotaDTO());
        when(citaRepository.save(any(Cita.class))).thenReturn(citaEntidad);

        CitaResponseDTO response = citaService.guardar(request);

        assertThat(response).isNotNull();
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    @DisplayName("2. Guardar Cita: Error si mascota no existe (Feign falla)")
    void guardarCita_MascotaNoEncontrada() {
        CitaRequestDTO request = TestDataFactory.unaCitaRequest();
        when(mascotaClient.obtenerMascotaPorId(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> citaService.guardar(request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("3. Buscar Cita: Retorna datos correctamente")
    void buscarCita_Exito() {
        Cita cita = TestDataFactory.unaCita();
        when(citaRepository.findById(1L)).thenReturn(java.util.Optional.of(cita));
    
        java.util.Optional<CitaResponseDTO> responseOptional = citaService.obtenerPorId(1L);

        assertThat(responseOptional).isPresent();
        assertThat(responseOptional.get().getMotivo()).isEqualTo(cita.getMotivo());
}

    @Test
    @DisplayName("4. Eliminar Cita: Verifica llamada al repositorio")
    void eliminarCita_Exito() {
        doNothing().when(citaRepository).deleteById(1L);
        citaService.eliminar(1L);
        verify(citaRepository, times(1)).deleteById(1L);
    }
}