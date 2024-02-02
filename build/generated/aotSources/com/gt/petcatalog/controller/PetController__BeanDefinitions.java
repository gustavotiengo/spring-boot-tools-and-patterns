package com.gt.petcatalog.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PetController}.
 */
@Generated
public class PetController__BeanDefinitions {
  /**
   * Get the bean definition for 'petController'.
   */
  public static BeanDefinition getPetControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PetController.class);
    beanDefinition.setInstanceSupplier(PetController::new);
    return beanDefinition;
  }
}
