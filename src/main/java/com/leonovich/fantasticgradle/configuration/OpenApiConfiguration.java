/*
 * Copyright 2022 Aliaksandr Leanovich. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leonovich.fantasticgradle.configuration;

import com.leonovich.fantasticgradle.configuration.properties.ApplicationProperties;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a href="https://swagger.io/">swagger documentation</a>
 *
 * Created : 30/08/2022 09:51
 * Project : fantastic-gradle
 * IDE : IntelliJ IDEA
 *
 * @author Aliaksandr_Leanovich
 * @version 1.0
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class OpenApiConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(applicationProperties.name())
                        .description("Fantastic gradle Open API Documentation")
                        .version(applicationProperties.fantasticGradleVersion())
                        .license(new License().name("Apache License 2.0").url("http://springdoc.org"))
                        .description("SpringShop Wiki Documentation")
                        .contact(
                                new Contact().email("test@test.com")
                                        .url("https://github.com/cyberalexander/fantastic-gradle")
                        )
                );
    }

    /**
     * This custom annotation dedicated to store swagger schema documentation,
     * which is common for multiple APIs in the project.
     */
    @SuppressWarnings("squid:S1710")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public @interface DefaultResponseSchemaTemplate {
        /**
         * An array containing descriptions of potential response payloads, for different media types.
         *
         * @return array of content
         **/
        @AliasFor(annotation = ApiResponse.class, attribute = "content")
        Content[] value() default {};

        /**
         * An array of response headers. Allows additional information to be included with response.
         *
         * @return array of headers
         **/
        @AliasFor(annotation = ApiResponse.class, attribute = "headers")
        Header[] headers() default {};

        /**
         * An array of operation links that can be followed from the response.
         *
         * @return array of links
         **/
        @AliasFor(annotation = ApiResponse.class, attribute = "links")
        Link[] links() default {};

        /**
         * The list of optional extensions.
         *
         * @return an optional array of extensions
         */
        @AliasFor(annotation = ApiResponse.class, attribute = "extensions")
        Extension[] extensions() default {};

        /**
         * A reference to a response defined in components responses.
         *
         * @since 2.0.3
         * @return the reference
         **/
        @AliasFor(annotation = ApiResponse.class, attribute = "ref")
        String ref() default "";

        /**
         * Set to true to resolve the response schema from method return type.
         *
         * @since 2.2.0
         **/
        @AliasFor(annotation = ApiResponse.class, attribute = "useReturnTypeSchema")
        boolean useReturnTypeSchema() default false;
    }
}