
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.List;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();

    }
    @Bean
    public OpenAPI openAPI() {
        // Security Scheme 이름
        final String securitySchemeName = "bearerAuth";
        Server devServer = new Server();
        devServer.setUrl("/");

        //운영서버에 따로 띄우기 위해 서버를 추가 가능
        Server prodServer = new Server();
        prodServer.setUrl("운영 URL");

        Info info = new Info()
                .title("Swagger API") // API 문서 제목
                .version("v1.0.0") // API 문서 버전
                .description("스웨거 API"); // API 문서 설명

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .servers(List.of(devServer, prodServer));
    }
    @Bean
    public OpenApiCustomizer globalHeaderCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    Parameter acceptLanguage = new Parameter()
                            .in(ParameterIn.HEADER.toString()) // 헤더로 지정
                            .name("Accept-Language") //헤더 이름
                            .required(false) //필수 아님
                            .schema(new StringSchema()._default("ko")) //기본값 "ko"
                            .description("언어 설정 (예: ko, en, en_US)");
                    operation.addParametersItem(acceptLanguage); //해당 operation에 추가
                })
        );
    }

    @Bean
    public GroupedOpenApi nonApiGroup(OpenApiCustomizer globalHeaderCustomizer) {
        return GroupedOpenApi.builder()
                .group("non-api")   //원하는 그룹 이름
                .pathsToExclude("/api/**")  //api로 시작하는 경로는 제외
                .addOpenApiCustomizer(globalHeaderCustomizer()) //전역 헤더 커스터마이저 연결
                .build();
    }

    //일반 API 그룹
    @Bean
    public GroupedOpenApi generalApi(OpenApiCustomizer globalHeaderCustomizer) {
        return GroupedOpenApi.builder()
                .group("general-api")
                .pathsToMatch("/api/**")    //일반 API 경로
                .addOpenApiCustomizer(globalHeaderCustomizer()) //전역 헤더 커스터마이저 연결
                .build();
    }
}





import static java.awt.SystemColor.info;


public class SwaggerConfig {


    @Bean

}
