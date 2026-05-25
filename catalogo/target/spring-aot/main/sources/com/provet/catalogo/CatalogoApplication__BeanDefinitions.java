package com.provet.catalogo;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CatalogoApplication}.
 */
@Generated
public class CatalogoApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'catalogoApplication'.
   */
  public static BeanDefinition getCatalogoApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CatalogoApplication.class);
    beanDefinition.setInstanceSupplier(CatalogoApplication::new);
    return beanDefinition;
  }
}
