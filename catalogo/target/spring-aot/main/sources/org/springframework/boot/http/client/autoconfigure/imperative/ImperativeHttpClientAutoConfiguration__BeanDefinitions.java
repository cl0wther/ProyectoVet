package org.springframework.boot.http.client.autoconfigure.imperative;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.ClientHttpRequestFactory;

/**
 * Bean definitions for {@link ImperativeHttpClientAutoConfiguration}.
 */
@Generated
public class ImperativeHttpClientAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.http.client.autoconfigure.imperative.ImperativeHttpClientAutoConfiguration'.
   */
  private static BeanInstanceSupplier<ImperativeHttpClientAutoConfiguration> getImperativeHttpClientAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ImperativeHttpClientAutoConfiguration>forConstructor(Environment.class)
            .withGenerator((registeredBean, args) -> new ImperativeHttpClientAutoConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'imperativeHttpClientAutoConfiguration'.
   */
  public static BeanDefinition getImperativeHttpClientAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ImperativeHttpClientAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(getImperativeHttpClientAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientHttpRequestFactoryBuilder'.
   */
  private static BeanInstanceSupplier<ClientHttpRequestFactoryBuilder> getClientHttpRequestFactoryBuilderInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ClientHttpRequestFactoryBuilder>forFactoryMethod(ImperativeHttpClientAutoConfiguration.class, "clientHttpRequestFactoryBuilder", ResourceLoader.class, ImperativeHttpClientsProperties.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.http.client.autoconfigure.imperative.ImperativeHttpClientAutoConfiguration", ImperativeHttpClientAutoConfiguration.class).clientHttpRequestFactoryBuilder(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'clientHttpRequestFactoryBuilder'.
   */
  public static BeanDefinition getClientHttpRequestFactoryBuilderBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ClientHttpRequestFactoryBuilder.class);
    beanDefinition.setTargetType(ResolvableType.forClass(ClientHttpRequestFactoryBuilder.class));
    beanDefinition.setFactoryBeanName("org.springframework.boot.http.client.autoconfigure.imperative.ImperativeHttpClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getClientHttpRequestFactoryBuilderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientHttpRequestFactory'.
   */
  private static BeanInstanceSupplier<ClientHttpRequestFactory> getClientHttpRequestFactoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ClientHttpRequestFactory>forFactoryMethod(ImperativeHttpClientAutoConfiguration.class, "clientHttpRequestFactory", ClientHttpRequestFactoryBuilder.class, HttpClientSettings.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.http.client.autoconfigure.imperative.ImperativeHttpClientAutoConfiguration", ImperativeHttpClientAutoConfiguration.class).clientHttpRequestFactory(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'clientHttpRequestFactory'.
   */
  public static BeanDefinition getClientHttpRequestFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ClientHttpRequestFactory.class);
    beanDefinition.setLazyInit(true);
    beanDefinition.setFactoryBeanName("org.springframework.boot.http.client.autoconfigure.imperative.ImperativeHttpClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getClientHttpRequestFactoryInstanceSupplier());
    return beanDefinition;
  }
}
