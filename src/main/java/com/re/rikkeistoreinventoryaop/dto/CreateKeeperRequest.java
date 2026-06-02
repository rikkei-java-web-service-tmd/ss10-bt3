package com.re.rikkeistoreinventoryaop.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateKeeperRequest(
        @NotBlank String fullName,
        @NotBlank String staffCode) {
}
