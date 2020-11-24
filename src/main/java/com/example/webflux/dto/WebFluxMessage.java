package com.example.webflux.dto;

import lombok.Data;

@Data
public class WebFluxMessage {
    private Long id;
    private Integer destId;
    private String value;
}
