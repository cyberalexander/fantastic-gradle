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
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}