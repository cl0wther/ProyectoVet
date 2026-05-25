package org.springframework.boot.http.client.autoconfigure.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.env.Environment;

/**
 * Bean definitions for {@link HttpServiceClientPropertiesAutoConfiguration}.
 */
@Generated
public class HttpServiceClientPropertiesAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'httpServiceClientPropertiesAutoConfiguration'.
   */
  public static BeanDefinition getHttpServiceClientPropertiesAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpServiceClientPropertiesAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(HttpServiceClientPropertiesAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'httpServiceClientProperties'.
   */
  private static BeanInstanceSupplier<HttpServiceClientProperties> getHttpServiceClientPropertiesInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<HttpServiceClientProperties>forFactoryMethod(HttpServiceClientPropertiesAutoConfiguration.class, "httpServiceClientProperties", Environment.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.http.client.autoconfigure.service.HttpServiceClientPropertiesAutoConfiguration", HttpServiceClientPropertiesAutoConfiguration.class).httpServiceClientProperties(args.get(0)));
  }

  /**
   * Get the bean definition for 'httpServiceClientProperties'.
   */
  public static BeanDefinition getHttpServiceClientPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpServiceClientProperties.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.http.client.autoconfigure.service.HttpServiceClientPropertiesAutoConfiguration");
    beanDefinition.setInstanceSupplier(getHttpServiceClientPropertiesInstanceSupplier());
    return beanDefinition;
  }
}
