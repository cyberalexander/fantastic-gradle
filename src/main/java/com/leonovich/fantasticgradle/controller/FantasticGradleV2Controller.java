package com.leonovich.fantasticgradle.controller;

import com.leonovich.fantasticgradle.dto.FantasticGradleDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The 2-nd version of the Controller, which overrides and enhances the API from V1 {@link FantasticGradleController}.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v2/fantastic", produces = {"application/json"})
public class FantasticGradleV2Controller {
    private static final String API_VERSION_SUFFIX = "_V2";

    /**
     * Example of UUID : 4fcfc816-2f2f-420a-b159-4150366e489c or use {@code UUID.randomUUID()}
     * @param fantasticGradleId The unique identifier of fantastic gradle
     * @return The instance of {@link FantasticGradleDto} associated with given {@param fantasticGradleId}
     */
    @GetMapping(path = "{fantasticGradleId}")
    public FantasticGradleDto getFantasticGradle(@PathVariable UUID fantasticGradleId) {
        return FantasticGradleDto.builder()
                .fantasticGradleId(fantasticGradleId)
                .name("FantasticGradle".concat(API_VERSION_SUFFIX))
                .createdWhen(LocalDateTime.now())
                .build();
    }
}
