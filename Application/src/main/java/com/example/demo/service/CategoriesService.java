package com.example.demo.service;

import com.example.demo.entity.Categories;
import com.example.demo.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Categories getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Categories save(Categories category) {
        return repository.save(category);
    }

    public List<Categories> getAllCategories() {
        return repository.findAll();
    }

    public Optional<Categories> getCategoryById(Long id) {
        return repository.findById(id);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
