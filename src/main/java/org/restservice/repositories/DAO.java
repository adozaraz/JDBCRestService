package org.restservice.repositories;

import java.util.Optional;

public interface DAO<Entity, Key> {
    boolean create(Entity entity);
    Optional<Entity> read(Key key);
    boolean update(Entity entity);
    boolean delete(Entity entity);
}
