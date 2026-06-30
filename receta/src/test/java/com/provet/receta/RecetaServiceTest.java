package com.provet.receta;

import com.provet.receta.client.CatalogoClient;
import com.provet.receta.client.CitaClient;
import com.provet.receta.client.MascotaClient;
import com.provet.receta.dto.MascotaDTO;
import com.provet.receta.dto.CitaDTO;
import com.provet.receta.dto.MedicamentoDTO;
import com.provet.receta.dto.RecetaRequestDTO;
import com.provet.receta.dto.RecetaResponseDTO;
import com.provet.receta.model.Receta;
import com.provet.receta.repository.RecetaRepository;
import com.provet.receta.service.RecetaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private MascotaClient mascotaClient;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private CitaClient citaClient;

    @InjectMocks
    private RecetaService recetaService;

    @Test
    void crearReceta_datosValidos_guardaYRetornaDTO() {
        RecetaRequestDTO request = TestDataFactory.unaRecetaRequest();
        MascotaDTO mascotaMock = TestDataFactory.unaMascotaDTO();
        CitaDTO citaMock = TestDataFactory.unaCitaDTO();
        MedicamentoDTO medicamentoMock = TestDataFactory.unMedicamentoDTO();

        when(mascotaClient.obtenerMascotaPorId(request.getIdMascota())).thenReturn(mascotaMock);
        when(citaClient.obtenerCitaPorId(request.getIdCita())).thenReturn(citaMock);
        when(catalogoClient.obtenerMedicamentoPorId(request.getDetalles().get(0).getIdMedicamento())).thenReturn(medicamentoMock);
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(invocation -> {
            Receta recetaGuardada = invocation.getArgument(0);
            recetaGuardada.setId(1L);
            return recetaGuardada;
        });

        RecetaResponseDTO resultado = recetaService.crearReceta(request);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getIdMascota()).isEqualTo(request.getIdMascota());
        assertThat(resultado.getIdCita()).isEqualTo(request.getIdCita());
        assertThat(resultado.getEstado()).isEqualTo("DISPONIBLE");
        assertThat(resultado.getDetalles()).hasSize(1);
        
        verify(mascotaClient).obtenerMascotaPorId(request.getIdMascota());
        verify(citaClient).obtenerCitaPorId(request.getIdCita());
        verify(catalogoClient).obtenerMedicamentoPorId(request.getDetalles().get(0).getIdMedicamento());
        verify(recetaRepository).save(any(Receta.class));
    }

    @Test
    void obtenerPorId_recetaExiste_retornaDTO() {
        Receta recetaMock = TestDataFactory.unaReceta();
        when(recetaRepository.findById(recetaMock.getId())).thenReturn(Optional.of(recetaMock));

        RecetaResponseDTO resultado = recetaService.obtenerPorId(recetaMock.getId());

        assertThat(resultado.getId()).isEqualTo(recetaMock.getId());
        assertThat(resultado.getDiagnostico()).isEqualTo(recetaMock.getDiagnostico());
        
        verify(recetaRepository).findById(recetaMock.getId());
    }

    @Test
    void obtenerPorId_recetaNoExiste_lanzaExcepcion() {
        Long idInexistente = 999L;
        when(recetaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recetaService.obtenerPorId(idInexistente))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Receta no encontrada con el ID: " + idInexistente);
                
        verify(recetaRepository).findById(idInexistente);
    }

    @Test
    void validarMedicamentoPermitido_existenRecetas_retornaTrue() {
        Long idMascota = 1L;
        Long idMedicamento = 2L;
        List<Receta> recetas = List.of(TestDataFactory.unaReceta());
        
        when(recetaRepository.buscarRecetasValidasParaCompra(idMascota, idMedicamento)).thenReturn(recetas);

        boolean resultado = recetaService.validarMedicamentoPermitido(idMascota, idMedicamento);

        assertThat(resultado).isTrue();
        verify(recetaRepository).buscarRecetasValidasParaCompra(idMascota, idMedicamento);
    }

    @Test
    void validarMedicamentoPermitido_noExistenRecetas_retornaFalse() {
        Long idMascota = 1L;
        Long idMedicamento = 2L;
        
        when(recetaRepository.buscarRecetasValidasParaCompra(idMascota, idMedicamento)).thenReturn(Collections.emptyList());

        boolean resultado = recetaService.validarMedicamentoPermitido(idMascota, idMedicamento);

        assertThat(resultado).isFalse();
        verify(recetaRepository).buscarRecetasValidasParaCompra(idMascota, idMedicamento);
    }
}