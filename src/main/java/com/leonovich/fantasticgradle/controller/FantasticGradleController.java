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
import com.leonovich.fantasticgradle.mapper.FantasticGradleMapper;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import com.leonovich.fantasticgradle.repository.FantasticRepository;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

/**
 * The controller is aimed to manage {@link FantasticGradleDto} entity.
 */
@CommonsLog
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/fantastic", produces = {"application/json"})
public class FantasticGradleController {

    private FantasticGradleMapper mapper;

    private FantasticRepository<FantasticGradle, UUID> repository;


    /**
     * The API retrieves the instance of {@link FantasticGradleDto} from a persistence storage by its unique identifier.
     * <p>
     * Example of UUID : 4fcfc816-2f2f-420a-b159-4150366e489c or use {@code UUID.randomUUID()}
     *
     * @param fantasticGradleId The unique identifier of fantastic gradle
     * @return The instance of {@link FantasticGradleDto} associated with given {@param fantasticGradleId}
     */
    @GetMapping(path = "{fantasticGradleId}")
    public FantasticGradleDto getFantasticGradle(@PathVariable @NotBlank UUID fantasticGradleId) {
        log.info(String.format("GET /api/v1/fantastic/%s API invoked.", fantasticGradleId));
        Optional<FantasticGradle> fantasticGradle = repository.get(fantasticGradleId);
        return fantasticGradle.map(result -> mapper.modelToDto(result)).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createFantasticGradle(@RequestBody FantasticGradleDto request) {
        log.info(String.format("POST /api/v1/fantastic API invoked with params : %s", request));
        return repository.save(mapper.dtoToModel(request));
    }

    @PutMapping(path = "{fantasticGradleId}")
    public void updateFantasticGradle(
            @PathVariable @NotBlank UUID fantasticGradleId,
            @RequestBody FantasticGradleDto modified) {
        log.info(String.format("PUT /api/v1/fantastic/%s API invoked with params : %s", fantasticGradleId, modified));
        repository.get(fantasticGradleId).ifPresent(queried -> {
            queried.setName(modified.getName());
            repository.save(queried);
        });
    }


    //TODO #1 : Continue working on FantasticGradleController extension
    //TODO #3 : Implement logging
    //TODO #4 : Implement swagger
    //TODO #5 : Configure lombok annotations
}
