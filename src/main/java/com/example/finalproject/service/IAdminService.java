package com.example.finalproject.service;

import com.example.finalproject.dto.AdminDTO;

public interface IAdminService {
    AdminDTO signUp(AdminDTO adminDTO);
    AdminDTO login(AdminDTO adminDTO);
}

