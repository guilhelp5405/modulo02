package br.com.guilhelp.entities;

public record User(
        String id,
        String name,
        String email,
        String password
) {
}
