package br.com.guilhelp.dtos;

import java.util.Optional;

public record ErrorResponseDto(
        String message,
        int status,
        String timestamp,
        Optional<Object> details
) {
}
