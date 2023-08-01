package com.example.finalproject.service;

import com.example.finalproject.domain.Student;
import com.example.finalproject.dto.StudentDTO;
import com.example.finalproject.exceptions.messages.StudentDuplicateException;
import com.example.finalproject.exceptions.messages.StudentNotFoundException;
import com.example.finalproject.exceptions.messages.StudentWrongCredentialsException;
import com.example.finalproject.mapper.StudentMapper;
import com.example.finalproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IStudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public StudentDTO login(StudentDTO studentDTO) {
        Student student = studentRepository.findByStudentNumber(studentDTO.getStudentNumber())
                .orElseThrow(StudentNotFoundException::new);
        if(!Objects.equals(student.getPassword(), studentDTO.getPassword())) {
            throw new StudentWrongCredentialsException();
        }
        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO register(StudentDTO studentDTO) {
        Student student = studentRepository.findByStudentNumber(studentDTO.getStudentNumber()).orElse(null);
        studentDTO.setRole("ROLE_STUDENT");
        if (Objects.isNull(student)) {
            return studentMapper.toDTO(studentRepository.save(studentMapper.toEntity(studentDTO)));
        }
        throw new StudentDuplicateException();
    }

    @Override
    public StudentDTO profile() {
        return studentMapper.toDTO(studentRepository
                .findByStudentNumber(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(StudentNotFoundException::new));
    }
}
