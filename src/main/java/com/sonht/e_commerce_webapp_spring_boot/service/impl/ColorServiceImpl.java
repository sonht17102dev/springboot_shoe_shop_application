package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.Color;
import com.sonht.e_commerce_webapp_spring_boot.repository.ColorRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {

    private ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

}
