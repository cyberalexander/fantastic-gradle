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
package com.leonovich.fantasticgradle.repository;

import com.leonovich.fantasticgradle.model.FantasticGradle;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Primary
@Component
public class InMemoryFantasticGradleRepository implements FantasticRepository<FantasticGradle, UUID> {

    private static final Map<UUID, FantasticGradle> STORAGE = new HashMap<>();

    @Override
    public Optional<FantasticGradle> get(UUID fantasticGradleId) {
        return Optional.ofNullable(STORAGE.get(fantasticGradleId));
    }

    @Override
    public UUID save(FantasticGradle fantasticGradle) {
        if (Objects.isNull(fantasticGradle.getFantasticGradleId())) {
            fantasticGradle.setFantasticGradleId(UUID.randomUUID());
            STORAGE.put(fantasticGradle.getFantasticGradleId(), fantasticGradle);
        } else {
            STORAGE.put(fantasticGradle.getFantasticGradleId(), fantasticGradle);
        }
        return fantasticGradle.getFantasticGradleId();
    }
}
