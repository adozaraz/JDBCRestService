package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.LearningClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/learningClass")
public class LearningClassController extends HttpServlet {

    @Autowired
    private LearningClassService service;

    private final HashMap<String, Action<LearningClassService>> actionHashMap = new HashMap<>();

    @PostMapping("/create")
    public LearningClass createLearningClass(@RequestBody LearningClass learningClass) {
        return this.service.create(learningClass);
    }

    @GetMapping("/get/{uuid}")
    public Optional<LearningClass> getLearningCLass(@PathVariable UUID uuid) {
        return this.service.read(uuid);
    }

    @PostMapping("/update")
    public LearningClass updateLearningClass(@RequestBody LearningClass learningClass) {
        return this.service.update(learningClass);
    }

    @DeleteMapping("/delete")
    public void deleteLearningClass(@RequestBody LearningClass learningClass) {
        this.service.delete(learningClass);
    }
}
