package com.provet.catalogo.config;

import com.provet.catalogo.repository.CategoriaRepository;
import com.provet.catalogo.repository.MedRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataInitializer}.
 */
@Generated
public class DataInitializer__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'dataInitializer'.
   */
  private static BeanInstanceSupplier<DataInitializer> getDataInitializerInstanceSupplier() {
    return BeanInstanceSupplier.<DataInitializer>forConstructor(CategoriaRepository.class, MedRepository.class)
            .withGenerator((registeredBean, args) -> new DataInitializer(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'dataInitializer'.
   */
  public static BeanDefinition getDataInitializerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DataInitializer.class);
    beanDefinition.setInstanceSupplier(getDataInitializerInstanceSupplier());
    return beanDefinition;
  }
}
