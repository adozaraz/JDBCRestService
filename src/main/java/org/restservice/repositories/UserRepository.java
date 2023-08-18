package org.restservice.repositories;

import org.restservice.entities.User;

import java.util.UUID;

public interface UserRepository extends DAO<User, UUID> {
    Iterable<User> findAll();
    Iterable<User> findAllById(Iterable<UUID> ids);
    boolean saveAll(Iterable<User> users);

}
