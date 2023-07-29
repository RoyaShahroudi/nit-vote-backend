package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminDTO;
import com.example.finalproject.service.IAdminService;
import com.example.finalproject.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/submit")
    public ResponseEntity<AdminDTO> submit(@RequestBody @Valid AdminDTO adminDTO) {
        AdminDTO admin =  adminService.submit(adminDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessToken(admin));
        return new ResponseEntity<>(admin, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminDTO> login(@RequestBody @Valid AdminDTO adminDTO) {
        AdminDTO admin =  adminService.login(adminDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessToken(admin));
        return new ResponseEntity<>(admin, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AdminDTO>> getAdmins() {
        return new ResponseEntity<>(adminService.getAdmins(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<AdminDTO> profile() {
        return new ResponseEntity<>(adminService.profile(), HttpStatus.OK);

    }
}
