package com.servlet.osf.entity;

import com.servlet.osf.services.BaseService;
import lombok.Data;

@Data
public class OSFService {
    private String name;
    private String code;
    private String label;
    private BaseService service;
}
