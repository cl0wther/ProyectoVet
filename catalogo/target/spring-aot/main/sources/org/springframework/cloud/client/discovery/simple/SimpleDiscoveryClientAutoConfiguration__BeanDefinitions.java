package org.springframework.cloud.client.discovery.simple;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SimpleDiscoveryClientAutoConfiguration}.
 */
@Generated
public class SimpleDiscoveryClientAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'simpleDiscoveryClientAutoConfiguration'.
   */
  public static BeanDefinition getSimpleDiscoveryClientAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpleDiscoveryClientAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(SimpleDiscoveryClientAutoConfiguration::new);
    return beanDefinition;
  }
}
