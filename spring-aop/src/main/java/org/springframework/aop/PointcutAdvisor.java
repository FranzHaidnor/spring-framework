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

package org.springframework.aop;

/*
 * Spring 的 PointcutAdvisor 接口用于定义切点和通知之间的关系，从而实现对目标对象的增强操作。PointcutAdvisor 接口继承了 Advisor 接口，并新增了两个方法：
 *
 * 1. getPointcut()：获取切点（Pointcut）对象。
 *
 * 在实现 PointcutAdvisor 接口时，需要重写这两个方法，以指定切点和通知的具体实现。一般情况下，我们可以通过实现 Pointcut 接口来定义切点，然后将其与 Advice 对象结合起来，形成一个完整的 PointcutAdvisor 对象。
 *
 * PointcutAdvisor 接口的作用主要有以下几点：
 *
 * 1. 定义切点和通知之间的关系：PointcutAdvisor 接口用于将切点和通知结合在一起，从而定义了在目标对象的特定连接点上执行的通知操作。
 * 2. 实现增强功能：通过 PointcutAdvisor 接口，我们可以实现各种增强功能，比如记录日志、事务管理、安全控制等。
 * 3. 提供灵活性：使用 PointcutAdvisor 接口可以提供更大的灵活性，因为我们可以根据具体的需求来定义切点和通知，实现对目标对象的不同连接点进行不同类型的增强操作。
 *
 * 总之，Spring 的 PointcutAdvisor 接口是 AOP 编程中非常重要的一个接口，它可以实现对目标对象的增强操作，并提供更大的灵活性和可扩展性。
 */
/**
 * Superinterface for all Advisors that are driven by a pointcut.
 * This covers nearly all advisors except introduction advisors,
 * for which method-level matching doesn't apply.
 *
 * @author Rod Johnson
 */
public interface PointcutAdvisor extends Advisor {

	/**
	 * Get the Pointcut that drives this advisor.
	 */
	Pointcut getPointcut();

}
