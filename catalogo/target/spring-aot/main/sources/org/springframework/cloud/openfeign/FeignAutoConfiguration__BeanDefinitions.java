package org.springframework.cloud.openfeign;

import java.util.HashMap;
import java.util.Map;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;

/**
 * Bean definitions for {@link FeignAutoConfiguration}.
 */
@Generated
public class FeignAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'feignAutoConfiguration'.
   */
  public static BeanDefinition getFeignAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignAutoConfiguration.class);
    InstanceSupplier<FeignAutoConfiguration> instanceSupplier = InstanceSupplier.using(FeignAutoConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(FeignAutoConfiguration__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'feignFeature'.
   */
  private static BeanInstanceSupplier<HasFeatures> getFeignFeatureInstanceSupplier() {
    return BeanInstanceSupplier.<HasFeatures>forFactoryMethod(FeignAutoConfiguration.class, "feignFeature")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.openfeign.FeignAutoConfiguration", FeignAutoConfiguration.class).feignFeature());
  }

  /**
   * Get the bean definition for 'feignFeature'.
   */
  public static BeanDefinition getFeignFeatureBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HasFeatures.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.openfeign.FeignAutoConfiguration");
    beanDefinition.setInstanceSupplier(getFeignFeatureInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Use AOT child context management initialization
   */
  private static FeignClientFactory feignContextAddFeignChildContextInitializer(
      RegisteredBean registeredBean, FeignClientFactory instance) {
    Map<String, Object> initializers = new HashMap<>();
    return instance.withApplicationContextInitializers(initializers);
  }

  /**
   * Get the bean instance supplier for 'feignContext'.
   */
  private static BeanInstanceSupplier<FeignClientFactory> getFeignContextInstanceSupplier() {
    return BeanInstanceSupplier.<FeignClientFactory>forFactoryMethod(FeignAutoConfiguration.class, "feignContext")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.openfeign.FeignAutoConfiguration", FeignAutoConfiguration.class).feignContext());
  }

  /**
   * Get the bean definition for 'feignContext'.
   */
  public static BeanDefinition getFeignContextBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignClientFactory.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.openfeign.FeignAutoConfiguration");
    InstanceSupplier<FeignClientFactory> instanceSupplier = getFeignContextInstanceSupplier();
    instanceSupplier = instanceSupplier.andThen(FeignAutoConfiguration__BeanDefinitions::feignContextAddFeignChildContextInitializer);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link FeignAutoConfiguration.DefaultFeignTargeterConfiguration}.
   */
  @Generated
  public static class DefaultFeignTargeterConfiguration {
    /**
     * Get the bean definition for 'defaultFeignTargeterConfiguration'.
     */
    public static BeanDefinition getDefaultFeignTargeterConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignAutoConfiguration.DefaultFeignTargeterConfiguration.class);
      beanDefinition.setInstanceSupplier(FeignAutoConfiguration.DefaultFeignTargeterConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'feignTargeter'.
     */
    private static BeanInstanceSupplier<Targeter> getFeignTargeterInstanceSupplier() {
      return BeanInstanceSupplier.<Targeter>forFactoryMethod(FeignAutoConfiguration.DefaultFeignTargeterConfiguration.class, "feignTargeter")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.openfeign.FeignAutoConfiguration$DefaultFeignTargeterConfiguration", FeignAutoConfiguration.DefaultFeignTargeterConfiguration.class).feignTargeter());
    }

    /**
     * Get the bean definition for 'feignTargeter'.
     */
    public static BeanDefinition getFeignTargeterBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(Targeter.class);
      beanDefinition.setFactoryBeanName("org.springframework.cloud.openfeign.FeignAutoConfiguration$DefaultFeignTargeterConfiguration");
      beanDefinition.setInstanceSupplier(getFeignTargeterInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link FeignAutoConfiguration.FeignJacksonConfiguration}.
   */
  @Generated
  public static class FeignJacksonConfiguration {
    /**
     * Get the bean definition for 'feignJacksonConfiguration'.
     */
    public static BeanDefinition getFeignJacksonConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignAutoConfiguration.FeignJacksonConfiguration.class);
      beanDefinition.setInstanceSupplier(FeignAutoConfiguration.FeignJacksonConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'pageJacksonModule'.
     */
    private static BeanInstanceSupplier<PageJacksonModule> getPageJacksonModuleInstanceSupplier() {
      return BeanInstanceSupplier.<PageJacksonModule>forFactoryMethod(FeignAutoConfiguration.FeignJacksonConfiguration.class, "pageJacksonModule")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.openfeign.FeignAutoConfiguration$FeignJacksonConfiguration", FeignAutoConfiguration.FeignJacksonConfiguration.class).pageJacksonModule());
    }

    /**
     * Get the bean definition for 'pageJacksonModule'.
     */
    public static BeanDefinition getPageJacksonModuleBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(PageJacksonModule.class);
      beanDefinition.setFactoryBeanName("org.springframework.cloud.openfeign.FeignAutoConfiguration$FeignJacksonConfiguration");
      beanDefinition.setInstanceSupplier(getPageJacksonModuleInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'sortModule'.
     */
    private static BeanInstanceSupplier<SortJacksonModule> getSortModuleInstanceSupplier() {
      return BeanInstanceSupplier.<SortJacksonModule>forFactoryMethod(FeignAutoConfiguration.FeignJacksonConfiguration.class, "sortModule")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.openfeign.FeignAutoConfiguration$FeignJacksonConfiguration", FeignAutoConfiguration.FeignJacksonConfiguration.class).sortModule());
    }

    /**
     * Get the bean definition for 'sortModule'.
     */
    public static BeanDefinition getSortModuleBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(SortJacksonModule.class);
      beanDefinition.setFactoryBeanName("org.springframework.cloud.openfeign.FeignAutoConfiguration$FeignJacksonConfiguration");
      beanDefinition.setInstanceSupplier(getSortModuleInstanceSupplier());
      return beanDefinition;
    }
  }
}
