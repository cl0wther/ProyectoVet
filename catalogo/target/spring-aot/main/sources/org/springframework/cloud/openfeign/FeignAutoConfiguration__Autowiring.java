package org.springframework.cloud.openfeign;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link FeignAutoConfiguration}.
 */
@Generated
public class FeignAutoConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static FeignAutoConfiguration apply(RegisteredBean registeredBean,
      FeignAutoConfiguration instance) {
    AutowiredFieldValueResolver.forField("configurations").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
