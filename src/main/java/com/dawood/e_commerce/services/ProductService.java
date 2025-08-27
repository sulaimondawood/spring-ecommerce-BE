package com.dawood.e_commerce.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.dtos.response.EcommerceMeta;
import com.dawood.e_commerce.dtos.response.ProductPaginationResponse;
import com.dawood.e_commerce.dtos.response.ProductResponseDTO;
import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.enums.ProductStatus;
import com.dawood.e_commerce.exceptions.ProductException;
import com.dawood.e_commerce.mapper.ProductMapper;
import com.dawood.e_commerce.repository.CategoryRepository;
import com.dawood.e_commerce.repository.ProductRepository;
import com.dawood.e_commerce.utils.ProductSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(ProductService.class);

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductPaginationResponse getAllProducts(
      int pageNumber,
      int pageSize,
      UUID categoryId,
      String name,
      String size,
      String description,
      ProductStatus status,
      Long minPrice,
      Long maxPrice) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    ProductCategory category = null;

    if (categoryId != null) {
      category = categoryRepository.findById(categoryId)
          .orElseThrow(() -> new ProductException("Product category is invalid"));
    }

    Specification<Product> specification = ProductSpecification.hasCategory(category)
        .and(ProductSpecification.containsName(name))
        .and(ProductSpecification.containsDescription(description))
        .and(ProductSpecification.hasSize(size))
        .and(ProductSpecification.priceBetween(minPrice, maxPrice))
        .and(ProductSpecification.hasStatus(status));

    Page<Product> pagedProducts = productRepository.findAll(specification, pageable);

    EcommerceMeta meta = EcommerceMeta.builder()
        .hasPrev(pagedProducts.hasPrevious())
        .hasNext(pagedProducts.hasNext())
        .pageNumber(pagedProducts.getNumber())
        .pageSize(pageable.getPageSize())
        .totalPages(pagedProducts.getTotalPages())
        .build();

    List<ProductResponseDTO> content = pagedProducts.getContent()
        .stream()
        .map(ProductMapper::toDTO).toList();

    ProductPaginationResponse products = ProductPaginationResponse
        .builder()
        .content(content)
        .meta(meta)
        .build();

    return products;

  }

  public ProductPaginationResponse searchProduct(String searchQuery, int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    System.out.println(searchQuery);

    Specification<Product> specification = ProductSpecification.containsDescription(searchQuery)
        .or(ProductSpecification.containsName(searchQuery));

    Page<Product> pagedProducts = productRepository.findAll(specification, pageable);

    List<ProductResponseDTO> products = pagedProducts.getContent()
        .stream()
        .map(ProductMapper::toDTO)
        .toList();

    EcommerceMeta meta = EcommerceMeta
        .builder()
        .hasNext(pagedProducts.hasNext())
        .hasPrev(pagedProducts.hasPrevious())
        .pageNumber(pagedProducts.getNumber())
        .pageSize(pagedProducts.getSize())
        .totalPages(pagedProducts.getTotalPages())
        .build();

    ProductPaginationResponse response = ProductPaginationResponse.builder()
        .content(products)
        .meta(meta)
        .build();

    return response;
  }

}
