package br.com.guilhelp.repositories;

import br.com.guilhelp.entities.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
