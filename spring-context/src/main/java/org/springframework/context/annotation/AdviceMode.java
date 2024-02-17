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

package org.springframework.context.annotation;

// 切面增强模式
// 用于确定是否应应用基于 JDK 代理或基于 AspectJ 编织的建议的枚举。
/**
 * Enumeration used to determine whether JDK proxy-based or
 * AspectJ weaving-based advice should be applied.
 *
 * @author Chris Beams
 * @since 3.1
 * @see org.springframework.scheduling.annotation.EnableAsync#mode()
 * @see org.springframework.scheduling.annotation.AsyncConfigurationSelector#selectImports
 * @see org.springframework.transaction.annotation.EnableTransactionManagement#mode()
 */
public enum AdviceMode {

	/**
	 * JDK proxy-based advice.
	 */
	PROXY,

	/**
	 * AspectJ weaving-based advice.
	 */
	ASPECTJ

}
