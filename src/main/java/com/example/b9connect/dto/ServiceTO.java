package com.example.b9connect.dto;

import com.example.b9connect.entities.Faqs;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ServiceTO(@JsonProperty("id") Long id,
                        @JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("faq") List<Faqs> faq,
                        @JsonProperty("users") List<Long> users
) {
}
