package com.example.demo.service;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Categories;
import com.example.demo.repository.CategoriesRepository;
=======
import com.example.demo.entity.Categories;
import com.example.demo.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
>>>>>>> origin/sarobidy-admin

@Service
public class CategoriesService {

<<<<<<< HEAD
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
=======
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Categories saveCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> getCategoryById(Long id) {
        return categoriesRepository.findById(id);
    }

    public void deleteCategory(Long id) {
        categoriesRepository.deleteById(id);
    }
}
>>>>>>> origin/sarobidy-admin
