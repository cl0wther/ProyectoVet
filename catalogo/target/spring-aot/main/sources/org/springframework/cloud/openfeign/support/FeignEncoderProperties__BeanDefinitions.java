package org.springframework.cloud.openfeign.support;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link FeignEncoderProperties}.
 */
@Generated
public class FeignEncoderProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'feignEncoderProperties'.
   */
  public static BeanDefinition getFeignEncoderPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignEncoderProperties.class);
    beanDefinition.setInstanceSupplier(FeignEncoderProperties::new);
    return beanDefinition;
  }
}
