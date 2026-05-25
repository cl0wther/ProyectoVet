package com.provet.catalogo.service;

import com.provet.catalogo.repository.CategoriaRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CategoriaService}.
 */
@Generated
public class CategoriaService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'categoriaService'.
   */
  private static BeanInstanceSupplier<CategoriaService> getCategoriaServiceInstanceSupplier() {
    return BeanInstanceSupplier.<CategoriaService>forConstructor(CategoriaRepository.class)
            .withGenerator((registeredBean, args) -> new CategoriaService(args.get(0)));
  }

  /**
   * Get the bean definition for 'categoriaService'.
   */
  public static BeanDefinition getCategoriaServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CategoriaService.class);
    beanDefinition.setInstanceSupplier(getCategoriaServiceInstanceSupplier());
    return beanDefinition;
  }
}
