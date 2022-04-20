package com.dmdev.spring;

import com.dmdev.spring.config.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan // enable scan of @ConfigurationProperties to map props file to new bean of class annotated by @ConfigurationProperties
public class ApplicationRunner {

    public static void main(String[] args) {

        var context = SpringApplication.run(ApplicationRunner.class, args);
         System.out.println(context.getBeanDefinitionCount());
        System.out.println(context.getBean("pool1"));
        System.out.println(context.getBean(DatabaseProperties.class));

////        var container = new Container();
////        var userService = container.get(UserService.class);
//
//        String value = "hello";
//        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
//        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));
//
////        try (var context = new ClassPathXmlApplicationContext("application.xml")) {
//        try (var context = new AnnotationConfigApplicationContext()) {
//            context.register(ApplicationConfiguration.class);
//            context.getEnvironment().setActiveProfiles("web", "prod");
//            context.refresh();
//
////        System.out.println(context.getBean(ConnectionPool.class));
////            System.out.println(context.getBean("pool2", ConnectionPool.class));
//
////            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
//            var companyService = context.getBean("companyService", CompanyService.class);
//            System.out.println(companyService.findById(1));
//        }
    }
}
