/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.beans.factory;

import org.springframework.lang.Nullable;

/*
 * 由 Bean 工厂实现的子接口，可以是层次结构的一部分。
 * 允许以可配置方式设置父级的 Bean 工厂的相应 setParentBeanFactory 方法可以在 ConfigurableBeanFactory 接口中找到。
 *
 * HierarchicalBeanFactory 是 Spring 框架中的一个接口，它继承了 BeanFactory 接口，并添加了一些额外的方法。它的作用是提供了一种层次结构的 BeanFactory 实现方式，用于管理和访问 Spring 容器中的 Bean。
 * 具体来说，HierarchicalBeanFactory 的作用包括：
 * 1. 提供了父子容器的支持：HierarchicalBeanFactory 允许创建一个包含多个子容器的层次结构，每个子容器可以访问父容器中定义的 Bean。这样可以实现不同层次之间的 Bean 继承和共享。
 * 2. 实现 Bean 的查找和访问：HierarchicalBeanFactory 定义了一些方法，如 `getBean()`、`containsBean()`、`containsLocalBean()` 等，用于在容器中查找和访问 Bean。它可以根据父子容器的关系，递归地查找 Bean，直到找到或者整个层次结构遍历完。
 * 3. 支持 Bean 的覆盖：当子容器中存在与父容器中相同名称的 Bean 时，子容器可以覆盖父容器中的 Bean 定义。这样可以在不修改父容器配置的情况下，对特定 Bean 进行个性化的配置。
 * 总而言之，HierarchicalBeanFactory 提供了一种灵活的机制，用于管理和访问层次化的 Bean 定义，方便在 Spring 容器中进行 Bean 的查找、访问和重载。
 */
/**
 * Sub-interface implemented by bean factories that can be part
 * of a hierarchy.
 *
 * <p>The corresponding {@code setParentBeanFactory} method for bean
 * factories that allow setting the parent in a configurable
 * fashion can be found in the ConfigurableBeanFactory interface.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 07.07.2003
 * @see org.springframework.beans.factory.config.ConfigurableBeanFactory#setParentBeanFactory
 */
public interface HierarchicalBeanFactory extends BeanFactory {

	// 返回父 Bean 工厂，或者 null 如果没有。
	/**
	 * Return the parent bean factory, or {@code null} if there is none.
	 */
	@Nullable
	BeanFactory getParentBeanFactory();

	/*
	 * 返回本地 Bean 工厂是否包含给定名称的 Bean，忽略在祖先上下文中定义的 Bean。
	 * 这是 的替代方法 containsBean，忽略来自祖先 Bean 工厂的给定名称的 bean。
	 */
	/**
	 * Return whether the local bean factory contains a bean of the given name,
	 * ignoring beans defined in ancestor contexts.
	 * <p>This is an alternative to {@code containsBean}, ignoring a bean
	 * of the given name from an ancestor bean factory.
	 * @param name the name of the bean to query
	 * @return whether a bean with the given name is defined in the local factory
	 * @see BeanFactory#containsBean
	 */
	boolean containsLocalBean(String name);

}
