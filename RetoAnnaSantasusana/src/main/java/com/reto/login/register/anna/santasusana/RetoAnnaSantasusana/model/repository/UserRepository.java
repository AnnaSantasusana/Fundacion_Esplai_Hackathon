package com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.repository;

import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
