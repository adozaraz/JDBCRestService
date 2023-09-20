package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.StudentService;
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
@RequestMapping("/student")
public class StudentController extends HttpServlet {
    @Autowired
    private StudentService service;

    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student) {
        return this.service.create(student);
    }

    @GetMapping("/get/{uuid}")
    public Optional<Student> getStudent(@PathVariable UUID uuid) {
        return this.service.read(uuid);
    }

    @PostMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return this.service.update(student);
    }

    @DeleteMapping("/delete")
    public void deleteStudent(@RequestBody Student student) {
        this.service.delete(student);
    }

    @GetMapping("/get/all")
    public Iterable<Student> getAllStudents() {
        return this.service.findAll();
    }

    @GetMapping("/get/firstName/{firstName}")
    public Optional<Student> getByFirstName(@PathVariable String firstName) {
        return this.service.findByFirstName(firstName);
    }

    @GetMapping("/get/lastName/{lastName}")
    public Optional<Student> getByLastName(@PathVariable String lastName) {
        return this.service.findByLastName(lastName);
    }

    @GetMapping("/get/fullName/{firstName}/{lastName}")
    public Optional<Student> getByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        return this.service.findByFullName(firstName, lastName);
    }
}
