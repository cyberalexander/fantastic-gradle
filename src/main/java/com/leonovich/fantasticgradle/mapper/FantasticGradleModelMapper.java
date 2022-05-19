package com.leonovich.fantasticgradle.mapper;

import com.leonovich.fantasticgradle.dto.FantasticGradleDto;
import com.leonovich.fantasticgradle.model.FantasticGradle;
import org.springframework.stereotype.Component;

@Component
public class FantasticGradleModelMapper {

    public FantasticGradleDto map(FantasticGradle source) {
        return FantasticGradleDto.builder()
                .fantasticGradleId(source.getFantasticGradleId())
                .name(source.getName())
                .createdWhen(source.getCreatedWhen())
                .build();
    }

    public FantasticGradle map(FantasticGradleDto source) {
        return FantasticGradle.builder()
                .fantasticGradleId(source.getFantasticGradleId())
                .name(source.getName())
                .createdWhen(source.getCreatedWhen())
                .build();
    }
}
