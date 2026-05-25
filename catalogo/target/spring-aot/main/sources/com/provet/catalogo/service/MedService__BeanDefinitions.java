package com.provet.catalogo.service;

import com.provet.catalogo.repository.CategoriaRepository;
import com.provet.catalogo.repository.MedRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MedService}.
 */
@Generated
public class MedService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'medService'.
   */
  private static BeanInstanceSupplier<MedService> getMedServiceInstanceSupplier() {
    return BeanInstanceSupplier.<MedService>forConstructor(MedRepository.class, CategoriaRepository.class)
            .withGenerator((registeredBean, args) -> new MedService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'medService'.
   */
  public static BeanDefinition getMedServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MedService.class);
    beanDefinition.setInstanceSupplier(getMedServiceInstanceSupplier());
    return beanDefinition;
  }
}
