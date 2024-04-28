/*
 * Copyright 2002-2020 the original author or authors.
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

import org.springframework.beans.BeansException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

// 根据各种条件获取 bean 的配置清单
/*
 * ListableBeanFactory的主要作用是允许应用程序以编程方式获取和遍历应用程序上下文中的所有bean实例。它提供了以下功能：
 *
 * 1. 列举所有bean实例：ListableBeanFactory定义了getBeanDefinitionCount()和getBeanDefinitionNames()方法，用于获取应用程序上下文中所有bean定义的数量和名称。通过这些方法可以遍历并获取所有bean实例的信息。
 * 2. 按类型获取bean实例：ListableBeanFactory提供了getBeansOfType()和getBeanNamesForType()方法，用于按照指定的类型获取bean实例或bean名称。这样可以方便地根据类型查找和获取需要的bean实例。
 * 3. 获取所有bean的名称：ListableBeanFactory定义了getBeanNamesIterator()方法，用于获取所有bean的名称的迭代器。通过这个方法可以逐个获取所有bean的名称。
 * 4. 判断是否包含指定名称的bean：ListableBeanFactory提供了containsBean()方法，用于判断是否包含指定名称的bean。这样可以方便地检查某个bean是否存在。
 * 需要注意的是，除了getBeanDefinitionCount()和containsBeanDefinition()方法外，ListableBeanFactory的其他方法不适合频繁调用。具体实现可能会比较慢。
 * 总之，ListableBeanFactory是一个扩展了BeanFactory功能的接口，提供了便利的方法来获取和遍历应用程序上下文中的所有bean实例。它可以方便地进行类型查找、获取所有bean的名称等操作，对于需要对bean进行全面管理和操作的场景非常有用。
 */

/**
 * Extension of the {@link BeanFactory} interface to be implemented by bean factories
 * that can enumerate all their bean instances, rather than attempting bean lookup
 * by name one by one as requested by clients. BeanFactory implementations that
 * preload all their bean definitions (such as XML-based factories) may implement
 * this interface.
 *
 * <p>If this is a {@link HierarchicalBeanFactory}, the return values will <i>not</i>
 * take any BeanFactory hierarchy into account, but will relate only to the beans
 * defined in the current factory. Use the {@link BeanFactoryUtils} helper class
 * to consider beans in ancestor factories too.
 *
 * <p>The methods in this interface will just respect bean definitions of this factory.
 * They will ignore any singleton beans that have been registered by other means like
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory}'s
 * {@code registerSingleton} method, with the exception of
 * {@code getBeanNamesForType} and {@code getBeansOfType} which will check
 * such manually registered singletons too. Of course, BeanFactory's {@code getBean}
 * does allow transparent access to such special beans as well. However, in typical
 * scenarios, all beans will be defined by external bean definitions anyway, so most
 * applications don't need to worry about this differentiation.
 *
 * <p><b>NOTE:</b> With the exception of {@code getBeanDefinitionCount}
 * and {@code containsBeanDefinition}, the methods in this interface
 * are not designed for frequent invocation. Implementations may be slow.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see HierarchicalBeanFactory
 * @see BeanFactoryUtils
 * @since 16 April 2001
 */
public interface ListableBeanFactory extends BeanFactory {
	/*
	 * 检查这个Bean工厂是否包含给定名称的Bean定义。
	 * <p>不考虑该工厂可能参与的任何层次结构，
	 * 并忽略通过其他方式注册的任何单例Bean，而不是Bean定义。
	 * @param beanName 要查找的Bean的名称
	 * @return 如果这个Bean工厂包含给定名称的Bean定义，则返回true
	 * @see #containsBean
	 */
	/**
	 * Check if this bean factory contains a bean definition with the given name.
	 * <p>Does not consider any hierarchy this factory may participate in,
	 * and ignores any singleton beans that have been registered by
	 * other means than bean definitions.
	 *
	 * @param beanName the name of the bean to look for
	 * @return if this bean factory contains a bean definition with the given name
	 * @see #containsBean
	 */
	boolean containsBeanDefinition(String beanName);

	/*
	 * 返回工厂中定义的Bean的数量。
	 * <p>不考虑该工厂可能参与的任何层次结构，
	 * 并忽略通过其他方式注册的任何单例Bean，而不是Bean定义。
	 * @return 工厂中定义的Bean的数量
	 */
	/**
	 * Return the number of beans defined in the factory.
	 * <p>Does not consider any hierarchy this factory may participate in,
	 * and ignores any singleton beans that have been registered by
	 * other means than bean definitions.
	 *
	 * @return the number of beans defined in the factory
	 */
	int getBeanDefinitionCount();

	/*
	 * 返回在此工厂中定义的所有Bean的名称。
	 * <p>不考虑该工厂可能参与的任何层次结构，
	 * 并忽略通过其他方式注册的任何单例Bean，而不是Bean定义。
	 * @return 在此工厂中定义的所有Bean的名称，
	 * 如果没有定义则返回一个空数组
	 */
	/**
	 * Return the names of all beans defined in this factory.
	 * <p>Does not consider any hierarchy this factory may participate in,
	 * and ignores any singleton beans that have been registered by
	 * other means than bean definitions.
	 *
	 * @return the names of all beans defined in this factory,
	 * or an empty array if none defined
	 */
	String[] getBeanDefinitionNames();

	/*
	 * 返回与给定类型（包括子类）匹配的所有Bean的名称，
	 * 可以基于Bean定义或者FactoryBeans的getObjectType方法的值进行判断。
	 * <p><b>注意：此方法仅检查顶级Bean。</b>它不会检查可能与指定类型匹配的嵌套Bean。
	 * <p>考虑由FactoryBeans创建的对象，这意味着将初始化FactoryBeans。
	 * 如果由FactoryBean创建的对象不匹配，则将使用原始的FactoryBean本身来匹配类型。
	 * <p>不考虑该工厂可能参与的任何层次结构。
	 * 使用BeanFactoryUtils的beanNamesForTypeIncludingAncestors方法可以在祖先工厂中包含Bean。
	 * <p>注意：不会忽略通过其他方式注册的单例Bean的情况。
	 * <p>这个版本的getBeanNamesForType方法匹配所有类型的Bean，无论是单例、原型还是FactoryBeans。
	 * 在大多数实现中，结果与getBeanNamesForType(type, true, true)的结果相同。
	 * <p>由此方法返回的Bean名称应尽可能地按照后端配置中定义的顺序返回Bean名称。
	 * @param type 要匹配的泛型化类型的类或接口
	 * @return 与给定对象类型（包括子类）匹配的所有Bean的名称（或FactoryBeans创建的对象），
	 * 如果没有找到则返回一个空数组
	 * @since 4.2
	 * @see #isTypeMatch(String, ResolvableType)
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType)
	 */
	/**
	 * Return the names of beans matching the given type (including subclasses),
	 * judging from either bean definitions or the value of {@code getObjectType}
	 * in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
	 * will get initialized. If the object created by the FactoryBean doesn't match,
	 * the raw FactoryBean itself will be matched against the type.
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
	 * be it singletons, prototypes, or FactoryBeans. In most implementations, the
	 * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
	 * <p>Bean names returned by this method should always return bean names <i>in the
	 * order of definition</i> in the backend configuration, as far as possible.
	 *
	 * @param type the generically typed class or interface to match
	 * @return the names of beans (or objects created by FactoryBeans) matching
	 * the given object type (including subclasses), or an empty array if none
	 * @see #isTypeMatch(String, ResolvableType)
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType)
	 * @since 4.2
	 */
	String[] getBeanNamesForType(ResolvableType type);

	/*
	 返回与给定类型匹配的bean的名称（包括子类），根据bean定义或者FactoryBeans的getObjectType的值来判断。
	 <p><b>注意：此方法仅审查顶级bean。</b>它不会检查可能与指定类型匹配的嵌套bean。
	 <p>如果设置了"allowEagerInit"标志，则会考虑由FactoryBeans创建的对象，这意味着FactoryBeans将被初始化。
	 如果由FactoryBean创建的对象不匹配，则会将原始FactoryBean本身与该类型进行匹配。
	 如果未设置"allowEagerInit"，则只会检查原始FactoryBeans（不需要初始化每个FactoryBean）。
	 <p>不考虑此工厂可能参与的任何层次结构。使用BeanFactoryUtils的{@code beanNamesForTypeIncludingAncestors}方法，也可以包含祖先工厂中的bean。
	 <p>注意：不会忽略以其他方式注册的单例bean，而不是通过bean定义。
	 <p>此方法返回的bean名称应尽可能按照后端配置中的<i>定义顺序</i>返回。
	 @param type 要匹配的泛型类型或接口
	 @param includeNonSingletons 是否包括原型或作用域bean，或者仅包括单例（也适用于FactoryBeans）
	 @param allowEagerInit 是否初始化<i>懒加载的单例</i>和<i>由FactoryBeans创建的对象</i>（或通过"factory-bean"引用的工厂方法）进行类型检查。
	 请注意，为了确定其类型，FactoryBeans需要被急切地初始化：因此，请注意将"true"传递给此标志将会初始化FactoryBeans和"factory-bean"引用。
	 @return 匹配给定对象类型（包括子类）的bean的名称（或由FactoryBeans创建的对象的名称），如果没有匹配的，则返回空数组
	 @since 5.2
	 @see FactoryBean#getObjectType
	 @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType, boolean, boolean)
	 */
	/**
	 * Return the names of beans matching the given type (including subclasses),
	 * judging from either bean definitions or the value of {@code getObjectType}
	 * in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
	 * which means that FactoryBeans will get initialized. If the object created by the
	 * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
	 * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
	 * (which doesn't require initialization of each FactoryBean).
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>Bean names returned by this method should always return bean names <i>in the
	 * order of definition</i> in the backend configuration, as far as possible.
	 *
	 * @param type                 the generically typed class or interface to match
	 * @param includeNonSingletons whether to include prototype or scoped beans too
	 *                             or just singletons (also applies to FactoryBeans)
	 * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
	 *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
	 *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
	 *                             eagerly initialized to determine their type: So be aware that passing in "true"
	 *                             for this flag will initialize FactoryBeans and "factory-bean" references.
	 * @return the names of beans (or objects created by FactoryBeans) matching
	 * the given object type (including subclasses), or an empty array if none
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType, boolean, boolean)
	 * @since 5.2
	 */
	String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit);

	/**
	 * Return the names of beans matching the given type (including subclasses),
	 * judging from either bean definitions or the value of {@code getObjectType}
	 * in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
	 * will get initialized. If the object created by the FactoryBean doesn't match,
	 * the raw FactoryBean itself will be matched against the type.
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
	 * be it singletons, prototypes, or FactoryBeans. In most implementations, the
	 * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
	 * <p>Bean names returned by this method should always return bean names <i>in the
	 * order of definition</i> in the backend configuration, as far as possible.
	 *
	 * @param type the class or interface to match, or {@code null} for all bean names
	 * @return the names of beans (or objects created by FactoryBeans) matching
	 * the given object type (including subclasses), or an empty array if none
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, Class)
	 */
	String[] getBeanNamesForType(@Nullable Class<?> type);

	/**
	 * Return the names of beans matching the given type (including subclasses),
	 * judging from either bean definitions or the value of {@code getObjectType}
	 * in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
	 * which means that FactoryBeans will get initialized. If the object created by the
	 * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
	 * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
	 * (which doesn't require initialization of each FactoryBean).
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>Bean names returned by this method should always return bean names <i>in the
	 * order of definition</i> in the backend configuration, as far as possible.
	 *
	 * @param type                 the class or interface to match, or {@code null} for all bean names
	 * @param includeNonSingletons whether to include prototype or scoped beans too
	 *                             or just singletons (also applies to FactoryBeans)
	 * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
	 *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
	 *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
	 *                             eagerly initialized to determine their type: So be aware that passing in "true"
	 *                             for this flag will initialize FactoryBeans and "factory-bean" references.
	 * @return the names of beans (or objects created by FactoryBeans) matching
	 * the given object type (including subclasses), or an empty array if none
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, Class, boolean, boolean)
	 */
	String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);

	/**
	 * Return the bean instances that match the given object type (including
	 * subclasses), judging from either bean definitions or the value of
	 * {@code getObjectType} in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
	 * will get initialized. If the object created by the FactoryBean doesn't match,
	 * the raw FactoryBean itself will be matched against the type.
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>This version of getBeansOfType matches all kinds of beans, be it
	 * singletons, prototypes, or FactoryBeans. In most implementations, the
	 * result will be the same as for {@code getBeansOfType(type, true, true)}.
	 * <p>The Map returned by this method should always return bean names and
	 * corresponding bean instances <i>in the order of definition</i> in the
	 * backend configuration, as far as possible.
	 *
	 * @param type the class or interface to match, or {@code null} for all concrete beans
	 * @return a Map with the matching beans, containing the bean names as
	 * keys and the corresponding bean instances as values
	 * @throws BeansException if a bean could not be created
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class)
	 * @since 1.1.2
	 */
	<T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;

	/**
	 * Return the bean instances that match the given object type (including
	 * subclasses), judging from either bean definitions or the value of
	 * {@code getObjectType} in the case of FactoryBeans.
	 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
	 * check nested beans which might match the specified type as well.
	 * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
	 * which means that FactoryBeans will get initialized. If the object created by the
	 * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
	 * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
	 * (which doesn't require initialization of each FactoryBean).
	 * <p>Does not consider any hierarchy this factory may participate in.
	 * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
	 * to include beans in ancestor factories too.
	 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
	 * by other means than bean definitions.
	 * <p>The Map returned by this method should always return bean names and
	 * corresponding bean instances <i>in the order of definition</i> in the
	 * backend configuration, as far as possible.
	 *
	 * @param type                 the class or interface to match, or {@code null} for all concrete beans
	 * @param includeNonSingletons whether to include prototype or scoped beans too
	 *                             or just singletons (also applies to FactoryBeans)
	 * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
	 *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
	 *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
	 *                             eagerly initialized to determine their type: So be aware that passing in "true"
	 *                             for this flag will initialize FactoryBeans and "factory-bean" references.
	 * @return a Map with the matching beans, containing the bean names as
	 * keys and the corresponding bean instances as values
	 * @throws BeansException if a bean could not be created
	 * @see FactoryBean#getObjectType
	 * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class, boolean, boolean)
	 */
	<T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws BeansException;

	/**
	 * Find all names of beans which are annotated with the supplied {@link Annotation}
	 * type, without creating corresponding bean instances yet.
	 * <p>Note that this method considers objects created by FactoryBeans, which means
	 * that FactoryBeans will get initialized in order to determine their object type.
	 *
	 * @param annotationType the type of annotation to look for
	 *                       (at class, interface or factory method level of the specified bean)
	 * @return the names of all matching beans
	 * @see #findAnnotationOnBean
	 * @since 4.0
	 */
	String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

	/*
	 * 查找所有使用提供的 Annotation 类型进行注释的 bean，返回具有相应 bean 实例的 bean 名称的 Map。
	 * 请注意，此方法考虑由 FactoryBeans 创建的对象，这意味着 FactoryBeans 将被初始化以确定其对象类型。
	 * 形参:
	 * annotationType – 要查找的注解类型（在指定 Bean 的类、接口或工厂方法级别）
	 * 返回值:
	 * 具有匹配 Bean 的映射，包含作为键的 Bean 名称和作为值的相应 Bean 实例
	 * 抛出:
	 * BeansException – 如果无法创建 Bean
	 * 自:
	 * 3.0
	 * 请参阅:
	 * findAnnotationOnBean
	 */
	/**
	 * Find all beans which are annotated with the supplied {@link Annotation} type,
	 * returning a Map of bean names with corresponding bean instances.
	 * <p>Note that this method considers objects created by FactoryBeans, which means
	 * that FactoryBeans will get initialized in order to determine their object type.
	 *
	 * @param annotationType the type of annotation to look for
	 *                       (at class, interface or factory method level of the specified bean)
	 * @return a Map with the matching beans, containing the bean names as
	 * keys and the corresponding bean instances as values
	 * @throws BeansException if a bean could not be created
	 * @see #findAnnotationOnBean
	 * @since 3.0
	 */
	Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;

	/**
	 * Find an {@link Annotation} of {@code annotationType} on the specified bean,
	 * traversing its interfaces and super classes if no annotation can be found on
	 * the given class itself, as well as checking the bean's factory method (if any).
	 *
	 * @param beanName       the name of the bean to look for annotations on
	 * @param annotationType the type of annotation to look for
	 *                       (at class, interface or factory method level of the specified bean)
	 * @return the annotation of the given type if found, or {@code null} otherwise
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBeanNamesForAnnotation
	 * @see #getBeansWithAnnotation
	 * @since 3.0
	 */
	@Nullable
	<A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
			throws NoSuchBeanDefinitionException;

}
