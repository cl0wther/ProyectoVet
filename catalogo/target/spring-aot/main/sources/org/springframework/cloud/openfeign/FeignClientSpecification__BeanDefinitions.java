package org.springframework.cloud.openfeign;

import java.lang.Class;
import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link FeignClientSpecification}.
 */
@Generated
public class FeignClientSpecification__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'default.com.provet.catalogo.CatalogoApplication.FeignClientSpecification'.
   */
  private static BeanInstanceSupplier<FeignClientSpecification> getFeignClientSpecificationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<FeignClientSpecification>forConstructor(String.class, String.class, Class[].class)
            .withGenerator((registeredBean, args) -> new FeignClientSpecification(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'feignClientSpecification'.
   */
  public static BeanDefinition getFeignClientSpecificationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FeignClientSpecification.class);
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, "default.com.provet.catalogo.CatalogoApplication");
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, "default");
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(2, new String[] {});
    beanDefinition.setInstanceSupplier(getFeignClientSpecificationInstanceSupplier());
    return beanDefinition;
  }
}
