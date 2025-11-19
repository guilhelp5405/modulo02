package br.com.guilhelp.repository.adapter;

import br.com.guilhelp.entities.User;
import br.com.guilhelp.repository.orm.UserOrm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAdapter {
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User cast(UserOrm orm) {
        return new User(
                orm.id(),
                orm.name(),
                orm.email(),
                orm.password()
        );
    }

    public UserOrm cast(User entity) {
        return new UserOrm(
                entity.id(),
                entity.name(),
                entity.email(),
                passwordEncoder.encode(entity.password())
        );
    }
}
