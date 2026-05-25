package org.springframework.cloud.openfeign;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link FeignOAuth2Properties}.
 */
@Generated
public class FeignOAuth2Properties__BeanDefinitions {
  /**
   * Get the bean definition for 'feignOAuth2Properties'.
   */
  public static BeanDefinition getFeignOAuthPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignOAuth2Properties.class);
    beanDefinition.setInstanceSupplier(FeignOAuth2Properties::new);
    return beanDefinition;
  }
}
