package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.Enrollment;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.LearningClassService;
import org.restservice.services.Service;
import org.restservice.services.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(
        name = "EnrollmentController",
        urlPatterns = "/enrollments"
)
public class EnrollmentController extends HttpServlet {
    private final EnrollmentService service;
    private final StudentService studentService;
    private final LearningClassService learningClassService;
    private final HashMap<String, Action<Enrollment, UUID>> actionHashMap = new HashMap<>();

    public EnrollmentController(EnrollmentService service, StudentService studentService, LearningClassService learningClassService) {
        this.service = service;
        this.studentService = studentService;
        this.learningClassService = learningClassService;

        actionHashMap.put("create", new Action<Enrollment, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Enrollment, UUID> service) {
                UUID studentId = UUID.fromString(request.getParameter("student"));
                UUID learningClassId = UUID.fromString(request.getParameter("learningClass"));
                Optional<Student> student = studentService.read(studentId);
                Optional<LearningClass> learningClass = learningClassService.read(learningClassId);
                if (student.isPresent() && learningClass.isPresent()) {
                    if (service.create(new Enrollment(List.of(student.get()), List.of(learningClass.get())))) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });
        actionHashMap.put("get", new Action<Enrollment, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Enrollment, UUID> service) {
                UUID enrollmentId = UUID.fromString(request.getParameter("enrollmentId"));
                Optional<Enrollment> enrollment = service.read(enrollmentId);
                if (enrollment.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(enrollment.get());
                        out.print(studentJson);
                        out.flush();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        });

        actionHashMap.put("update", new Action<Enrollment, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Enrollment, UUID> service) {
                UUID enrollmentId = UUID.fromString(request.getParameter("enrollmentId"));
                UUID studentId = UUID.fromString(request.getParameter("student"));
                UUID learningClassId = UUID.fromString(request.getParameter("learningClass"));
                Optional<Student> student = studentService.read(studentId);
                Optional<LearningClass> learningClass = learningClassService.read(learningClassId);
                if (student.isPresent() && learningClass.isPresent()) {
                    if (service.update(new Enrollment(enrollmentId, List.of(student.get()), List.of(learningClass.get())))) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });

        actionHashMap.put("delete", new Action<Enrollment, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Enrollment, UUID> service) {
                UUID enrollmentId = UUID.fromString(request.getParameter("enrollmentId"));
                UUID studentId = UUID.fromString(request.getParameter("student"));
                UUID learningClassId = UUID.fromString(request.getParameter("learningClass"));
                Optional<Student> student = studentService.read(studentId);
                Optional<LearningClass> learningClass = learningClassService.read(learningClassId);
                if (student.isPresent() && learningClass.isPresent()) {
                    if (service.delete(new Enrollment(enrollmentId, List.of(student.get()), List.of(learningClass.get())))) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionType = request.getParameter("action");
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        Action<Enrollment, UUID> action = actionHashMap.getOrDefault(actionType, new Action<Enrollment, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Enrollment, UUID> service) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
        action.perform(request, response, service);
    }
}
