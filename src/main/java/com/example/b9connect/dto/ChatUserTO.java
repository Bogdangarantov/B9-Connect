package com.example.b9connect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatUserTO(@JsonProperty("name") String name,@JsonProperty("id") Long id) {
}
