package com.dawood.e_commerce.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dawood.e_commerce.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

}
