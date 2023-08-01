package com.example.finalproject;

import com.example.finalproject.domain.Admin;
import com.example.finalproject.dto.AdminDTO;
import com.example.finalproject.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
class AdminAuthenticationTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void afterEach() {
        adminRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        adminRepository.deleteAll();
    }

    @Test
    void adminRegister_Ok() throws Exception {
        AdminDTO adminDTO = new AdminDTO().setUsername("1234").setPassword("1234");
        MvcResult result = mockMvc
                .perform(post("/v1/admin/submit")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(adminDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(adminRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void adminLogin_Ok() throws Exception {
        AdminDTO adminDTO = new AdminDTO().setUsername("1234").setPassword("1234");
        adminRepository.save(new Admin().setUsername("1234").setPassword("1234"));
        MvcResult result = mockMvc
                .perform(post("/v1/admin/login")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(adminDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(result.getResponse().getStatus()).isEqualTo(200);
        AdminDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), AdminDTO.class);
        then(response.getUsername()).isEqualTo("1234");
    }

    @Test
    void adminLogin_wrong_password() throws Exception {
        AdminDTO adminDTO = new AdminDTO().setUsername("1234").setPassword("12345");
        adminRepository.save(new Admin().setUsername("1234").setPassword("1234"));
        MvcResult result = mockMvc
                .perform(post("/v1/admin/login")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(adminDTO))
                        .with(csrf())
                )
                .andReturn();

        then(result).isNotNull();
        then(result.getResponse().getStatus()).isEqualTo(400);
    }
}
