package org.restservice.repositories;

public interface DAO<Entity, Key> {
    boolean create(Entity entity);
    Entity read(Key key);
    boolean update(Entity entity);
    boolean delete(Entity entity);
    boolean deleteByKey(Key key);
}
