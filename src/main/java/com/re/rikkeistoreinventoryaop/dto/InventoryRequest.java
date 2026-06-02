package com.re.rikkeistoreinventoryaop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryRequest(
        @NotBlank String sku,
        @NotNull @Positive Long quantity,
        @NotNull @Positive Long keeperId) {
}
