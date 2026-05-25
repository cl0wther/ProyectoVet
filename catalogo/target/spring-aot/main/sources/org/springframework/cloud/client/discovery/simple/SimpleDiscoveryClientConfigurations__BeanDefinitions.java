package org.springframework.cloud.client.discovery.simple;

import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;

/**
 * Bean definitions for {@link SimpleDiscoveryClientConfigurations}.
 */
@Generated
public class SimpleDiscoveryClientConfigurations__BeanDefinitions {
  /**
   * Bean definitions for {@link SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration}.
   */
  @Generated
  public static class WebApplicationSimpleDiscoveryClientConfiguration {
    /**
     * Get the bean instance supplier for 'org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientConfigurations$WebApplicationSimpleDiscoveryClientConfiguration'.
     */
    private static BeanInstanceSupplier<SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration> getWebApplicationSimpleDiscoveryClientConfigurationInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration>forConstructor(InetUtils.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> new SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration(args.get(0), args.get(1)));
    }

    /**
     * Get the bean definition for 'webApplicationSimpleDiscoveryClientConfiguration'.
     */
    public static BeanDefinition getWebApplicationSimpleDiscoveryClientConfigurationBeanDefinition(
        ) {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration.class);
      beanDefinition.setInstanceSupplier(getWebApplicationSimpleDiscoveryClientConfigurationInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'simpleDiscoveryClient'.
     */
    private static BeanInstanceSupplier<DiscoveryClient> getSimpleDiscoveryClientInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<DiscoveryClient>forFactoryMethod(SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration.class, "simpleDiscoveryClient", SimpleDiscoveryProperties.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientConfigurations$WebApplicationSimpleDiscoveryClientConfiguration", SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration.class).simpleDiscoveryClient(args.get(0)));
    }

    /**
     * Get the bean definition for 'simpleDiscoveryClient'.
     */
    public static BeanDefinition getSimpleDiscoveryClientBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(DiscoveryClient.class);
      beanDefinition.setFactoryBeanName("org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientConfigurations$WebApplicationSimpleDiscoveryClientConfiguration");
      beanDefinition.setInstanceSupplier(getSimpleDiscoveryClientInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'simpleDiscoveryProperties'.
     */
    private static BeanInstanceSupplier<SimpleDiscoveryProperties> getSimpleDiscoveryPropertiesInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<SimpleDiscoveryProperties>forFactoryMethod(SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration.class, "simpleDiscoveryProperties", String.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientConfigurations$WebApplicationSimpleDiscoveryClientConfiguration", SimpleDiscoveryClientConfigurations.WebApplicationSimpleDiscoveryClientConfiguration.class).simpleDiscoveryProperties(args.get(0)));
    }

    /**
     * Get the bean definition for 'simpleDiscoveryProperties'.
     */
    public static BeanDefinition getSimpleDiscoveryPropertiesBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(SimpleDiscoveryProperties.class);
      beanDefinition.setFactoryBeanName("org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientConfigurations$WebApplicationSimpleDiscoveryClientConfiguration");
      beanDefinition.setInstanceSupplier(getSimpleDiscoveryPropertiesInstanceSupplier());
      return beanDefinition;
    }
  }
}
