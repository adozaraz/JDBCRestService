package org.restservice.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(
        name = "EnrollmentController",
        urlPatterns = "/enrollments"
)
public class EnrollmentController extends HttpServlet {

}
