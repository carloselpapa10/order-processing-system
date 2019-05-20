package org.ordersample.customerservice;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.TestPropertySource;


@EnableSpringDataWebSupport
@ComponentScan(basePackages = {"org.ordersample.customerservice.*"})
@EnableJpaRepositories(basePackages = {"org.ordersample.customerservice.*"})
@EntityScan(basePackages = {"org.ordersample.customerservice.*"})
@TestPropertySource("classpath:application-test.yml")
public class BaseTest{

}