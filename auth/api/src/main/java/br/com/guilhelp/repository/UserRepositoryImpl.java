package br.com.guilhelp.repository;

import br.com.guilhelp.entities.User;
import br.com.guilhelp.repositories.UserRepository;
import br.com.guilhelp.repository.adapter.UserRepositoryAdapter;
import br.com.guilhelp.repository.client.MongoUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final MongoUserRepository mongoUserRepository;
    private final UserRepositoryAdapter adapter;

    public UserRepositoryImpl(MongoUserRepository mongoUserRepository, UserRepositoryAdapter adapter) {
        this.mongoUserRepository = mongoUserRepository;
        this.adapter = adapter;
    }

    @Override
    public User save(User user) {
        var orm = adapter.cast(user);
        var savedOrm = mongoUserRepository.save(orm);
        return adapter.cast(savedOrm);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return mongoUserRepository.findByEmail(email)
                .map(adapter::cast);
    }
}
