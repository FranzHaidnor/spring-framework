/*
 * Copyright 2002-2016 the original author or authors.
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

package org.aopalliance.aop;

/*
 * Advice（通知/增强）
 *
 * Advice有以下几种常见的类型：
 * 1、AspectJMethodBeforeAdvice：前置通知。AspectJ中 before 属性对应的通知（@Before标注的方法会被解析成该通知），在切面方法执行之前执行。
 * 2、AspectJAfterReturningAdvice：后置通知。AspectJ中 afterReturning 属性对应的通知（@AfterReturning 标注的方法会被解析成该通知），在切面方法执行之后执行，如果有异常，则不执行。注意：该通知与AspectJMethodBeforeAdvice对应。
 * 3、AspectJAroundAdvice：环绕通知。AspectJ中 around 属性对应的通知（@Around标注的方法会被解析成该通知），在切面方法执行前后执行。
 * 4、AspectJAfterAdvice：返回通知。AspectJ中 after 属性对应的通知（@After 标注的方法会被解析成该通知），不论是否异常都会执行。
 * 5、AspectJAfterThrowingAdvice：异常通知，AspectJ中 after 属性对应的通知（@AfterThrowing标注的方法会被解析成该通知），在连接点抛出异常后执行。
 */
/**
 * Tag interface for Advice. Implementations can be any type
 * of advice, such as Interceptors.
 *
 * @author Rod Johnson
 * @version $Id: Advice.java,v 1.1 2004/03/19 17:02:16 johnsonr Exp $
 */
public interface Advice {

}
