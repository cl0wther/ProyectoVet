package org.springframework.boot.http.client.autoconfigure.imperative;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ImperativeHttpClientsProperties}.
 */
@Generated
public class ImperativeHttpClientsProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'imperativeHttpClientsProperties'.
   */
  public static BeanDefinition getImperativeHttpClientsPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ImperativeHttpClientsProperties.class);
    beanDefinition.setInstanceSupplier(ImperativeHttpClientsProperties::new);
    return beanDefinition;
  }
}
