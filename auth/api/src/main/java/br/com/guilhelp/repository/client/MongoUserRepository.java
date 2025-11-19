package br.com.guilhelp.repository.client;

import br.com.guilhelp.repository.orm.UserOrm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<UserOrm, String> {
    Optional<UserOrm> findByEmail(String email);
}
