package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.request.ProductRequestDTO;
import com.dawood.e_commerce.dtos.request.ProductUpdateRequestDTO;
import com.dawood.e_commerce.dtos.response.EcommerceMeta;
import com.dawood.e_commerce.dtos.response.ProductPaginationResponse;
import com.dawood.e_commerce.dtos.response.ProductResponseDTO;
import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.ProductStatus;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.exceptions.ProductException;
import com.dawood.e_commerce.exceptions.ProductNotFoundException;
import com.dawood.e_commerce.mapper.ProductMapper;
import com.dawood.e_commerce.repository.CategoryRepository;
import com.dawood.e_commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        SecurityContext ctx = SecurityContextHolder.getContext();
        String email = ctx.getAuthentication().getName();

        User seller = userService.getUserByEmail(email);

        if (!seller.getRole().equals(UserRole.SELLER)) {
            throw new SecurityException("Only seller can create a product");
        }

        List<UUID> categoryIds = requestDTO.getCategory();

        List<ProductCategory> categories = categoryRepository.findAllById(categoryIds);

        Product product = Product.builder()
                .color(requestDTO.getColor())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .name(requestDTO.getName())
                .discount(calculateDiscount(requestDTO.getMrpPrice(), requestDTO.getPrice()))
                .inStock(requestDTO.getStockQuantity() > 0)
                .images(requestDTO.getImages())
                .mrpPrice(requestDTO.getMrpPrice())
                .stockQuantity(requestDTO.getStockQuantity())
                .size(requestDTO.getSize())
                .seller(seller)
                .category(categories)
                .status(ProductStatus.IN_STOCK)
                .build();

        return ProductMapper.toDTO(productRepository.save(product));
    }

    public void deleteProduct(UUID productId) {
        Product product = ProductMapper.toModel(getSingleProduct(productId));
        productRepository.delete(product);
    }

    public ProductResponseDTO getSingleProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());

        return ProductMapper.toDTO(product);
    }

    public ProductResponseDTO updateProduct(ProductUpdateRequestDTO requestDTO, UUID productId) {

        SecurityContext ctx = SecurityContextHolder.getContext();
        String email = ctx.getAuthentication().getName();

        User seller = userService.getUserByEmail(email, "User does not have a seller account");

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());

        System.out.println(existingProduct.getDescription());

        if (!seller.getUuid().equals(existingProduct.getSeller().getUuid())) {
            throw new ProductException();
        }

        List<ProductCategory> categories;

        if (requestDTO.getCategory() != null) {
            categories = categoryRepository.findAllById(requestDTO.getCategory());
        } else {
            categories = existingProduct.getCategory();
        }

        existingProduct.setName(requestDTO.getName());
        existingProduct.setDescription(requestDTO.getDescription());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setMrpPrice(requestDTO.getMrpPrice());
        existingProduct.setStockQuantity(requestDTO.getStockQuantity());
        existingProduct.setStatus(requestDTO.getStatus());
        existingProduct.setCategory(categories);
        existingProduct.setSize(requestDTO.getSize());
        existingProduct.setColor(requestDTO.getColor());
        existingProduct.setImages(requestDTO.getImages());

        return ProductMapper.toDTO(productRepository.save(existingProduct));
    }

    public ProductPaginationResponse getAllProducts(int pageSize,
            int pageNumber) {

        SecurityContext ctx = SecurityContextHolder.getContext();

        String email = ctx.getAuthentication().getName();

        User seller = userService.getUserByEmail(email, "Seller does not exists");

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> pagedProduct = productRepository.findAllBySellerOrderByCreatedAtDesc(seller, pageable)
                .getContent();

        EcommerceMeta meta = EcommerceMeta
                .builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .hasPrev(pageable.hasPrevious())
                .build();

        ProductPaginationResponse products = ProductPaginationResponse.builder()
                .content(pagedProduct.stream().map(ProductMapper::toDTO).toList())
                .meta(meta)
                .build();

        return products;

    }

    private int calculateDiscount(long mrpPrice, long price) {

        if (mrpPrice <= 0)
            return 0;

        double discount = mrpPrice - price;

        double discountPercentage = ((discount / mrpPrice)) * 100;

        return (int) Math.round(discountPercentage);
    }

}
