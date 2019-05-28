package org.ordersample.customerservice.swagger;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import com.fasterxml.classmate.TypeResolver;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	  public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            	.apis(RequestHandlerSelectors.basePackage("org.ordersample.customerservice"))
	            	.build()
					.apiInfo(apiInfo())
	            	.pathMapping("/")
					.genericModelSubstitutes(ResponseEntity.class, CompletableFuture.class)
	            	.alternateTypeRules(
	                    newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
	                            typeResolver.resolve(WildcardType.class))
	            	)
	            	.useDefaultResponseMessages(false);
	  }

	private ApiInfo apiInfo(){

			ApiInfo apiInfo = new ApiInfo("Customer Service",
				"Customer's description",
				"Version 0.1",
				"terms Of Service Url",
				new Contact("Carlos", "someurl", "c.avendano10@gmail.com"),
				"license", "licenseUrl",
				Collections.emptyList());

		return apiInfo;
	}

	  @Autowired
	  private TypeResolver typeResolver;
}
