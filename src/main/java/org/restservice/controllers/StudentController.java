package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.StudentService;

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

@WebServlet(
        name = "StudentController",
        urlPatterns = "/students"
)
public class StudentController extends HttpServlet {
    private final StudentService service;
    private final HashMap<String, Action<StudentService>> actionHashMap = new HashMap<>();

    public StudentController(EnrollmentService enrollmentService) throws SQLException {
        this.service = new StudentService(enrollmentService);
        createActionMap();
    }

    public StudentController(StudentService service) {
        this.service = service;
        createActionMap();
    }

    private void createActionMap() {
        actionHashMap.put("create", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                BufferedReader reader = null;
                try {
                    reader = request.getReader();
                    StudentDTO studentDTO = new Gson().fromJson(reader, StudentDTO.class);
                    if (service.create(studentDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            }
        });
        actionHashMap.put("get", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                UUID studentId = UUID.fromString(request.getParameter("studentId"));
                Optional<Student> student = service.read(studentId);
                if (student.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(student.get());
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

        actionHashMap.put("update", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                BufferedReader reader = null;
                try {
                    reader = request.getReader();
                    StudentDTO studentDTO = new Gson().fromJson(reader, StudentDTO.class);
                    if (service.update(studentDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        actionHashMap.put("delete", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                BufferedReader reader = null;
                try {
                    reader = request.getReader();
                    StudentDTO studentDTO = new Gson().fromJson(reader, StudentDTO.class);
                    if (service.delete(studentDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        actionHashMap.put("getAll", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                Iterable<Student> students = service.findAll();
                try {
                    PrintWriter out = response.getWriter();
                    for (Student student : students) {
                        String studentJson = new Gson().toJson(student);
                        out.println(studentJson);
                    }
                    out.flush();
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("getByFirstName", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                Optional<Student> student = service.findByFirstName(request.getParameter("firstName"));
                if (student.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(student.get());
                        out.println(studentJson);
                        out.flush();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        });

        actionHashMap.put("getByLastName", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                Optional<Student> student = service.findByLastName(request.getParameter("lastName"));
                if (student.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(student.get());
                        out.println(studentJson);
                        out.flush();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        });

        actionHashMap.put("getByFullName", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                Optional<Student> student = service.findByFullName(request.getParameter("firstName"), request.getParameter("lastName"));
                if (student.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(student.get());
                        out.println(studentJson);
                        out.flush();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        });

        actionHashMap.put("getWithClasses", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                Optional<Student> student = service.getStudentWithAttendingClasses(UUID.fromString(request.getParameter("studentId")));
                if (student.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String studentJson = new Gson().toJson(student.get());
                        out.println(studentJson);
                        out.flush();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
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
        response.setCharacterEncoding("UTF-8");
        Action<StudentService> action = actionHashMap.getOrDefault(actionType, new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, StudentService service) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
        action.perform(request, response, service);
    }
}
