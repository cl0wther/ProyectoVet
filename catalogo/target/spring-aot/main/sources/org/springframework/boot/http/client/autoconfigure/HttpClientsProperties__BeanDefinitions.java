package org.springframework.boot.http.client.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HttpClientsProperties}.
 */
@Generated
public class HttpClientsProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'httpClientsProperties'.
   */
  public static BeanDefinition getHttpClientsPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpClientsProperties.class);
    beanDefinition.setInstanceSupplier(HttpClientsProperties::new);
    return beanDefinition;
  }
}
