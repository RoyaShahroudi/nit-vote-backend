package com.example.finalproject.mapper;

import com.example.finalproject.domain.Admin;
import com.example.finalproject.dto.AdminDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AdminMapper {
    public Admin toEntity(AdminDTO adminDTO) {
        if (Objects.isNull(adminDTO)) return null;
        return new Admin()
                .setId(adminDTO.getId())
                .setUsername(adminDTO.getUsername())
                .setPassword(adminDTO.getPassword())
                .setRole(adminDTO.getRole());
    }

    public AdminDTO toDTO(Admin admin) {
        if (Objects.isNull(admin)) return null;
        return new AdminDTO()
                .setId(admin.getId())
                .setUsername(admin.getUsername())
                .setPassword(admin.getPassword())
                .setRole(admin.getRole());
    }

}
