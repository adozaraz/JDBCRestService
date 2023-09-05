package org.restservice.factories;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action<Service> {
    void perform(HttpServletRequest request, HttpServletResponse response, Service service);
}
