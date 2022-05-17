package com.leonovich.fantasticgradle.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FantasticGradleDto {

    private UUID fantasticGradleId;

    private String name;

    private LocalDateTime createdWhen;
}
