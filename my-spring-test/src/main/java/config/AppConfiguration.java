package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
// 开启 @Async 异步方法注解
@EnableAsync
// 开启 AOP 代理
@EnableAspectJAutoProxy
@ComponentScan("org.example")
//@Import(BeanConfiguration.class)
public class AppConfiguration {

}