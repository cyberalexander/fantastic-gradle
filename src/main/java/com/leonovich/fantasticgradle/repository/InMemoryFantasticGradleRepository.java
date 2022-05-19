package com.leonovich.fantasticgradle.repository;

import com.leonovich.fantasticgradle.model.FantasticGradle;
import org.jeasy.random.EasyRandom;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Primary
@Component
public class InMemoryFantasticGradleRepository implements FantasticRepository<FantasticGradle, UUID> {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    private static final Map<UUID, FantasticGradle> STORAGE = new HashMap<>();

    @Override
    public FantasticGradle get(UUID fantasticGradleId) {
        return FantasticGradle.builder()
                .fantasticGradleId(fantasticGradleId)
                .name(EASY_RANDOM.nextObject(String.class))
                .createdWhen(LocalDateTime.now())
                .build();
    }

    @Override
    public UUID save(FantasticGradle fantasticGradle) {
        fantasticGradle.setFantasticGradleId(UUID.randomUUID());
        STORAGE.put(fantasticGradle.getFantasticGradleId(), fantasticGradle);
        return fantasticGradle.getFantasticGradleId();
    }
}
