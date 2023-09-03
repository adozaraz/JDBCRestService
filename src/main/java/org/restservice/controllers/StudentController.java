package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.Student;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.Service;
import org.restservice.services.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final HashMap<String, Action<Student, UUID>> actionHashMap = new HashMap<>();

    public StudentController(EnrollmentService enrollmentService) throws SQLException {
        this.service = new StudentService(enrollmentService);
        createActionMap();
    }

    public StudentController(StudentService service) {
        this.service = service;
        createActionMap();
    }

    private void createActionMap() {
        actionHashMap.put("create", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                if (service.create(new Student(firstName, lastName))) response.setStatus(HttpServletResponse.SC_OK);
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });
        actionHashMap.put("get", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
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

        actionHashMap.put("update", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                UUID studentId = UUID.fromString(request.getParameter("studentId"));
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                if (service.update(new Student(studentId, firstName, lastName))) response.setStatus(HttpServletResponse.SC_OK);
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });

        actionHashMap.put("delete", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                UUID studentId = UUID.fromString(request.getParameter("studentId"));
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                if (service.delete(new Student(studentId, firstName, lastName))) response.setStatus(HttpServletResponse.SC_OK);
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });

        actionHashMap.put("getAll", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                if (service instanceof StudentService studentService) {
                    Iterable<Student> students = studentService.findAll();
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
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("getByFirstName", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                if (service instanceof StudentService studentService) {
                    Optional<Student> student = studentService.findByFirstName(request.getParameter("firstName"));
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
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("getByLastName", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                if (service instanceof StudentService studentService) {
                    Optional<Student> student = studentService.findByLastName(request.getParameter("lastName"));
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
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("getByFullName", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                if (service instanceof StudentService studentService) {
                    Optional<Student> student = studentService.findByFullName(request.getParameter("firstName"), request.getParameter("lastName"));
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
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("getWithClasses", new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                if (service instanceof StudentService studentService) {
                    Optional<Student> student = studentService.getStudentWithAttendingClasses(UUID.fromString(request.getParameter("studentId")));
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
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
        Action<Student, UUID> action = actionHashMap.getOrDefault(actionType, new Action<Student, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<Student, UUID> service) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
        action.perform(request, response, service);
    }
}
