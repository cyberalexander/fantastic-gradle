package com.leonovich.fantasticgradle.controller;

import com.leonovich.fantasticgradle.dto.FantasticGradleDto;
import com.leonovich.fantasticgradle.mapper.FantasticGradleModelMapper;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import com.leonovich.fantasticgradle.repository.FantasticRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The controller is aimed to manage {@link FantasticGradleDto} entity.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/fantastic", produces = {"application/json"})
public class FantasticGradleController {

    private FantasticGradleModelMapper mapper;

    private FantasticRepository<FantasticGradle, UUID> repository;


    /**
     * Example of UUID : 4fcfc816-2f2f-420a-b159-4150366e489c or use {@code UUID.randomUUID()}
     * @param fantasticGradleId The unique identifier of fantastic gradle
     * @return The instance of {@link FantasticGradleDto} associated with given {@param fantasticGradleId}
     */
    @GetMapping(path = "{fantasticGradleId}")
    public FantasticGradleDto getFantasticGradle(@PathVariable UUID fantasticGradleId) {
        return mapper.map(repository.get(fantasticGradleId));
    }

    @PostMapping
    public FantasticGradleDto createFantasticGradle(@RequestBody FantasticGradleDto request) {
        //TODO implement creation of the FantasticGradle in memory
        return request;
    }


    //TODO #1 : Continue working on FantasticGradleController extension
    //TODO #2 : Implement unit tests
    //TODO #3 : Implement logging
    //TODO #4 : Implement swagger
    //TODO #5 : Configure lombok annotations
    //TODO #6 : Implement objectMapper to convert Model to DTO
}
