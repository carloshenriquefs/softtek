package com.saudemental.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Configuração do Swagger/OpenAPI com suporte a versionamento
 */
@Configuration
public class SwaggerConfig {

    @Value("${api.version:1.0.0}")
    private String apiVersion;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Saúde Mental API")
                        .version("v1 (" + apiVersion + ")")
                        .description("""
                            API REST para gestão de saúde mental no ambiente corporativo.
                            
                            ## Versões Disponíveis
                            - **v1**: Versão atual (STABLE)
                            
                            ## Funcionalidades
                            - Autenticação e autorização com JWT
                            - Avaliações de saúde mental
                            - Registro de humor diário
                            - Recursos de apoio
                            - Auditoria completa de ações
                            - Conformidade com NR-1 e LGPD
                            
                            ## Autenticação
                            Todos os endpoints (exceto /auth/login) requerem autenticação via JWT.
                            
                            1. Faça login em `/api/v1/auth/login`
                            2. Copie o token recebido
                            3. Clique em "Authorize" e cole o token no formato: `Bearer {seu-token}`
                            
                            ## Versionamento
                            A API usa versionamento por URL path:
                            - Base URL: `/api/v1`
                            - Exemplo: `GET /api/v1/assessments`
                            
                            ## Headers de Versão
                            Todas as respostas incluem:
                            - `X-API-Version`: Versão da API
                            - `X-Service-Version`: Versão do serviço
                            """)
                        .contact(new Contact()
                                .name("Equipe de Saúde Mental")
                                .email("saude.mental@empresa.com")
                                .url("https://empresa.com/saude-mental"))
                        .license(new License()
                                .name("Proprietário")
                                .url("https://empresa.com/license")))
                .servers(Arrays.asList(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor Local - Desenvolvimento"),
                        new Server()
                                .url("http://10.0.2.2:" + serverPort)
                                .description("Servidor Android Emulator"),
                        new Server()
                                .url("https://api.saudemental.empresa.com")
                                .description("Servidor de Produção")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT recebido no login")));
    }
}