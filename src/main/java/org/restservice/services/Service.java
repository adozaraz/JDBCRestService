package org.restservice.services;

import org.restservice.repositories.DAO;

import java.util.Optional;

public interface Service<Entity, Key> extends DAO<Entity, Key> {
}
