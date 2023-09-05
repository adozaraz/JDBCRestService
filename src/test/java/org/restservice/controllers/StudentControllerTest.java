package org.restservice.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.services.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private StudentController studentController;

    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Nikita", "Boradulin");
        this.studentService = mock(StudentService.class);
        this.studentController = new StudentController(studentService);
    }

    @Test
    void doGetDataFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get
        when(request.getParameter("studentId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(student.getStudentId());
        when(request.getParameter("action")).thenReturn("get");

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doGetDataSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get
        when(request.getParameter("studentId")).thenReturn(student.getStudentId());

        when(request.getParameter("action")).thenReturn("get");

        when(studentService.read(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doGetAll() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get all
        when(request.getParameter("action")).thenReturn("getAll");

        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        students.add(new Student("Test", "Robt"));
        students.add(new Student("Allla", "Laala"));

        when(studentService.findAll()).thenReturn(students);

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));
        assertTrue(stringWriter.toString().contains("Test"));
        assertTrue(stringWriter.toString().contains("Robt"));
        assertTrue(stringWriter.toString().contains("Allla"));
        assertTrue(stringWriter.toString().contains("Laala"));

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doGetByFirstNameFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by first name
        when(request.getParameter("firstName")).thenReturn("Tratata").thenReturn(student.getFirstName());
        when(request.getParameter("action")).thenReturn("getByFirstName");

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetByFirstNameSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by first name
        when(request.getParameter("firstName")).thenReturn(student.getFirstName());
        when(request.getParameter("action")).thenReturn("getByFirstName");

        when(studentService.findByFirstName(student.getFirstName())).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetByLastNameFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by last name
        when(request.getParameter("lastName")).thenReturn("Tratata").thenReturn(student.getLastName());
        when(request.getParameter("action")).thenReturn("getByLastName");

        when(studentService.findByLastName(student.getLastName())).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetByLastNameSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by last name
        when(request.getParameter("lastName")).thenReturn(student.getLastName());
        when(request.getParameter("action")).thenReturn("getByLastName");

        when(studentService.findByLastName(student.getLastName())).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetByFullNameFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by full name
        when(request.getParameter("firstName")).thenReturn("Tratata").thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn("Tratata").thenReturn(student.getLastName());


        when(request.getParameter("action")).thenReturn("getByFullName");

        when(studentService.findByFullName(student.getFirstName(), student.getFirstName())).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetByFullNameSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get by full name
        when(request.getParameter("firstName")).thenReturn(student.getFirstName());
        when(request.getParameter("lastName")).thenReturn(student.getLastName());

        when(request.getParameter("action")).thenReturn("getByFullName");

        when(studentService.findByFullName(student.getFirstName(), student.getLastName())).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(stringWriter.toString().contains(student.getStudentId()));
        assertTrue(stringWriter.toString().contains(student.getFirstName()));
        assertTrue(stringWriter.toString().contains(student.getLastName()));

        writer.flush();
        stringWriter.flush();
    }

    @Test
    void doGetWithClassesFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get with classes
        Set<LearningClass> learningClasses = new HashSet<>();
        learningClasses.add(new LearningClass("Total", "Hive"));
        learningClasses.add(new LearningClass("Absolute", "Power"));
        learningClasses.add(new LearningClass("Insight", "Future"));
        student.setLearningClasses(learningClasses);

        when(request.getParameter("studentId")).thenReturn(String.valueOf(UUID.randomUUID())).thenReturn(student.getStudentId());
        when(request.getParameter("action")).thenReturn("getWithClasses");

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        stringWriter.flush();
        writer.flush();
    }

    @Test
    void doGetWithClassesSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //Get with classes
        Set<LearningClass> learningClasses = new HashSet<>();
        learningClasses.add(new LearningClass("Total", "Hive"));
        learningClasses.add(new LearningClass("Absolute", "Power"));
        learningClasses.add(new LearningClass("Insight", "Future"));
        student.setLearningClasses(learningClasses);

        when(request.getParameter("studentId")).thenReturn(student.getStudentId());
        when(request.getParameter("action")).thenReturn("getWithClasses");

        when(studentService.getStudentWithAttendingClasses(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));

        studentController.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
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
    void doPostCreate() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Create
        StudentDTO student = new StudentDTO(UUID.randomUUID().toString(), "Nikita", "Boradulin");

        String learningClassJson = new Gson().toJson(student);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(request.getParameter("action")).thenReturn("create");

        when(studentService.create(any(StudentDTO.class))).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        studentController.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPostUpdateFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Update
        StudentDTO student = new StudentDTO(UUID.randomUUID().toString(), "Nikita", "Boradulin");

        String learningClassJson = new Gson().toJson(student);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(request.getParameter("action")).thenReturn("update");

        studentController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doPostUpdateSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Update
        StudentDTO student = new StudentDTO(UUID.randomUUID().toString(), "Nikita", "Boradulin");

        String learningClassJson = new Gson().toJson(student);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(request.getParameter("action")).thenReturn("update");

        when(studentService.update(student)).thenReturn(true);

        studentController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPostDeleteFail() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Delete
        when(request.getParameter("action")).thenReturn("delete");

        StudentDTO student = new StudentDTO(UUID.randomUUID().toString(), "Nikita", "Boradulin");

        String learningClassJson = new Gson().toJson(student);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        studentController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        

    }

    @Test
    void doPostDeleteSuccess() throws ServletException, IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        //Delete
        when(request.getParameter("action")).thenReturn("delete");

        StudentDTO student = new StudentDTO(UUID.randomUUID().toString(), "Nikita", "Boradulin");

        String learningClassJson = new Gson().toJson(student);

        Reader inputString = new StringReader(learningClassJson);
        BufferedReader reader = new BufferedReader(inputString);
        when(request.getReader()).thenReturn(reader);

        when(studentService.delete(student)).thenReturn(true);


        studentController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);


    }
}