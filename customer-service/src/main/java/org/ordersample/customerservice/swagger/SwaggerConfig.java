package org.ordersample.customerservice.swagger;

import static springfox.documentation.schema.AlternateTypeRules.newRule;
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
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private ServletContext servletContext;

	@Bean
	  public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            	.apis(RequestHandlerSelectors.basePackage("org.ordersample.customerservice"))
	            	.build()
				//.apiInfo(apiInfo())
	            .pathMapping("/")
				/*.host("http://localhost:8080/")
				.pathProvider(new RelativePathProvider(servletContext){
					@Override
					public String getApplicationBasePath(){
						return "/myapi";
					}
				})*/
	            .genericModelSubstitutes(ResponseEntity.class, CompletableFuture.class)
	            .alternateTypeRules(
	                    newRule(typeResolver.resolve(DeferredResult.class,
	                                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
	                            typeResolver.resolve(WildcardType.class))
	            )
	            .useDefaultResponseMessages(false);
	  }

	private ApiInfo apiInfo(){

		ApiInfo apiInfo = new ApiInfo("DE-Insights",
				"Insights' description",
				"Version 0.1",
				"terms Of Service Url",
				"CARLOS",
				"license",
				"license Url");

		return apiInfo;
	}

	  @Autowired
	  private TypeResolver typeResolver;
}
