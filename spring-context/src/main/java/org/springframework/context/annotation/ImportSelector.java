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

import java.util.function.Predicate;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/*
 * 由类型实现的接口，这些类型根据给定的选择条件（通常是一个或多个注释属性）确定应导入哪些 @Configuration 类。
 * 可以 ImportSelector 实现以下 Aware 任何接口，并且它们各自的方法将在之前 selectImports调用：
 * EnvironmentAware
 * BeanFactoryAware
 * BeanClassLoaderAware
 * ResourceLoaderAware
 *
 * 或者，该类可以为单个构造函数提供以下一个或多个受支持的参数类型：
 * Environment
 * BeanFactory
 * ClassLoader
 * ResourceLoader
 *
 * ImportSelector 实现的处理方式通常与常规 @Import 注解相同，但是，也可以推迟导入的选择，直到处理完所有 @Configuration 类（有关详细信息，请参阅 DeferredImportSelector ）。
 */
/**
 * Interface to be implemented by types that determine which @{@link Configuration}
 * class(es) should be imported based on a given selection criteria, usually one or
 * more annotation attributes.
 *
 * <p>An {@link ImportSelector} may implement any of the following
 * {@link org.springframework.beans.factory.Aware Aware} interfaces,
 * and their respective methods will be called prior to {@link #selectImports}:
 * <ul>
 * <li>{@link org.springframework.context.EnvironmentAware EnvironmentAware}</li>
 * <li>{@link org.springframework.beans.factory.BeanFactoryAware BeanFactoryAware}</li>
 * <li>{@link org.springframework.beans.factory.BeanClassLoaderAware BeanClassLoaderAware}</li>
 * <li>{@link org.springframework.context.ResourceLoaderAware ResourceLoaderAware}</li>
 * </ul>
 *
 * <p>Alternatively, the class may provide a single constructor with one or more of
 * the following supported parameter types:
 * <ul>
 * <li>{@link org.springframework.core.env.Environment Environment}</li>
 * <li>{@link org.springframework.beans.factory.BeanFactory BeanFactory}</li>
 * <li>{@link java.lang.ClassLoader ClassLoader}</li>
 * <li>{@link org.springframework.core.io.ResourceLoader ResourceLoader}</li>
 * </ul>
 *
 * <p>{@code ImportSelector} implementations are usually processed in the same way
 * as regular {@code @Import} annotations, however, it is also possible to defer
 * selection of imports until all {@code @Configuration} classes have been processed
 * (see {@link DeferredImportSelector} for details).
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see DeferredImportSelector
 * @see Import
 * @see ImportBeanDefinitionRegistrar
 * @see Configuration
 */
public interface ImportSelector {

	/*
	 * 根据导入的 @Configuration 类选择AnnotationMetadata并返回应导入的类的名称
	 */
	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 * @return the class names, or an empty array if none
	 */
	String[] selectImports(AnnotationMetadata importingClassMetadata);


	/*
	 * 返回排除的类，是一个类过滤器，但是这个方法被default注解了，可见Spring公司也知道，这个基本没人用
	 *
	 * 返回一个谓词，用于从导入候选项中排除类，以传递方式应用于通过此选择器的导入找到的所有类。
	 * 如果此谓词返回 true 给定的完全限定类名，则该类将不被视为导入的配置类，从而绕过类文件加载和元数据自省。
	 */
	/**
	 * Return a predicate for excluding classes from the import candidates, to be
	 * transitively applied to all classes found through this selector's imports.
	 * <p>If this predicate returns {@code true} for a given fully-qualified
	 * class name, said class will not be considered as an imported configuration
	 * class, bypassing class file loading as well as metadata introspection.
	 * @return the filter predicate for fully-qualified candidate class names
	 * of transitively imported configuration classes, or {@code null} if none
	 * @since 5.2.4
	 */
	@Nullable
	default Predicate<String> getExclusionFilter() {
		return null;
	}

// Predicate 是一个接口, 可以用于判断是否符合条件
//	Predicate<String> predicate = new Predicate<String>() {
//		@Override
//		public boolean test(String string) {
//			return false;
//		}
//	};

}
