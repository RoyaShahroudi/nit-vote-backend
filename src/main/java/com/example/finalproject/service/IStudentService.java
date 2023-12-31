package com.example.finalproject.service;

import com.example.finalproject.dto.StudentDTO;

public interface IStudentService {

    StudentDTO login(StudentDTO studentDTO);

    StudentDTO register(StudentDTO studentDTO);

    StudentDTO profile();
}

