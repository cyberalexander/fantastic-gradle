/*
 * Copyright 2022 Aliaksandr Leanovich. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leonovich.fantasticgradle.controller;

import com.leonovich.fantasticgradle.dto.FantasticGradleDto;
import com.leonovich.fantasticgradle.mapper.FantasticGradleModelMapper;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import com.leonovich.fantasticgradle.repository.FantasticRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

/**
 * The 2-nd version of the Controller, which overrides and enhances the API from V1 {@link FantasticGradleController}.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v2/fantastic", produces = {"application/json"})
public class FantasticGradleV2Controller {
    private static final String API_VERSION_SUFFIX = "_V2";

    private FantasticGradleModelMapper mapper;

    private FantasticRepository<FantasticGradle, UUID> repository;

    /**
     * Example of UUID : 4fcfc816-2f2f-420a-b159-4150366e489c or use {@code UUID.randomUUID()}
     *
     * @param fantasticGradleId The unique identifier of fantastic gradle
     * @return The instance of {@link FantasticGradleDto} associated with given {@param fantasticGradleId}
     */
    @GetMapping(path = "{fantasticGradleId}")
    public FantasticGradleDto getFantasticGradle(@PathVariable UUID fantasticGradleId) {
        Optional<FantasticGradle> fantasticGradle = repository.get(fantasticGradleId);
        return fantasticGradle
                .map(result -> {
                    result.setName(result.getName().concat(API_VERSION_SUFFIX));
                    return result;
                })
                .map(result -> mapper.map(result))
                .orElse(null);
    }
}
