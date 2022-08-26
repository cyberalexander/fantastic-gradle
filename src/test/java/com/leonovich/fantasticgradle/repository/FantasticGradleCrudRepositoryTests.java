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

package com.leonovich.fantasticgradle.repository;

import com.leonovich.fantasticgradle.model.FantasticGradle;
import lombok.extern.apachecommons.CommonsLog;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created : 24/08/2022 08:12
 * Project : fantastic-gradle
 * IDE : IntelliJ IDEA
 *
 * @author Aliaksandr_Leanovich
 * @version 1.0
 */
@CommonsLog
@DataJpaTest
@ExtendWith(SpringExtension.class)
class FantasticGradleCrudRepositoryTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private transient CrudRepository<FantasticGradle, UUID> fantasticGradleCrudRepository;

    @Test
    void testFindById() {
        log.info(String.format("repo : %s", fantasticGradleCrudRepository));
        FantasticGradle given = EASY_RANDOM.nextObject(FantasticGradle.class);
        FantasticGradle saved = fantasticGradleCrudRepository.save(given);

        Optional<FantasticGradle> queried = fantasticGradleCrudRepository.findById(saved.getFantasticGradleId());

        Assertions.assertTrue(queried.isPresent());
        Assertions.assertEquals(saved, queried.get());
    }

    @Test
    void testFindAll() {
        log.info(String.format("repo : %s", fantasticGradleCrudRepository));
        List<FantasticGradle> saved = Stream.generate(() -> EASY_RANDOM.nextObject(FantasticGradle.class))
                .limit(3)
                .map(item -> fantasticGradleCrudRepository.save(item))
                .collect(Collectors.toList());

        List<FantasticGradle> queried = new ArrayList<>();
        fantasticGradleCrudRepository.findAll().forEach(queried::add);

        Assertions.assertTrue(queried.contains(saved.get(0)));
        Assertions.assertTrue(queried.contains(saved.get(1)));
        Assertions.assertTrue(queried.contains(saved.get(2)));
    }

    @Test
    void testSave() {
        log.info(String.format("repo : %s", fantasticGradleCrudRepository));
        FantasticGradle given = FantasticGradle.builder().name("fantastic_name").build();
        FantasticGradle saved = fantasticGradleCrudRepository.save(given);

        Optional<FantasticGradle> queried = fantasticGradleCrudRepository.findById(saved.getFantasticGradleId());

        Assertions.assertTrue(queried.isPresent());
        Assertions.assertEquals(saved, queried.get());
    }
}
