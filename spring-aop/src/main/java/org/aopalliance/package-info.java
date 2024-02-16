/*
https://blog.csdn.net/sun_tantan/article/details/107497476

Advice：代表要织入的逻辑
Joinpoint：连接点，增强逻辑的织入地点

Advice：增强（通知），代表要织入的逻辑
Interceptor：拦截器，代表了以拦截器方式去实现通知
MethodInterceptor：方法拦截器（Spring中提供了实现类）
ConstructorInterceptor：构造器拦截器
 */
/**
 * Spring's variant of the AOP Alliance interfaces.
 */
package org.aopalliance;
