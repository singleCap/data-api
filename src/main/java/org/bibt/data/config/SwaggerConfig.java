package org.bibt.data.config;

import org.bibt.data.util.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


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
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name(Constant.TOKEN_HEADER_NAME).description("token").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.bibt.data"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters);
    }

    /**
     * Swagger2文档网站的信息
     *
     * @return ApiInfo Swagger2文档网站的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BIBT Data API Document")
                .description("This is a restful api document of BIBT Data.")
                .version("1.0")
                .build();
    }
}
