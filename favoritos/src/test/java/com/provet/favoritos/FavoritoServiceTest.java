package com.provet.favoritos;

import com.provet.favoritos.client.CatalogoClient;
import com.provet.favoritos.client.UsuariosClient;
import com.provet.favoritos.dto.FavoritoRequestDTO;
import com.provet.favoritos.dto.FavoritoResponseDTO;
import com.provet.favoritos.dto.MedicamentoDTO;
import com.provet.favoritos.dto.UsuarioDTO;
import com.provet.favoritos.model.Favorito;
import com.provet.favoritos.repository.FavoritoRepository;
import com.provet.favoritos.service.FavoritoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de FavoritoService para Provet.
 * Adaptadas al comportamiento original del servicio.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FavoritoService - Pruebas Unitarias")
class FavoritoServiceTest {

    @Mock
    private FavoritoRepository favoritoRepository;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private UsuariosClient usuariosClient;

    @InjectMocks
    private FavoritoService favoritoService;

    @Test
    @DisplayName("agregar: guarda el favorito correctamente sin duplicados")
    void agregar_datosValidos_guardaFavorito() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        UsuarioDTO usuario = TestDataFactory.unUsuarioDTO();
        FavoritoRequestDTO request = TestDataFactory.unFavoritoRequest(
                medicamento.getId(), usuario.getNombre());

        Favorito favoritoGuardado = TestDataFactory.unFavorito(
                medicamento.getId(), medicamento.getNombre(), usuario.getNombre());

        when(usuariosClient.obtenerDuenioPorNombre(usuario.getNombre())).thenReturn(usuario);
        when(catalogoClient.obtenerMedicamento(medicamento.getId())).thenReturn(medicamento);
        
        when(favoritoRepository.existsByMedicamentoIdAndUsuario(medicamento.getId(), usuario.getNombre()))
                .thenReturn(false);
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(favoritoGuardado);

        FavoritoResponseDTO resultado = favoritoService.agregar(request);


        assertThat(resultado.getNombreMedicamento()).isEqualTo(medicamento.getNombre());
        assertThat(resultado.getUsuario()).isEqualTo(usuario.getNombre());
        assertThat(resultado.getMedicamentoId()).isEqualTo(medicamento.getId());
        verify(favoritoRepository).save(any(Favorito.class));
    }

    @Test
    @DisplayName("agregar: lanza excepcion cuando el usuario ya tiene ese medicamento en favoritos")
    void agregar_favoritoDuplicado_lanzaExcepcion() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        UsuarioDTO usuario = TestDataFactory.unUsuarioDTO();
        FavoritoRequestDTO request = TestDataFactory.unFavoritoRequest(
                medicamento.getId(), usuario.getNombre());

        when(usuariosClient.obtenerDuenioPorNombre(usuario.getNombre())).thenReturn(usuario);
        when(catalogoClient.obtenerMedicamento(medicamento.getId())).thenReturn(medicamento);
        

        when(favoritoRepository.existsByMedicamentoIdAndUsuario(medicamento.getId(), usuario.getNombre()))
                .thenReturn(true);

        assertThatThrownBy(() -> favoritoService.agregar(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ya tiene ese medicamento en favoritos");

        verify(favoritoRepository, never()).save(any());
}

    @Test
    @DisplayName("listarTodos: retorna todos los favoritos como DTO")
    void listarTodos_retornaListaCompleta() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        Favorito f1 = TestDataFactory.unFavorito(medicamento.getId(), medicamento.getNombre(), "juan");
        Favorito f2 = TestDataFactory.unFavorito(medicamento.getId(), medicamento.getNombre(), "maria");
        when(favoritoRepository.findAll()).thenReturn(List.of(f1, f2));

        List<FavoritoResponseDTO> resultado = favoritoService.listarTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(FavoritoResponseDTO::getUsuario)
                .containsExactly("juan", "maria");
        verify(favoritoRepository).findAll();
    }

    @Test
    @DisplayName("listarPorUsuario: retorna solo los favoritos del usuario indicado")
    void listarPorUsuario_retornaFavoritosDelUsuario() {
        MedicamentoDTO m1 = TestDataFactory.unMedicamentoDTO();
        MedicamentoDTO m2 = TestDataFactory.unMedicamentoDTO();
        MedicamentoDTO m3 = TestDataFactory.unMedicamentoDTO();
        Favorito f1 = TestDataFactory.unFavorito(m1.getId(), m1.getNombre(), "juan");
        Favorito f2 = TestDataFactory.unFavorito(m2.getId(), m2.getNombre(), "juan");
        Favorito f3 = TestDataFactory.unFavorito(m3.getId(), m3.getNombre(), "juan");
        when(favoritoRepository.findByUsuario("juan")).thenReturn(List.of(f1, f2, f3));

        List<FavoritoResponseDTO> resultado = favoritoService.listarPorUsuario("juan");

        assertThat(resultado).hasSize(3);
        assertThat(resultado).allMatch(f -> f.getUsuario().equals("juan"));
        verify(favoritoRepository).findByUsuario("juan");
    }

    @Test
    @DisplayName("eliminar: elimina el favorito cuando el id existe")
    void eliminar_favoritoExistente_llamaDelete() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        Favorito favorito = TestDataFactory.unFavorito(medicamento.getId(), medicamento.getNombre(), "juan");
        
        when(favoritoRepository.findById(favorito.getId())).thenReturn(Optional.of(favorito));

        favoritoService.eliminar(favorito.getId());

        verify(favoritoRepository).findById(favorito.getId());
        verify(favoritoRepository).delete(favorito);
    }

    @Test
    @DisplayName("eliminar: lanza excepcion cuando el id no existe")
    void eliminar_favoritoNoExiste_lanzaExcepcion() {
        when(favoritoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> favoritoService.eliminar(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no encontrado");

        verify(favoritoRepository, never()).delete(any());
    }
}