package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.factories.Action;
import org.restservice.services.EnrollmentService;
import org.restservice.services.LearningClassService;

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
        name = "LearningClassController",
        urlPatterns = "/learningClasses"
)
public class LearningClassController extends HttpServlet {

    private final LearningClassService service;

    private final HashMap<String, Action<LearningClassService>> actionHashMap = new HashMap<>();

    public LearningClassController(EnrollmentService service) throws SQLException {
        this.service = new LearningClassService(service);
        createActionMap();
    }

    public LearningClassController(LearningClassService service) {
        this.service = service;
        createActionMap();
    }

    private void createActionMap() {
        actionHashMap.put("create", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                try {
                    BufferedReader reader = request.getReader();
                    LearningClassDTO learningClassDTO = new Gson().fromJson(reader, LearningClassDTO.class);
                    if (service.create(learningClassDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("get", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                UUID learningClassId = UUID.fromString(request.getParameter("learningClassId"));
                Optional<LearningClass> learningClass = service.read(learningClassId);
                if (learningClass.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String learningClassJson = new Gson().toJson(learningClass.get());
                        out.print(learningClassJson);
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

        actionHashMap.put("update", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                try {
                    BufferedReader reader = request.getReader();
                    LearningClassDTO learningClassDTO = new Gson().fromJson(reader, LearningClassDTO.class);
                    if (service.update(learningClassDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        });

        actionHashMap.put("delete", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                BufferedReader reader = null;
                try {
                    reader = request.getReader();
                    LearningClassDTO learningClassDTO = new Gson().fromJson(reader, LearningClassDTO.class);
                    if (service.delete(learningClassDTO)) response.setStatus(HttpServletResponse.SC_OK);
                    else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            }
        });

        actionHashMap.put("getWithStudents", new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                Optional<LearningClass> learningClass = service.getLearningClassWithAttendingStudents(UUID.fromString(request.getParameter("learningClassId")));
                if (learningClass.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String learningClassJson = new Gson().toJson(learningClass.get());
                        out.print(learningClassJson);
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
        Action action = actionHashMap.getOrDefault(actionType, new Action<>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, LearningClassService service) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
        action.perform(request, response, service);
    }
}
