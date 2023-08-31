package org.restservice.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.services.LearningClassService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LearningClassControllerTest {

    @InjectMocks
    private LearningClassController learningClassController;

    @Mock
    private LearningClassService learningClassService;

    private LearningClass learningClass;

    @BeforeEach
    public void setUp() {
        learningClass = new LearningClass("Infinite", "Power");
    }

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get
        when(request.getParameter("learningClassId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(learningClass.getLearningClassId());

        when(request.getParameter("action")).thenReturn("get");

        when(learningClassService.read(UUID.fromString(learningClass.getLearningClassId()))).thenReturn(Optional.of(learningClass));
        when(learningClassService.read(not(UUID.fromString(learningClass.getLearningClassId())))).thenReturn(Optional.empty());

        learningClassController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        learningClassController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(learningClass.getLearningClassId()));
        assertTrue(stringWriter.toString().contains(learningClass.getTitle()));
        assertTrue(stringWriter.toString().contains(learningClass.getDescription()));

        stringWriter.flush();
        writer.flush();
        //Get with classes
        Set<Student> attendingStudents = new HashSet<>();
        attendingStudents.add(new Student("Total", "Hive"));
        attendingStudents.add(new Student("Absolute", "Power"));
        attendingStudents.add(new Student("Insight", "Future"));
        learningClass.setAttendingStudents(attendingStudents);

        when(request.getParameter("learningClassId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("action")).thenReturn("getWithClasses");

        when(learningClassService.read(UUID.fromString(learningClass.getLearningClassId()))).thenReturn(Optional.of(learningClass));
        when(learningClassService.read(not(UUID.fromString(learningClass.getLearningClassId())))).thenReturn(Optional.empty());

        learningClassController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        learningClassController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(learningClass.getLearningClassId()));
        assertTrue(stringWriter.toString().contains(learningClass.getTitle()));
        assertTrue(stringWriter.toString().contains(learningClass.getDescription()));
        assertTrue(stringWriter.toString().contains("Total"));
        assertTrue(stringWriter.toString().contains("Hive"));
        assertTrue(stringWriter.toString().contains("Absolute"));
        assertTrue(stringWriter.toString().contains("Power"));
        assertTrue(stringWriter.toString().contains("Insight"));
        assertTrue(stringWriter.toString().contains("Future"));

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doPost() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Create
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("title")).thenReturn(learningClass.getTitle());
        when(request.getParameter("description")).thenReturn(learningClass.getDescription());
        when(learningClassService.create(any(LearningClass.class))).thenReturn(true);

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        //Update
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("learningClassId")).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("title")).thenReturn("Lala").thenReturn(learningClass.getTitle());
        when(request.getParameter("description")).thenReturn("Tralala").thenReturn(learningClass.getDescription());

        when(learningClassService.update(learningClass)).thenReturn(true).thenReturn(false);
        when(learningClassService.update(not(learningClass))).thenReturn(false);

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());


        //Delete

        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("learningClassId")).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("title")).thenReturn("Lala").thenReturn(learningClass.getTitle());
        when(request.getParameter("description")).thenReturn("Tralala").thenReturn(learningClass.getDescription());

        when(learningClassService.delete(learningClass)).thenReturn(true).thenReturn(false);
        when(learningClassService.delete(not(learningClass))).thenReturn(false);

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        learningClassController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }
}