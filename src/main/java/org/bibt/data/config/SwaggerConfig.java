package org.bibt.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger配置
 *
 * @author ZengFanyong
 * @date 2021/1/22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置一个Docket Bean
     *
     * @return
     *      Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.bibt.data"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Swagger2文档网站的信息
     *
     * @return ApiInfo
     *      Swagger2文档网站的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BIBT Data API Document")
                .description("This is a restful api document of BIBT Data.")
                .version("1.0")
                .build();
    }
}
