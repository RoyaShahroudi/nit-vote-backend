package com.example.finalproject.service;

import com.example.finalproject.domain.Student;
import com.example.finalproject.dto.StudentDTO;
import com.example.finalproject.exceptions.messages.StudentDuplicateException;
import com.example.finalproject.exceptions.messages.StudentNotFoundException;
import com.example.finalproject.mapper.StudentMapper;
import com.example.finalproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return studentMapper.toDTO(
                studentRepository.findByStudentNumber(studentDTO.getStudentNumber())
                        .orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public StudentDTO register(StudentDTO studentDTO) {
        Student student = studentRepository.findByStudentNumber(studentDTO.getStudentNumber()).orElse(null);
        if (Objects.isNull(student)) {
            return studentMapper.toDTO(studentRepository.save(studentMapper.toEntity(studentDTO)));
        }
        throw new StudentDuplicateException();
    }

    @Override
    public StudentDTO profile() {
        return null;
    }
}
