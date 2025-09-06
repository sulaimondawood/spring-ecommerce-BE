package com.dawood.e_commerce.services;

import java.util.List;
import java.util.UUID;

import com.dawood.e_commerce.entities.Review;
import com.dawood.e_commerce.repository.ReviewRepository;
import com.dawood.e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewService {

  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;

  public List<Review> getProductReviews(UUID productId) {
    return null;
  }

}
