package com.example.finalproject.config;

import com.example.finalproject.domain.Student;
import com.example.finalproject.exceptions.messages.StudentNotFoundException;
import com.example.finalproject.repository.StudentRepository;
import com.example.finalproject.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, StudentRepository studentRepository) {
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(6);

        if (Objects.equals(jwtUtil.validateJwtToken(token), Boolean.TRUE)) {
            Student student = studentRepository.findByStudentNumber(jwtUtil.getStudentNumber(token)).orElseThrow(StudentNotFoundException::new);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(student.getRole());
            Authentication authentication = new UsernamePasswordAuthenticationToken(String.valueOf(student.getStudentNumber()) , student, Collections.singletonList(grantedAuthority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("studentNumber", student.getStudentNumber());
        }
        filterChain.doFilter(request, response);
    }
}