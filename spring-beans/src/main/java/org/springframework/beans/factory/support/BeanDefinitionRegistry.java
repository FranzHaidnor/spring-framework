/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanDefinitionStoreException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.AliasRegistry;

/*
 * 保存 bean 定义的注册表的接口，例如 RootBeanDefinition
 * 和 ChildBeanDefinition 实例。通常由 BeanFactories 实现
 * 在内部使用 AbstractBeanDefinition 层次结构。
 *
 * <p>这是Spring的bean工厂包中唯一封装的接口
 * Bean 定义的<i>注册</i>。标准 BeanFactory 接口
 * 仅涵盖对<i>完全配置的工厂实例</i>的访问。
 *
 * <p>Spring 的 bean 定义读者希望能够实现它
 * 界面。 Spring 核心中的已知实现者是 DefaultListableBeanFactory
 * 和 GenericApplicationContext。
 */
/**
 * Interface for registries that hold bean definitions, for example RootBeanDefinition
 * and ChildBeanDefinition instances. Typically implemented by BeanFactories that
 * internally work with the AbstractBeanDefinition hierarchy.
 *
 * <p>This is the only interface in Spring's bean factory packages that encapsulates
 * <i>registration</i> of bean definitions. The standard BeanFactory interfaces
 * only cover access to a <i>fully configured factory instance</i>.
 *
 * <p>Spring's bean definition readers expect to work on an implementation of this
 * interface. Known implementors within the Spring core are DefaultListableBeanFactory
 * and GenericApplicationContext.
 *
 * @author Juergen Hoeller
 * @since 26.11.2003
 * @see org.springframework.beans.factory.config.BeanDefinition
 * @see AbstractBeanDefinition
 * @see RootBeanDefinition
 * @see ChildBeanDefinition
 * @see DefaultListableBeanFactory
 * @see org.springframework.context.support.GenericApplicationContext
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 * @see PropertiesBeanDefinitionReader
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

	/*
	 * 在此注册表中注册一个新的 bean 定义。
	 * 必须支持RootBeanDefinition和ChildBeanDefinition。
	 * @param beanName 要注册的bean实例的名称
	 * @param beanDefinition 定义要注册的bean实例
	 * 如果 BeanDefinition 无效，则抛出 BeanDefinitionStoreException
	 * 如果已经存在 BeanDefinition，则抛出@ throws BeanDefinitionOverrideException
	 * 对于指定的bean名称，我们不允许覆盖它
	 * @see GenericBeanDefinition
	 * @see RootBeanDefinition
	 * @see ChildBeanDefinition
	 */
	/**
	 * Register a new bean definition with this registry.
	 * Must support RootBeanDefinition and ChildBeanDefinition.
	 * @param beanName the name of the bean instance to register
	 * @param beanDefinition definition of the bean instance to register
	 * @throws BeanDefinitionStoreException if the BeanDefinition is invalid
	 * @throws BeanDefinitionOverrideException if there is already a BeanDefinition
	 * for the specified bean name and we are not allowed to override it
	 * @see GenericBeanDefinition
	 * @see RootBeanDefinition
	 * @see ChildBeanDefinition
	 */
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException;

	/*
	 * 删除给定名称的 BeanDefinition。
	 * @param beanName 要注册的bean实例的名称
	 * 如果没有这样的bean定义，则@抛出NoSuchBeanDefinitionException
	 */
	/**
	 * Remove the BeanDefinition for the given name.
	 * @param beanName the name of the bean instance to register
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 */
	void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/*
	 * 返回给定 bean 名称的 BeanDefinition。
	 * @param beanName 要查找定义的 bean 名称
	 * @return 给定名称的 BeanDefinition （绝不是 {@code null}）
	 * 如果没有这样的bean定义，则@抛出NoSuchBeanDefinitionException
	 */
	/**
	 * Return the BeanDefinition for the given bean name.
	 * @param beanName name of the bean to find a definition for
	 * @return the BeanDefinition for the given name (never {@code null})
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 */
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/*
	 * 检查此注册表是否包含具有给定名称的 bean 定义。
	 * @param beanName 要查找的 bean 的名称
	 * @return 如果此注册表包含具有给定名称的 bean 定义
	 */
	/**
	 * Check if this registry contains a bean definition with the given name.
	 * @param beanName the name of the bean to look for
	 * @return if this registry contains a bean definition with the given name
	 */
	boolean containsBeanDefinition(String beanName);

	/*
	 * 返回此注册表中定义的所有 bean 的名称。
	 * @return 在此注册表中定义的所有 bean 的名称，
	 * 如果没有定义则为空数组
	 */
	/**
	 * Return the names of all beans defined in this registry.
	 * @return the names of all beans defined in this registry,
	 * or an empty array if none defined
	 */
	String[] getBeanDefinitionNames();

	/*
	 * 返回注册表中定义的bean数量。
	 * @return 注册表中定义的bean数量
	 */
	/**
	 * Return the number of beans defined in the registry.
	 * @return the number of beans defined in the registry
	 */
	int getBeanDefinitionCount();

	/*
	 * 确定给定的 bean 名称是否已在此注册表中使用，
	 * 即是否有本地bean或别名注册在此名称下。
	 * @param beanName 要检查的名称
	 * @return 给定的bean名称是否已在使用中
	 */
	/**
	 * Determine whether the given bean name is already in use within this registry,
	 * i.e. whether there is a local bean or alias registered under this name.
	 * @param beanName the name to check
	 * @return whether the given bean name is already in use
	 */
	boolean isBeanNameInUse(String beanName);

}
