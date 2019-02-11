package github.chickenbane.mvc.config

import org.springframework.boot.info.GitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    /**
     * Return API info for the build
     */
    @Bean
    fun apiInfo(properties: GitProperties): ApiInfo {
        return ApiInfoBuilder()
                .title("Rapidash MVC")
                .description("Simple spring boot service")
                .version(properties.shortCommitId)
                .build()
    }

    /**
     * Configure Swagger
     */
    @Bean
    fun customImplementation(info: ApiInfo): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("github.chickenbane.mvc.rest"))
                .build()
                .apiInfo(info)
    }

}

@Controller
class HomeController {

    @RequestMapping("/")
    fun index() = "redirect:swagger-ui.html"
}
