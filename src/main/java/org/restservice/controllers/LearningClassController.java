package org.restservice.controllers;

import com.google.gson.Gson;
import org.restservice.entities.LearningClass;
import org.restservice.factories.Action;
import org.restservice.services.LearningClassService;
import org.restservice.services.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@WebServlet(
        name = "LearningClassController",
        urlPatterns = "/learningClasses"
)
public class LearningClassController extends HttpServlet {

    private final LearningClassService service;

    private HashMap<String, Action<LearningClass, UUID>> actionHashMap = new HashMap<>();

    public LearningClassController(LearningClassService service) {
        this.service = service;

        actionHashMap.put("create", new Action<LearningClass, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<LearningClass, UUID> service) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                if (service.create(new LearningClass(title, description))) response.setStatus(HttpServletResponse.SC_OK);
                else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        });

        actionHashMap.put("get", new Action<LearningClass, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<LearningClass, UUID> service) {
                UUID learningClassId = UUID.fromString(request.getParameter("learningClassId"));
                Optional<LearningClass> learningClass = service.read(learningClassId);
                if (learningClass.isPresent()) {
                    try {
                        PrintWriter out = response.getWriter();
                        String learningClassJson = new Gson().toJson(learningClass.get());
                        out.print(learningClassJson);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        });

        actionHashMap.put("update", new Action<LearningClass, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<LearningClass, UUID> service) {
                String learningClassId = request.getParameter("learningClassId");
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                if (service.update(new LearningClass(learningClassId, title, description))) response.setStatus(HttpServletResponse.SC_OK);
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
        Action<LearningClass, UUID> action = actionHashMap.getOrDefault(actionType, new Action<LearningClass, UUID>() {
            @Override
            public void perform(HttpServletRequest request, HttpServletResponse response, Service<LearningClass, UUID> service) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
        action.perform(request, response, service);
    }
}
