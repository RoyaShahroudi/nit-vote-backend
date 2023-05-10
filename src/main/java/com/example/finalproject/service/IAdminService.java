package com.example.finalproject.service;

import com.example.finalproject.dto.AdminDTO;

import java.util.List;

public interface IAdminService {
    AdminDTO submit(AdminDTO adminDTO);

    AdminDTO login(AdminDTO adminDTO);

    List<AdminDTO> getAdmins();
}

