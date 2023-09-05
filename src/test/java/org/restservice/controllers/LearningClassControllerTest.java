package org.restservice.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.services.LearningClassService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LearningClassControllerTest {

    private LearningClassController learningClassController;

    private LearningClassService learningClassService;

    private LearningClass learningClass;

    @BeforeEach
    public void setUp() {
        learningClass = new LearningClass("Infinite", "Power");
        this.learningClassService = mock(LearningClassService.class);
        learningClassController = new LearningClassController(learningClassService);
    }

    @Test
    void doGetDataFail() throws IOException, ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("learningClassId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("action")).thenReturn("get");


        learningClassController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetDataSuccess() throws IOException, ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("learningClassId")).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("action")).thenReturn("get");
        when(learningClassService.read(UUID.fromString(learningClass.getLearningClassId()))).thenReturn(Optional.of(learningClass));

        learningClassController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);

        assertTrue(stringWriter.toString().contains(learningClass.getLearningClassId()));
        assertTrue(stringWriter.toString().contains(learningClass.getTitle()));
        assertTrue(stringWriter.toString().contains(learningClass.getDescription()));

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doGetWithStudentsFail() throws IOException, ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        //Get with classes
        when(request.getParameter("learningClassId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("action")).thenReturn("getWithStudents");

        learningClassController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doGetWithStudentsSuccess() throws IOException, ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        //Get with classes
        Set<Student> attendingStudents = new HashSet<>();
        attendingStudents.add(new Student("Total", "Hive"));
        attendingStudents.add(new Student("Absolute", "Power"));
        attendingStudents.add(new Student("Insight", "Future"));
        learningClass.setAttendingStudents(attendingStudents);

        when(request.getParameter("learningClassId")).thenReturn(learningClass.getLearningClassId());
        when(request.getParameter("action")).thenReturn("getWithStudents");
        when(learningClassService.getLearningClassWithAttendingStudents(UUID.fromString(learningClass.getLearningClassId()))).thenReturn(Optional.of(learningClass));

        learningClassController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);

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
    void doPostCreate() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("action")).thenReturn("create");

        LearningClassDTO learningClass = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Class");

        Set<String> attendingStudents = new HashSet<>();
        attendingStudents.add(UUID.randomUUID().toString());
        attendingStudents.add(UUID.randomUUID().toString());
        attendingStudents.add(UUID.randomUUID().toString());
        learningClass.setAttendingStudents(attendingStudents);

        String learningClassJson = new Gson().toJson(learningClass);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        //Create

        when(learningClassService.create(any(LearningClassDTO.class))).thenReturn(true);

        learningClassController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPostUpdateFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Update
        when(request.getParameter("action")).thenReturn("update");

        LearningClassDTO learningClass = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Class");

        String learningClassJson = new Gson().toJson(learningClass);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);


        learningClassController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);

    }

    @Test
    void doPostUpdateSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Update
        when(request.getParameter("action")).thenReturn("update");

        LearningClassDTO learningClass = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Class");

        String learningClassJson = new Gson().toJson(learningClass);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(learningClassService.update(learningClass)).thenReturn(true);

        learningClassController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPostDeleteFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Delete
        when(request.getParameter("action")).thenReturn("delete");

        LearningClassDTO learningClass = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Class");

        String learningClassJson = new Gson().toJson(learningClass);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        learningClassController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doPostDeleteSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Delete
        when(request.getParameter("action")).thenReturn("delete");
        LearningClassDTO learningClass = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Class");

        String learningClassJson = new Gson().toJson(learningClass);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(learningClassService.delete(learningClass)).thenReturn(true).thenReturn(false);

        learningClassController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}