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

package org.springframework.context.annotation;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * 独立的应用程序上下文，接受<em>组件类<em>作为输入——特别是 {@link Configuration @Configuration} 注释的类，
 * 但也接受普通的 {@link org.springframework.stereotype.Component @Component} 类型和使用 {@code javax.inject} 注解的 JSR-330 兼容类。
 * <p>
 * 支持3种注解类型：
 * 1.@Configuration
 * 2.@Component
 * 3.{@code javax.inject} 包下的注解
 * <p>
 * Standalone application context, accepting <em>component classes</em> as input &mdash;
 * in particular {@link Configuration @Configuration}-annotated classes, but also plain
 * {@link org.springframework.stereotype.Component @Component} types and JSR-330 compliant
 * classes using {@code javax.inject} annotations.
 * <p>
 * 允许使用 {@link #register(Class...)} 逐个注册类，以及使用 {@link #scan(String...)}. 进行类路径扫描
 *
 * <p>Allows for registering classes one by one using {@link #register(Class...)}
 * as well as for classpath scanning using {@link #scan(String...)}.
 *
 * <p>如果有多个{@code @Configuration}类，后面的类中定义的{@link Bean @Bean}方法将覆盖前面的类中定义的方法。
 * 可以利用这一点通过额外的 {@code @Configuration} 类故意覆盖某些 bean 定义。
 * <p>In case of multiple {@code @Configuration} classes, {@link Bean @Bean} methods
 * defined in later classes will override those defined in earlier classes. This can
 * be leveraged to deliberately override certain bean definitions via an extra
 * {@code @Configuration} class.
 *
 * <p>See {@link Configuration @Configuration}'s javadoc for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @see #register
 * @see #scan
 * @see AnnotatedBeanDefinitionReader
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.support.GenericXmlApplicationContext
 * @since 3.0
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

	// 用于传入 Class 扫描注册 BeanDefinition
	// Class<?>... componentClasses
	/**
	 * 带有注解的 bean定义读取器
	 */
	private final AnnotatedBeanDefinitionReader reader;

	// 用于传入类路径扫描注册 BeanDefinition
	// String... basePackages
	/**
	 * 类路径 bean定义读取器
	 */
	private final ClassPathBeanDefinitionScanner scanner;

	/*
	 * 创建一个需要填充的新AnnotationConfigApplicationContext
	 * 通过{@link #register}调用然后手动{@linkplain #refresh刷新}。
	 */
	/**
	 * Create a new AnnotationConfigApplicationContext that needs to be populated
	 * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
	 */
	public AnnotationConfigApplicationContext() {
		super();
		// 创建注解 bean 定义读取器， 参数 BeanDefinitionRegistry (bean 定义注册器)  参数是自己
		// 注册 BeanDefinitionRegistryPostProcessor
		this.reader = new AnnotatedBeanDefinitionReader(this);
		// 创建类路径 bean 定义扫描器， 参数 BeanDefinitionRegistry (bean 定义注册器) 参数是自己
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext with the given DefaultListableBeanFactory.
	 *
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 */
	public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		// 创建注解的 bean 定义读取器
		this.reader = new AnnotatedBeanDefinitionReader(this);
		// 创建类路径bean定义扫描器
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/*
	 * 创建一个新的AnnotationConfigApplicationContext，派生 definitions
	 * 从给定的组件类中自动刷新上下文。
	 * @param componentClasses 一个或多个组件类 - 例如，
	 */
	/**
	 * Create a new AnnotationConfigApplicationContext, deriving bean definitions
	 * from the given component classes and automatically refreshing the context.
	 *
	 * @param componentClasses one or more component classes &mdash; for example,
	 *                         {@link Configuration @Configuration} classes
	 */
	public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
		// 执行其无参构造方法
		this();
		// 注册 BeanDefinition
		this.register(componentClasses);
		// 刷新
		super.refresh();
	}

	/**
	 * 创建新的 AnnotationConfigApplicationContext，扫描给定包中的组件，为这些组件注册 Bean 定义，并自动刷新上下文。
	 * <p>
	 * Create a new AnnotationConfigApplicationContext, scanning for components
	 * in the given packages, registering bean definitions for those components,
	 * and automatically refreshing the context.
	 *
	 * @param basePackages the packages to scan for component classes
	 *                     要扫描组件类的包
	 */
	public AnnotationConfigApplicationContext(String... basePackages) {
		// 执行默认构造方法 初始化属性 AnnotatedBeanDefinitionReader ClassPathBeanDefinitionScanner
		this();
		// 扫描包 注册 BeanDefinition
		this.scan(basePackages);
		// 执行父抽象类 AbstractApplicationContext 的 refresh() 方法
		super.refresh();
	}


	/**
	 * Propagate the given custom {@code Environment} to the underlying
	 * {@link AnnotatedBeanDefinitionReader} and {@link ClassPathBeanDefinitionScanner}.
	 */
	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(environment);
		this.scanner.setEnvironment(environment);
	}

	/**
	 * Provide a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
	 * and/or {@link ClassPathBeanDefinitionScanner}, if any.
	 * <p>Default is {@link AnnotationBeanNameGenerator}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 *
	 * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
	 * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
	 * @see AnnotationBeanNameGenerator
	 * @see FullyQualifiedAnnotationBeanNameGenerator
	 */
	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.reader.setBeanNameGenerator(beanNameGenerator);
		this.scanner.setBeanNameGenerator(beanNameGenerator);
		getBeanFactory().registerSingleton(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
	}

	/**
	 * Set the {@link ScopeMetadataResolver} to use for registered component classes.
	 * <p>The default is an {@link AnnotationScopeMetadataResolver}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 */
	public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
		this.reader.setScopeMetadataResolver(scopeMetadataResolver);
		this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
	}


	//---------------------------------------------------------------------
	// Implementation of AnnotationConfigRegistry
	//---------------------------------------------------------------------

	/**
	 * Register one or more component classes to be processed.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 *
	 * @param componentClasses one or more component classes &mdash; for example,
	 *                         {@link Configuration @Configuration} classes
	 * @see #scan(String...)
	 * @see #refresh()
	 */
	@Override
	public void register(Class<?>... componentClasses) {
		Assert.notEmpty(componentClasses, "At least one component class must be specified");
		this.reader.register(componentClasses);
	}

	/*
	 * 在指定的基本包中执行扫描。
	 * 请注意, refresh () 必须调用，以便上下文完全处理新类。
	 * 指定的:
	 * 接口 AnnotationConfigRegistry 中的 扫描
	 * 形参:
	 * basePackages -要扫描组件类的包
	 * 请参阅:
	 * 注册(类…)， refresh ()
	 */
	/**
	 * Perform a scan within the specified base packages.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 *
	 * @param basePackages the packages to scan for component classes
	 * @see #register(Class...)
	 * @see #refresh()
	 */
	@Override
	public void scan(String... basePackages) {
		// 校验包路径不能为空
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		// 使用扫描器 ClassPathBeanDefinitionScanner 扫描包路径
		this.scanner.scan(basePackages);
	}


	//---------------------------------------------------------------------
	// Adapt superclass registerBean calls to AnnotatedBeanDefinitionReader
	//---------------------------------------------------------------------

	@Override
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, @Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
		this.reader.registerBean(beanClass, beanName, supplier, customizers);
	}

}
