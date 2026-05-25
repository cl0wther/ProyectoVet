package com.provet.catalogo.controller;

import com.provet.catalogo.service.MedService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MedController}.
 */
@Generated
public class MedController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'medController'.
   */
  private static BeanInstanceSupplier<MedController> getMedControllerInstanceSupplier() {
    return BeanInstanceSupplier.<MedController>forConstructor(MedService.class)
            .withGenerator((registeredBean, args) -> new MedController(args.get(0)));
  }

  /**
   * Get the bean definition for 'medController'.
   */
  public static BeanDefinition getMedControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MedController.class);
    beanDefinition.setInstanceSupplier(getMedControllerInstanceSupplier());
    return beanDefinition;
  }
}
