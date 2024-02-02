package org.springframework.boot.test.mock.mockito;

import java.util.Collections;
import java.util.Set;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MockitoPostProcessor}.
 */
@Generated
public class MockitoPostProcessor__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.test.mock.mockito.MockitoPostProcessor'.
   */
  private static BeanInstanceSupplier<MockitoPostProcessor> getMockitoPostProcessorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<MockitoPostProcessor>forConstructor(Set.class)
            .withGenerator((registeredBean, args) -> new MockitoPostProcessor(args.get(0)));
  }

  /**
   * Get the bean definition for 'mockitoPostProcessor'.
   */
  public static BeanDefinition getMockitoPostProcessorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MockitoPostProcessor.class);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, Collections.emptySet());
    beanDefinition.setInstanceSupplier(getMockitoPostProcessorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link MockitoPostProcessor.SpyPostProcessor}.
   */
  @Generated
  public static class SpyPostProcessor {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.test.mock.mockito.MockitoPostProcessor$SpyPostProcessor'.
     */
    private static BeanInstanceSupplier<MockitoPostProcessor.SpyPostProcessor> getSpyPostProcessorInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<MockitoPostProcessor.SpyPostProcessor>forConstructor(MockitoPostProcessor.class)
              .withGenerator((registeredBean, args) -> new MockitoPostProcessor.SpyPostProcessor(args.get(0)));
    }

    /**
     * Get the bean definition for 'spyPostProcessor'.
     */
    public static BeanDefinition getSpyPostProcessorBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(MockitoPostProcessor.SpyPostProcessor.class);
      beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
      beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, new RuntimeBeanReference("org.springframework.boot.test.mock.mockito.MockitoPostProcessor"));
      beanDefinition.setInstanceSupplier(getSpyPostProcessorInstanceSupplier());
      return beanDefinition;
    }
  }
}
