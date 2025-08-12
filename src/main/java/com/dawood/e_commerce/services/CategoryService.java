package com.dawood.e_commerce.services;

import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.exceptions.ProductCategoryException;
import com.dawood.e_commerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<ProductCategory> getAllCategories(){
        return categoryRepository.findAll();
    }


    public ProductCategory createCategory(ProductCategory productCategory){

        if(categoryRepository.existsByNameIgnoreCase(productCategory.getName())){
            throw new ProductCategoryException();
        }

        ProductCategory category = new ProductCategory();
        category.setName(productCategory.getName());

        return categoryRepository.save(category);

    }

    public void deleteCategory(UUID id){

        ProductCategory productCategory = categoryRepository.findById(id)
                        .orElseThrow(()->new ProductCategoryException("Product category does not exist"));

        categoryRepository.delete(productCategory);
    }

}
