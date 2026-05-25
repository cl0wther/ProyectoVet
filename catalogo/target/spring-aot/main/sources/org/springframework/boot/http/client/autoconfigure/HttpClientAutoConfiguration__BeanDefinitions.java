package org.springframework.boot.http.client.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.http.client.HttpClientSettings;

/**
 * Bean definitions for {@link HttpClientAutoConfiguration}.
 */
@Generated
public class HttpClientAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'httpClientAutoConfiguration'.
   */
  public static BeanDefinition getHttpClientAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpClientAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(HttpClientAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'httpClientSettings'.
   */
  private static BeanInstanceSupplier<HttpClientSettings> getHttpClientSettingsInstanceSupplier() {
    return BeanInstanceSupplier.<HttpClientSettings>forFactoryMethod(HttpClientAutoConfiguration.class, "httpClientSettings", ObjectProvider.class, HttpClientsProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.http.client.autoconfigure.HttpClientAutoConfiguration", HttpClientAutoConfiguration.class).httpClientSettings(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'httpClientSettings'.
   */
  public static BeanDefinition getHttpClientSettingsBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpClientSettings.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.http.client.autoconfigure.HttpClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getHttpClientSettingsInstanceSupplier());
    return beanDefinition;
  }
}
