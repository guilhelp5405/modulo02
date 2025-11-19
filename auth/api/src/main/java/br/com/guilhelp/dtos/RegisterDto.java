package br.com.guilhelp.dtos;

import br.com.guilhelp.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
    public User toDomain() {
        return new User(null, name, email, password);
    }
}
