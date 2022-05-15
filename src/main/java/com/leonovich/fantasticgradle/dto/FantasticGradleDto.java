package com.leonovich.fantasticgradle.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FantasticGradleDto {

    private UUID fantasticGradleId;

    public FantasticGradleDto(UUID fantasticGradleId) {
        this.fantasticGradleId = fantasticGradleId;
    }
}
