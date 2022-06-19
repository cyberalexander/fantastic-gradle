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

import com.leonovich.fantasticgradle.FantasticGradleApplication;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

@CommonsLog
@Getter
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FantasticGradleApplication.class)
class InMemoryFantasticGradleRepositoryTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private FantasticRepository<FantasticGradle, UUID> repository;


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
}
