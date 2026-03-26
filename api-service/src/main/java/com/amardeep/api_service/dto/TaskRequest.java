package com.amardeep.api_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String type;
    private String payload;
}