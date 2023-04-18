package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminDTO;
import com.example.finalproject.service.IAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v1/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/sign-up")
    public ResponseEntity<AdminDTO> signUp(@RequestBody @Valid AdminDTO admin) {
        return new ResponseEntity<>(adminService.signUp(admin), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminDTO> login(@RequestBody @Valid AdminDTO admin) {
        return new ResponseEntity<>(adminService.login(admin), HttpStatus.OK);
    }

}
