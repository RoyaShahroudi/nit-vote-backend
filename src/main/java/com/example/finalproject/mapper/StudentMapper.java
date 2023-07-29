package com.example.finalproject.mapper;

import com.example.finalproject.domain.Student;
import com.example.finalproject.dto.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StudentMapper {
    public Student toEntity(StudentDTO studentDTO) {
        if (Objects.isNull(studentDTO)) return null;
        return new Student()
                .setId(studentDTO.getId())
                .setStudentNumber(studentDTO.getStudentNumber())
                .setPassword(studentDTO.getPassword())
                .setRole(studentDTO.getRole());
    }

    public StudentDTO toDTO(Student student) {
        if (Objects.isNull(student)) return null;
        return new StudentDTO()
                .setId(student.getId())
                .setStudentNumber(student.getStudentNumber())
                .setPassword(student.getPassword())
                .setRole(student.getRole());
    }

}
