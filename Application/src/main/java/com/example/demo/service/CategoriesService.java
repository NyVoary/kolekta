package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Categories;
import com.example.demo.repository.CategoriesRepository;

@Service
public class CategoriesService {

    private final CategoriesRepository repository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.repository = categoriesRepository;
    }

    public List<Categories> getAll() {
        return repository.findAll();
    }

    public Categories getById(int id) {
        return repository.findById(id).orElse(null);
    }
}
