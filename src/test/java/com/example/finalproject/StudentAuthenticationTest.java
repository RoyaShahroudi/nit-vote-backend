package com.example.finalproject;

import com.example.finalproject.domain.Student;
import com.example.finalproject.dto.StudentDTO;
import com.example.finalproject.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = SecurityFilterAutoConfiguration.class)
@AutoConfigureMockMvc
class StudentAuthenticationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        studentRepository.deleteAll();
    }

    @Test
    void studentRegister_Ok() throws Exception {
        StudentDTO studentDTO = new StudentDTO().setStudentNumber("1234").setPassword("1234");
        MvcResult result = mockMvc
                .perform(post("/v1/student/register")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(studentDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(studentRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void studentLogin_Ok() throws Exception {
        StudentDTO studentDTO = new StudentDTO().setStudentNumber("1234").setPassword("1234");
        studentRepository.save(new Student().setStudentNumber("1234").setPassword("1234"));
        MvcResult result = mockMvc
                .perform(post("/v1/student/login")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(studentDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(result.getResponse().getStatus()).isEqualTo(200);
        StudentDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), StudentDTO.class);
        then(response.getStudentNumber()).isEqualTo("1234");
    }

    @Test
    void studentLogin_wrong_password() throws Exception {
        StudentDTO studentDTO = new StudentDTO().setStudentNumber("1234").setPassword("12345");
        studentRepository.save(new Student().setStudentNumber("1234").setPassword("1234"));
        MvcResult result = mockMvc
                .perform(post("/v1/student/login")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(studentDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(result.getResponse().getStatus()).isEqualTo(400);
    }
}
