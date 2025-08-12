package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private  final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getALlCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(@Valid @RequestBody ProductCategory request){
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){

        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
