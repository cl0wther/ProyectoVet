package org.springframework.cloud.openfeign;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link FeignClientProperties}.
 */
@Generated
public class FeignClientProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'feignClientProperties'.
   */
  public static BeanDefinition getFeignClientPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignClientProperties.class);
    beanDefinition.setInstanceSupplier(FeignClientProperties::new);
    return beanDefinition;
  }
}
