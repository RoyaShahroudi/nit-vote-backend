package com.example.finalproject.controller;

import com.example.finalproject.dto.StudentDTO;
import com.example.finalproject.service.IStudentService;
import com.example.finalproject.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v1/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<StudentDTO> login(@RequestBody @Valid StudentDTO studentDTO) {
        StudentDTO student =  studentService.login(studentDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessToken(student));
        return new ResponseEntity<>(student, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody @Valid StudentDTO studentDTO) {
        StudentDTO student =  studentService.register(studentDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessToken(student));
        return new ResponseEntity<>(student, httpHeaders, HttpStatus.CREATED);
    }
}
