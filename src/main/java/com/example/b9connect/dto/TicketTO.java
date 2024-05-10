package com.example.b9connect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TicketTO (@JsonProperty("id") Long id, @JsonProperty("name") String name,
                        @JsonProperty("problem") String problem,@JsonProperty("service_id") Long serviceId){
}
