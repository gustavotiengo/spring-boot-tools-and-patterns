package com.gt.petcatalog;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link PetcatalogApplication}.
 */
@Generated
public class PetcatalogApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'petcatalogApplication'.
   */
  public static BeanDefinition getPetcatalogApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PetcatalogApplication.class);
    beanDefinition.setTargetType(PetcatalogApplication.class);
    ConfigurationClassUtils.initializeConfigurationClass(PetcatalogApplication.class);
    beanDefinition.setInstanceSupplier(PetcatalogApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
