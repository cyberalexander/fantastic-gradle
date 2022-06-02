package com.leonovich.fantasticgradle;

import com.leonovich.fantasticgradle.controller.FantasticGradleController;
import com.leonovich.fantasticgradle.mapper.FantasticGradleModelMapper;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import com.leonovich.fantasticgradle.repository.FantasticRepository;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Getter
@Setter
@ActiveProfiles(value = {"test"})
@WebMvcTest(
        controllers = {FantasticGradleController.class}
)
class FantasticGradleControllerTests {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    private static final String API = "/api/v1/fantastic";
    private static final String GET_API = API.concat("/{fantasticGradleId}");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FantasticGradleModelMapper mapper;

    @MockBean
    private FantasticRepository<FantasticGradle, UUID> repository;

    @Autowired
    private FantasticGradleController controller;

    @Test
    void testControllerInstantiated() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    void testGetFantasticGradle() throws Exception {
        FantasticGradle expected = EASY_RANDOM.nextObject(FantasticGradle.class);
        mapper.map(expected);
        repository.get(expected.getFantasticGradleId());

        mockMvc.perform(
                get(GET_API, expected.getFantasticGradleId()).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
                //.andExpect(content().json(mapper.map(expected).toString(), true)); //TODO add response validation
    }
}
