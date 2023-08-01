package com.example.finalproject.service;

import com.example.finalproject.domain.Admin;
import com.example.finalproject.dto.AdminDTO;
import com.example.finalproject.exceptions.messages.AdminDuplicateException;
import com.example.finalproject.exceptions.messages.AdminNotFoundException;
import com.example.finalproject.exceptions.messages.WrongCredentialsException;
import com.example.finalproject.mapper.AdminMapper;
import com.example.finalproject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IAdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminDTO submit(AdminDTO adminDTO) {
        adminDTO.setRole("ROLE_ADMIN");
        if (adminRepository.findByUsername(adminDTO.getUsername()).isEmpty()) {
            return adminMapper.toDTO(adminRepository.save(adminMapper.toEntity(adminDTO)));
        }
        throw new AdminDuplicateException();
    }

    @Override
    public AdminDTO login(AdminDTO adminDTO) {
        Admin admin = adminRepository.findByUsername(adminDTO.getUsername()).orElseThrow(AdminNotFoundException::new);
        if(!Objects.equals(admin.getPassword(), adminDTO.getPassword())) {
            throw new WrongCredentialsException();
        }
        return adminMapper.toDTO(admin);
    }

    @Override
    public List<AdminDTO> getAdmins() {
        return adminRepository.findAll().stream().map(admin -> adminMapper.toDTO(admin)).toList();
    }

    @Override
    public AdminDTO profile() {
        return adminMapper.toDTO(adminRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(AdminNotFoundException::new));
    }
}
