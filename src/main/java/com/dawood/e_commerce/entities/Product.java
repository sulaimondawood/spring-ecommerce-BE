package com.dawood.e_commerce.entities;

import com.dawood.e_commerce.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private long price;

    private long mrpPrice;

    private int discount;

    private int stockQuantity;

    private boolean inStock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToMany
    @JoinTable(
            name = "product_category_mapping",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    private List<ProductCategory> category;

    private String size;

    private String color;

    @ManyToOne
    private User seller;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItem;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
