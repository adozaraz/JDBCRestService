package org.restservice.factories;

import org.restservice.services.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action<Entity, Key> {
    void perform(HttpServletRequest request, HttpServletResponse response, Service<Entity, Key> service);
}
