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

package com.leonovich.fantasticgradle.mapper;

import com.leonovich.fantasticgradle.dto.FantasticGradleDto;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
class FantasticGradleMapperTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    private static final FantasticGradleMapper mapper = Mappers.getMapper(FantasticGradleMapper.class);

    @Test
    void testModelToDto() {
        var model = EASY_RANDOM.nextObject(FantasticGradle.class);
        var dto = mapper.modelToDto(model);
        assertThat(dto.getFantasticGradleId()).isEqualTo(model.getFantasticGradleId());
        assertThat(dto.getName()).isEqualTo(model.getName());
        assertThat(dto.getCreatedWhen()).isEqualTo(model.getCreatedWhen());
    }

    @Test
    void testDtoToModel() {
        var dto = EASY_RANDOM.nextObject(FantasticGradleDto.class);
        var model = mapper.dtoToModel(dto);
        assertThat(model.getFantasticGradleId()).isEqualTo(dto.getFantasticGradleId());
        assertThat(model.getName()).isEqualTo(dto.getName());
        assertThat(model.getCreatedWhen()).isEqualTo(dto.getCreatedWhen());
    }
}
