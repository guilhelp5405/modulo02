package br.com.guilhelp.dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginDto(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(
            email,
            password
        );
    }
}
