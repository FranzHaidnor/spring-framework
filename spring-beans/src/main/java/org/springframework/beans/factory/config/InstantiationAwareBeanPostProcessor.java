/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.lang.Nullable;

/*
   Instantiation   实例化；例示；具现化
   Aware  adj.意识到；知道；明白；发现

 * InstantiationAwareBeanPostProcessor 是 Spring 框架中的一个接口，用于在 bean 实例化之前和之后进行自定义处理。
 *
 * Spring 容器在实例化 bean 对象时，会通过一系列的 BeanPostProcessor 进行处理，其中 InstantiationAwareBeanPostProcessor 是其中之一。它扩展了 BeanPostProcessor 接口，并添加了一些额外的回调方法，用于在 bean 实例化的不同阶段进行处理。
 *
 * 具体来说，InstantiationAwareBeanPostProcessor 提供了以下几个方法：
 *
 * 1. postProcessBeforeInstantiation()：在 bean 实例化之前调用，可以返回一个自定义的 bean 实例，用于替代默认的实例化过程。
 * 2. postProcessAfterInstantiation()：在 bean 实例化之后调用，可以对实例进行自定义初始化操作。
 * 3. postProcessPropertyValues()：在 bean 的属性注入之前调用，可以修改要注入的属性值或者验证属性的有效性。
 * 4. postProcessBeforeInitialization()：在 bean 的初始化方法（例如 InitializingBean 接口的 afterPropertiesSet() 方法或者自定义的 init 方法）之前调用，可以对 bean 进行自定义的初始化操作。
 * 5. postProcessAfterInitialization()：在 bean 的初始化方法之后调用，可以对 bean 进行自定义的后处理操作。
 *
 * 通过实现 InstantiationAwareBeanPostProcessor 接口，并重写这些方法，开发者可以在 bean 实例化的各个阶段进行自定义处理，灵活地修改或增强 bean 的行为。
 */
/**
 * Subinterface of {@link BeanPostProcessor} that adds a before-instantiation callback,
 * and a callback after instantiation but before explicit properties are set or
 * autowiring occurs.
 *
 * <p>Typically used to suppress default instantiation for specific target beans,
 * for example to create proxies with special TargetSources (pooling targets,
 * lazily initializing targets, etc), or to implement additional injection strategies
 * such as field injection.
 *
 * <p><b>NOTE:</b> This interface is a special purpose interface, mainly for
 * internal use within the framework. It is recommended to implement the plain
 * {@link BeanPostProcessor} interface as far as possible, or to derive from
 * {@link InstantiationAwareBeanPostProcessorAdapter} in order to be shielded
 * from extensions to this interface.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @since 1.2
 * @see org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#setCustomTargetSourceCreators
 * @see org.springframework.aop.framework.autoproxy.target.LazyInitTargetSourceCreator
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

	/**
	 * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
	 * The returned bean object may be a proxy to use instead of the target bean,
	 * effectively suppressing default instantiation of the target bean.
	 * <p>If a non-null object is returned by this method, the bean creation process
	 * will be short-circuited. The only further processing applied is the
	 * {@link #postProcessAfterInitialization} callback from the configured
	 * {@link BeanPostProcessor BeanPostProcessors}.
	 * <p>This callback will be applied to bean definitions with their bean class,
	 * as well as to factory-method definitions in which case the returned bean type
	 * will be passed in here.
	 * <p>Post-processors may implement the extended
	 * {@link SmartInstantiationAwareBeanPostProcessor} interface in order
	 * to predict the type of the bean object that they are going to return here.
	 * <p>The default implementation returns {@code null}.
	 * @param beanClass the class of the bean to be instantiated
	 * @param beanName the name of the bean
	 * @return the bean object to expose instead of a default instance of the target bean,
	 * or {@code null} to proceed with default instantiation
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see #postProcessAfterInstantiation
	 * @see org.springframework.beans.factory.support.AbstractBeanDefinition#getBeanClass()
	 * @see org.springframework.beans.factory.support.AbstractBeanDefinition#getFactoryMethodName()
	 */
	@Nullable
	default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	/*
	 * 在 Bean 实例化之后，通过构造函数或工厂方法，但在 Spring 属性填充（来自显式属性或自动连接）发生之前执行操作。
	 * 这是在 Spring 的自动连线启动之前在给定的 Bean 实例上执行自定义字段注入的理想回调。
	 * 默认实现返回 true.
	 * 形参:
	 * bean – 已创建的 Bean 实例，其属性尚未设置 beanName – 豆子的名字
	 * 返回值:
	 * true 是否应该在 Bean 上设置属性; false 是否应跳过属性填充。正常实现应返回 true。返回 false 还将阻止在此 Bean 实例上调用任何后续 InstantiationAwareBeanPostProcessor 实例。
	 * 抛出:
	 * BeansException – 万一出现错误
	 * 请参阅:
	 * postProcessBeforeInstantiation
	 */
	/**
	 * Perform operations after the bean has been instantiated, via a constructor or factory method,
	 * but before Spring property population (from explicit properties or autowiring) occurs.
	 * <p>This is the ideal callback for performing custom field injection on the given bean
	 * instance, right before Spring's autowiring kicks in.
	 * <p>The default implementation returns {@code true}.
	 * @param bean the bean instance created, with properties not having been set yet
	 * @param beanName the name of the bean
	 * @return {@code true} if properties should be set on the bean; {@code false}
	 * if property population should be skipped. Normal implementations should return {@code true}.
	 * Returning {@code false} will also prevent any subsequent InstantiationAwareBeanPostProcessor
	 * instances being invoked on this bean instance.
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see #postProcessBeforeInstantiation
	 */
	default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		return true;
	}

	/*
	 * 在工厂将给定的属性值应用于给定的 Bean 之前，对它们进行后处理，而无需任何属性描述符。
	 * 如果实现提供自定义postProcessPropertyValues实现，则应返回（默认值），pvs否则应返回null。在此接口的未来版本中（已删除postProcessPropertyValues），默认实现将直接返回给定pvs的原样。
	 * 形参:
	 * pvs – 工厂将要应用的属性值（从不 null） bean – 已创建但尚未设置其属性的 Bean 实例 beanName – 豆子的名字
	 * 返回值:
	 * 要应用于给定 Bean 的实际属性值（可以是传入的 PropertyValues 实例），或者 null 继续处理现有属性，但具体继续调用 postProcessPropertyValues （需要对当前 Bean 类初始化 PropertyDescriptors）
	 * 抛出:
	 * BeansException – 万一出现错误
	 */
	/**
	 * Post-process the given property values before the factory applies them
	 * to the given bean, without any need for property descriptors.
	 * <p>Implementations should return {@code null} (the default) if they provide a custom
	 * {@link #postProcessPropertyValues} implementation, and {@code pvs} otherwise.
	 * In a future version of this interface (with {@link #postProcessPropertyValues} removed),
	 * the default implementation will return the given {@code pvs} as-is directly.
	 * @param pvs the property values that the factory is about to apply (never {@code null})
	 * @param bean the bean instance created, but whose properties have not yet been set
	 * @param beanName the name of the bean
	 * @return the actual property values to apply to the given bean (can be the passed-in
	 * PropertyValues instance), or {@code null} which proceeds with the existing properties
	 * but specifically continues with a call to {@link #postProcessPropertyValues}
	 * (requiring initialized {@code PropertyDescriptor}s for the current bean class)
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @since 5.1
	 * @see #postProcessPropertyValues
	 */
	@Nullable
	default PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
			throws BeansException {

		return null;
	}

	/**
	 * Post-process the given property values before the factory applies them
	 * to the given bean. Allows for checking whether all dependencies have been
	 * satisfied, for example based on a "Required" annotation on bean property setters.
	 * <p>Also allows for replacing the property values to apply, typically through
	 * creating a new MutablePropertyValues instance based on the original PropertyValues,
	 * adding or removing specific values.
	 * <p>The default implementation returns the given {@code pvs} as-is.
	 * @param pvs the property values that the factory is about to apply (never {@code null})
	 * @param pds the relevant property descriptors for the target bean (with ignored
	 * dependency types - which the factory handles specifically - already filtered out)
	 * @param bean the bean instance created, but whose properties have not yet been set
	 * @param beanName the name of the bean
	 * @return the actual property values to apply to the given bean (can be the passed-in
	 * PropertyValues instance), or {@code null} to skip property population
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see #postProcessProperties
	 * @see org.springframework.beans.MutablePropertyValues
	 * @deprecated as of 5.1, in favor of {@link #postProcessProperties(PropertyValues, Object, String)}
	 */
	@Deprecated
	@Nullable
	default PropertyValues postProcessPropertyValues(
			PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {

		return pvs;
	}

}
