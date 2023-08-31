package org.restservice.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.services.StudentService;

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

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Nikita", "Boradulin");
    }

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get
        when(request.getParameter("studentId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(student.getStudentId());

        when(request.getParameter("action")).thenReturn("get");

        when(studentService.read(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));
        when(studentService.read(not(UUID.fromString(student.getStudentId())))).thenReturn(Optional.empty());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        stringWriter.flush();
        writer.flush();
        //Get all
        when(request.getParameter("action")).thenReturn("getAll");

        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        students.add(new Student("Test", "Robt"));
        students.add(new Student("Allla", "Laala"));

        when(studentService.findAll()).thenReturn(students);

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));
        assertTrue(stringWriter.toString().contains("Test"));
        assertTrue(stringWriter.toString().contains("Robt"));
        assertTrue(stringWriter.toString().contains("Allla"));
        assertTrue(stringWriter.toString().contains("Laala"));

        stringWriter.flush();
        writer.flush();

        //Get by first name
        when(request.getParameter("firstName")).thenReturn("Tratata").thenReturn(student.getFirstName());

        when(request.getParameter("action")).thenReturn("getByFirstName");

        when(studentService.findByFirstName(student.getFirstName())).thenReturn(Optional.of(student));
        when(studentService.findByFirstName(not(student.getFirstName()))).thenReturn(Optional.empty());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
        //Get by last name
        when(request.getParameter("lastName")).thenReturn("Tratata").thenReturn(student.getLastName());

        when(request.getParameter("action")).thenReturn("getByLastName");

        when(studentService.findByLastName(student.getLastName())).thenReturn(Optional.of(student));
        when(studentService.findByLastName(not(student.getLastName()))).thenReturn(Optional.empty());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
        //Get by full name
        when(request.getParameter("firstName")).thenReturn("Tratata").thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn("Tratata").thenReturn(student.getLastName());


        when(request.getParameter("action")).thenReturn("getByFullName");

        when(studentService.findByFullName(student.getFirstName(), student.getFirstName())).thenReturn(Optional.of(student));
        when(studentService.findByFullName(not(student.getFirstName()), not(student.getLastName()))).thenReturn(Optional.empty());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
        //Get with classes
        Set<LearningClass> learningClasses = new HashSet<>();
        learningClasses.add(new LearningClass("Total", "Hive"));
        learningClasses.add(new LearningClass("Absolute", "Power"));
        learningClasses.add(new LearningClass("Insight", "Future"));
        student.setLearningClasses(learningClasses);

        when(request.getParameter("studentId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(student.getStudentId());
        when(request.getParameter("action")).thenReturn("getWithClasses");

        when(studentService.read(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));
        when(studentService.read(not(UUID.fromString(student.getStudentId())))).thenReturn(Optional.empty());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());

        studentController.doGet(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));
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
        when(request.getParameter("firstName")).thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn(student.getLastName());

        when(request.getParameter("action")).thenReturn("create");

        when(studentService.create(any(Student.class))).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        studentController.doPost(request, response);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        //Update
        when(request.getParameter("firstName")).thenReturn("A").thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn("B").thenReturn(student.getLastName());
        when(request.getParameter("studentId")).thenReturn(student.getStudentId());

        when(request.getParameter("action")).thenReturn("update");

        when(studentService.update(student)).thenReturn(true);
        when(studentService.update(not(student))).thenReturn(false);

        studentController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        studentController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        //Delete
        when(request.getParameter("action")).thenReturn("delete");

        when(request.getParameter("firstName")).thenReturn("A").thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn("B").thenReturn(student.getLastName());
        when(request.getParameter("studentId")).thenReturn(student.getStudentId());

        when(studentService.delete(student)).thenReturn(true).thenReturn(false);
        when(studentService.delete(not(student))).thenReturn(false);

        studentController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        studentController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        studentController.doPost(request, response);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

    }
}