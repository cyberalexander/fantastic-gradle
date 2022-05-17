package com.leonovich.fantasticgradle.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FantasticGradle {

    private UUID fantasticGradleId;

    private String name;

    private LocalDateTime createdWhen;
}

