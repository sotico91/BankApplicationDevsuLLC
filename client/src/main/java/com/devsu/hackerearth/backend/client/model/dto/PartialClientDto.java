package com.devsu.hackerearth.backend.client.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PartialClientDto {
    // Lombok names the setter setActive(); accept the "isActive" key the API documents
    @Setter(onMethod_ = @JsonAlias("isActive"))
    private boolean isActive;
}
