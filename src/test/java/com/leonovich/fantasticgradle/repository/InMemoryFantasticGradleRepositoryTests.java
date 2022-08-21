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
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@CommonsLog
@Getter
@ExtendWith(SpringExtension.class)
class InMemoryFantasticGradleRepositoryTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    private FantasticRepository<FantasticGradle, UUID> repository = new InMemoryFantasticGradleRepository();


    @Test
    void testGetSuccess() {
        //given
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);
        UUID id = repository.save(expected);

        //when
        Optional<FantasticGradle> actual = repository.get(id);

        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getName(), actual.get().getName());
        Assertions.assertEquals(expected.getCreatedWhen(), actual.get().getCreatedWhen());
    }

    @Test
    void testGetIdempotent() {
        //given
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);

        //when
        UUID id1 = repository.save(expected);
        Optional<FantasticGradle> actual1 = repository.get(id1);

        UUID id2 = repository.save(expected);
        Optional<FantasticGradle> actual2 = repository.get(id2);

        //then
        Assertions.assertEquals(id1, id2);
        Assertions.assertEquals(actual1, actual2);
    }

    @Test
    void testGetAll() {
        //given
        FantasticGradle expected1 = EASY_RANDOM.nextObject(FantasticGradle.class);
        FantasticGradle expected2 = EASY_RANDOM.nextObject(FantasticGradle.class);
        FantasticGradle expected3 = EASY_RANDOM.nextObject(FantasticGradle.class);

        //when
        repository.save(expected1);
        repository.save(expected2);
        repository.save(expected3);
        Collection<FantasticGradle> actual = repository.getAll();

        //then
        Assertions.assertTrue(actual.contains(expected1));
        Assertions.assertTrue(actual.contains(expected2));
        Assertions.assertTrue(actual.contains(expected3));
    }
}
