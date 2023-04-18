package com.example.finalproject.service;

import com.example.finalproject.domain.Admin;
import com.example.finalproject.dto.AdminDTO;
import com.example.finalproject.mapper.AdminMapper;
import com.example.finalproject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IAdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminDTO signUp(AdminDTO adminDTO) {
        if (adminRepository.findByUsername(adminDTO.getUsername()).isEmpty()) {
            return adminMapper.toDTO(adminRepository.save(adminMapper.toEntity(adminDTO)));
        }
        //TODO throw exception
        return null;
    }

    @Override
    public AdminDTO login(AdminDTO adminDTO) {
        Admin admin = adminRepository.findByUsername(adminDTO.getUsername()).orElse(null);
        if (Objects.isNull(admin)) {
            //TODO throw exception
            return null;
        }
        //TODO check password
        return adminMapper.toDTO(admin);


    }
}
