package com.dawood.e_commerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dawood.e_commerce.dtos.request.WishlistRequestDTO;
import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.entities.Wishlist;
import com.dawood.e_commerce.exceptions.ProductException;
import com.dawood.e_commerce.exceptions.ProductNotFoundException;
import com.dawood.e_commerce.repository.ProductRepository;
import com.dawood.e_commerce.repository.WishlistRepository;
import com.dawood.e_commerce.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService {
  private final WishlistRepository wishlistRepository;
  private final ProductRepository productRepository;
  private final UserService userService;

  private final JwtUtils jwtUtils;

  public Wishlist addToWishlist(WishlistRequestDTO request, String jwt) {

    String email = jwtUtils.extractUsername(jwt);

    User user = userService.getUserByEmail(email);

    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new ProductNotFoundException("Product does not exists"));

    Wishlist userWishlist = user.getWishlist();

    if (userWishlist == null) {
      userWishlist = new Wishlist();

      userWishlist.setCustomer(user);
      userWishlist.getProducts().add(product);
      user.setWishlist(userWishlist);
    }

    if (userWishlist.getProducts().contains(product)) {
      throw new ProductException("Product already added to wishlist");
    }

    userWishlist.getProducts().add(product);

    return wishlistRepository.save(userWishlist);

  }

  public Wishlist removeFromWishlist(WishlistRequestDTO request, String jwt) {

    String email = jwtUtils.extractUsername(jwt);

    User user = userService.getUserByEmail(email);

    Wishlist userWishlist = user.getWishlist();

    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new ProductNotFoundException("Product does not exists"));

    if (!userWishlist.getProducts().contains(product) || userWishlist == null) {
      throw new ProductException("Product not in wishlist");
    }

    userWishlist.getProducts().remove(product);

    return wishlistRepository.save(userWishlist);

  }

  public List<Product> getUserWishlist(String jwt) {
    String email = jwtUtils.extractUsername(jwt);

    User user = userService.getUserByEmail(email);

    return user.getWishlist().getProducts();
  }

  public void removeAll(String jwt) {
    String email = jwtUtils.extractUsername(jwt);

    User user = userService.getUserByEmail(email);

    Wishlist wishlist = wishlistRepository.findByCustomerUuid(user.getUuid());

    wishlistRepository.delete(wishlist);
  }
}
