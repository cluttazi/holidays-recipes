package com.chrisluttazi.holidaysrecipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HolidaysRecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidaysRecipesApplication.class, args);
    }

    // @Bean
    // public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf)
    // {
    // return hemf.getSessionFactory();
    // }
}
