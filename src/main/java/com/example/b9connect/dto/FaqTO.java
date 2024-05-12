package com.example.b9connect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FaqTO(@JsonProperty("question") String question,@JsonProperty("answer") String answer) {
}
