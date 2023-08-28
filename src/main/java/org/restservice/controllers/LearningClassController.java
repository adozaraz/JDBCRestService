package org.restservice.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(
        name = "LearningClassController",
        urlPatterns = "/learningClasses"
)
public class LearningClassController extends HttpServlet {
}
