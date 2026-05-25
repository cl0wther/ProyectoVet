package org.springframework.boot.restclient.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestClient;

/**
 * Bean definitions for {@link RestClientAutoConfiguration}.
 */
@Generated
public class RestClientAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'restClientAutoConfiguration'.
   */
  public static BeanDefinition getRestClientAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RestClientAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(RestClientAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'restClientSsl'.
   */
  private static BeanInstanceSupplier<AutoConfiguredRestClientSsl> getRestClientSslInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AutoConfiguredRestClientSsl>forFactoryMethod(RestClientAutoConfiguration.class, "restClientSsl", ResourceLoader.class, ObjectProvider.class, ObjectProvider.class, SslBundles.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration", RestClientAutoConfiguration.class).restClientSsl(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'restClientSsl'.
   */
  public static BeanDefinition getRestClientSslBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AutoConfiguredRestClientSsl.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getRestClientSslInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'restClientBuilderConfigurer'.
   */
  private static BeanInstanceSupplier<RestClientBuilderConfigurer> getRestClientBuilderConfigurerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RestClientBuilderConfigurer>forFactoryMethod(RestClientAutoConfiguration.class, "restClientBuilderConfigurer", ResourceLoader.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration", RestClientAutoConfiguration.class).restClientBuilderConfigurer(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'restClientBuilderConfigurer'.
   */
  public static BeanDefinition getRestClientBuilderConfigurerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RestClientBuilderConfigurer.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getRestClientBuilderConfigurerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'restClientBuilder'.
   */
  private static BeanInstanceSupplier<RestClient.Builder> getRestClientBuilderInstanceSupplier() {
    return BeanInstanceSupplier.<RestClient.Builder>forFactoryMethod(RestClientAutoConfiguration.class, "restClientBuilder", RestClientBuilderConfigurer.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration", RestClientAutoConfiguration.class).restClientBuilder(args.get(0)));
  }

  /**
   * Get the bean definition for 'restClientBuilder'.
   */
  public static BeanDefinition getRestClientBuilderBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RestClient.Builder.class);
    beanDefinition.setScope("prototype");
    beanDefinition.setFactoryBeanName("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration");
    beanDefinition.setInstanceSupplier(getRestClientBuilderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link RestClientAutoConfiguration.HttpMessageConvertersConfiguration}.
   */
  @Generated
  public static class HttpMessageConvertersConfiguration {
    /**
     * Get the bean definition for 'httpMessageConvertersConfiguration'.
     */
    public static BeanDefinition getHttpMessageConvertersConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(RestClientAutoConfiguration.HttpMessageConvertersConfiguration.class);
      beanDefinition.setInstanceSupplier(RestClientAutoConfiguration.HttpMessageConvertersConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'httpMessageConvertersRestClientCustomizer'.
     */
    private static BeanInstanceSupplier<HttpMessageConvertersRestClientCustomizer> getHttpMessageConvertersRestClientCustomizerInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<HttpMessageConvertersRestClientCustomizer>forFactoryMethod(RestClientAutoConfiguration.HttpMessageConvertersConfiguration.class, "httpMessageConvertersRestClientCustomizer", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration$HttpMessageConvertersConfiguration", RestClientAutoConfiguration.HttpMessageConvertersConfiguration.class).httpMessageConvertersRestClientCustomizer(args.get(0)));
    }

    /**
     * Get the bean definition for 'httpMessageConvertersRestClientCustomizer'.
     */
    public static BeanDefinition getHttpMessageConvertersRestClientCustomizerBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpMessageConvertersRestClientCustomizer.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration$HttpMessageConvertersConfiguration");
      beanDefinition.setInstanceSupplier(getHttpMessageConvertersRestClientCustomizerInstanceSupplier());
      return beanDefinition;
    }
  }
}
