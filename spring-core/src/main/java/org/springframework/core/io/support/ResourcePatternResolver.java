/*
 * Copyright 2002-2007 the original author or authors.
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

package org.springframework.core.io.support;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/*
	ResourcePatternResolver是Spring框架中的一个接口，扩展自ResourceLoader，用于解析资源模式，支持通过模式匹配检索多个资源。
	其中，常见的实现类是PathMatchingResourcePatternResolver，它通过类路径、文件系统或URL等多种资源位置，能够根据给定的资源模式获取匹配的资源。
    通过调用getResources(String locationPattern)方法，您可以使用包含通配符的资源模式，例如classpath*:com/example/**.xml
    来获取满足条件的资源数组。这提供了一种灵活的机制，使得在应用程序中能够方便地加载和处理符合特定模式的资源文件，如配置文件、模板文件等
*/
/**
 * Strategy interface for resolving a location pattern (for example,
 * an Ant-style path pattern) into Resource objects.
 *
 * <p>This is an extension to the {@link org.springframework.core.io.ResourceLoader}
 * interface. A passed-in ResourceLoader (for example, an
 * {@link org.springframework.context.ApplicationContext} passed in via
 * {@link org.springframework.context.ResourceLoaderAware} when running in a context)
 * can be checked whether it implements this extended interface too.
 *
 * <p>{@link PathMatchingResourcePatternResolver} is a standalone implementation
 * that is usable outside an ApplicationContext, also used by
 * {@link ResourceArrayPropertyEditor} for populating Resource array bean properties.
 *
 * <p>Can be used with any sort of location pattern (e.g. "/WEB-INF/*-context.xml"):
 * Input patterns have to match the strategy implementation. This interface just
 * specifies the conversion method rather than a specific pattern format.
 *
 * <p>This interface also suggests a new resource prefix "classpath*:" for all
 * matching resources from the class path. Note that the resource location is
 * expected to be a path without placeholders in this case (e.g. "/beans.xml");
 * JAR files or classes directories can contain multiple files of the same name.
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 * @see org.springframework.core.io.Resource
 * @see org.springframework.core.io.ResourceLoader
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.context.ResourceLoaderAware
 */
public interface ResourcePatternResolver extends ResourceLoader {

	/*
	 * 类路径匹配所有资源的伪 URL 前缀："classpath*:"
	 * 这与 ResourceLoader 的类路径 URL 前缀不同，它检索给定名称（例如 "/beans.xml"）的
	 * 所有匹配资源，例如在所有部署的 JAR 文件的根目录中。
	 * 详见 org.springframework.core.io.ResourceLoader#CLASSPATH_URL_PREFIX
	 */
	/**
	 * Pseudo URL prefix for all matching resources from the class path: "classpath*:"
	 * This differs from ResourceLoader's classpath URL prefix in that it
	 * retrieves all matching resources for a given name (e.g. "/beans.xml"),
	 * for example in the root of all deployed JAR files.
	 * @see org.springframework.core.io.ResourceLoader#CLASSPATH_URL_PREFIX
	 */
	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	/*
	 * 将给定的位置模式解析为 Resource 对象。
	 * 应尽可能避免指向相同物理资源的重叠资源条目。结果应具有集合语义。
	 * @param locationPattern 要解析的位置模式
	 * @return 相应的 Resource 对象数组
	 * @throws IOException 如果发生 I/O 错误
	 */
	/**
	 * Resolve the given location pattern into Resource objects.
	 * <p>Overlapping resource entries that point to the same physical
	 * resource should be avoided, as far as possible. The result should
	 * have set semantics.
	 * @param locationPattern the location pattern to resolve
	 * @return the corresponding Resource objects
	 * @throws IOException in case of I/O errors
	 */
	Resource[] getResources(String locationPattern) throws IOException;

}
