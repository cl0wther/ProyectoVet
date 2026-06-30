package com.provet.catalogo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provet.catalogo.dto.MedRequestDTO;
import com.provet.catalogo.dto.MedResponseDTO;
import com.provet.catalogo.model.CategoriaMed;
import com.provet.catalogo.model.Medicamento;
import com.provet.catalogo.repository.CategoriaRepository;
import com.provet.catalogo.repository.MedRepository;
import com.provet.catalogo.service.MedService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class MedServiceTest {

    @Mock
    private MedRepository medRepository;

    @Mock
    private CategoriaRepository categoriaRepository;


    @InjectMocks
    private MedService medService;

    private final Faker faker = new Faker();
    private Medicamento medMock;
    private CategoriaMed categoriaMock;
    
    @BeforeEach
    void setUp() {
        categoriaMock = new CategoriaMed();
        categoriaMock.setId(1L);
        categoriaMock.setNombre(faker.options().option("Ansiolíticos", "Antibióticos", "Analgésicos", "Vacunas", "Vitaminas"));

        medMock = new Medicamento();
        medMock.setId(1L);
        medMock.setNombreMed(faker.medication().drugName());
        medMock.setPrecioMed(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50)));
        medMock.setCategoria(categoriaMock);
    }

@Test
    void obtenerTodos_retornaListaCompleta() {
        when(medRepository.findAll()).thenReturn(List.of(medMock));

        List<MedResponseDTO> result = medService.obtenerTodos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreMed()).isEqualTo(medMock.getNombreMed());
        verify(medRepository).findAll();
    }

    @Test
    void obtenerPorId_medicamentoExiste_retornaDTO() {
    when(medRepository.findById(1L)).thenReturn(Optional.of(medMock));

    Optional<MedResponseDTO> result = medService.obtenerPorId(1L);

    assertThat(result).isPresent(); 
    MedResponseDTO dto = result.get(); 

    // Hacemos las pruebas sobre el DTO
    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getNombreMed()).isEqualTo(medMock.getNombreMed());
    assertThat(dto.getPrecio()).isEqualTo(medMock.getPrecioMed());
}

    @Test
    void obtenerPorId_medicamentoNoExiste_lanzaExcepcion() {
        when(medRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medService.obtenerPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    void crear_datosValidos_retornaMedicamentoCreado() {
        MedRequestDTO request = new MedRequestDTO();
        request.setNombreMed(medMock.getNombreMed());
        request.setDescripcion(medMock.getDescripcion());
        request.setPrecio(medMock.getPrecioMed());
        request.setCategoriaId(1L); 


        when(medRepository.save(any(Medicamento.class))).thenReturn(medMock);

        MedResponseDTO result = medService.guardar(request);

        assertThat(result.getNombreMed()).isEqualTo(request.getNombreMed());
        verify(medRepository).save(any(Medicamento.class));
    }

    @Test
    void eliminar_medicamentoExiste_eliminaCorrectamente() {
        when(medRepository.existsById(1L)).thenReturn(true);

        medService.eliminar(1L);

        verify(medRepository).deleteById(1L);
    }

    @Test
    void eliminar_medicamentoNoExiste_lanzaExcepcion() {
        when(medRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> medService.eliminar(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");

        verify(medRepository, never()).deleteById(any());
    }
}