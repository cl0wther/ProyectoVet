package com.provet.catalogo.repository;

import com.provet.catalogo.model.Medicamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link MedRepository}.
 */
@Generated
public class MedRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public MedRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link MedRepository#findByCategoriaId(java.lang.Long)}.
   */
  public List<Medicamento> findByCategoriaId(Long categoriaId) {
    String queryString = "SELECT m FROM Medicamento m WHERE m.categoria.id = :categoriaId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("categoriaId", categoriaId);

    return (List<Medicamento>) query.getResultList();
  }
}
