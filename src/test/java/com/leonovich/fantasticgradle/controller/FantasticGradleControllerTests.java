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
package com.leonovich.fantasticgradle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonovich.fantasticgradle.mapper.FantasticGradleMapper;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Getter
@Setter
@ActiveProfiles(value = {"test"})
@WebMvcTest(
        controllers = {FantasticGradleController.class, FantasticGradleMapper.class}
)
class FantasticGradleControllerTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    private static final String API = "/api/v1/fantastic";
    private static final String GET_PUT_API = API.concat("/{fantasticGradleId}");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FantasticGradleMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CrudRepository<FantasticGradle, UUID> repository;

    @Autowired
    @InjectMocks
    private FantasticGradleController controller;

    @Test
    void testControllerInstantiated() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    void testGetFantasticGradle() throws Exception {
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);

        Mockito.when(repository.findById(expected.getFantasticGradleId())).thenReturn(Optional.of(expected));

        mockMvc.perform(get(GET_PUT_API, expected.getFantasticGradleId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fantasticGradleId").hasJsonPath())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.createdWhen").exists());
    }

    @Test
    void testGetFantasticGradleNotFound() throws Exception {
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);

        Mockito.when(repository.findById(expected.getFantasticGradleId())).thenReturn(Optional.empty());

        mockMvc.perform(get(GET_PUT_API, expected.getFantasticGradleId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fantasticGradleId").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.createdWhen").doesNotExist());
    }

    @Test
    void testGetAllFantasticGradles() throws Exception {
        List<FantasticGradle> expected = List.of(
                EASY_RANDOM.nextObject(FantasticGradle.class),
                EASY_RANDOM.nextObject(FantasticGradle.class),
                EASY_RANDOM.nextObject(FantasticGradle.class)
        );

        Mockito.when(repository.findAll()).thenReturn(expected);

        mockMvc.perform(get(API + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void testCreateFantasticGradle() throws Exception {
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);

        Mockito.when(repository.save(expected)).thenReturn(expected);

        mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(expected.getFantasticGradleId())));
    }

    @Test
    void testUpdateFantasticGradle() throws Exception {
        FantasticGradle modified = EASY_RANDOM.nextObject(FantasticGradle.class);
        FantasticGradle updated = EASY_RANDOM.nextObject(FantasticGradle.class);
        updated.setFantasticGradleId(modified.getFantasticGradleId());

        Mockito.when(repository.findById(modified.getFantasticGradleId())).thenReturn(Optional.of(updated));

        // 1. Test that PUT operation invoked successfully
        mockMvc.perform(put(GET_PUT_API, modified.getFantasticGradleId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified)))
                .andExpect(status().isOk());

        // 2. Invoke GET operation to check that object was updated during PUT operation
        mockMvc.perform(get(GET_PUT_API, modified.getFantasticGradleId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.name").value(modified.getName()));
    }
}
