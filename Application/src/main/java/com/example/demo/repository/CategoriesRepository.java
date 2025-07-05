package com.example.demo.repository;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Categories;

@Repository
<<<<<<< HEAD
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{}

=======
public interface CategoriesRepository extends JpaRepository<Categories, Long>{

}
>>>>>>> origin/sarobidy-admin
