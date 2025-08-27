package com.dawood.e_commerce.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.entities.Cart;
import com.dawood.e_commerce.entities.CartItem;
import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.exceptions.ProductException;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
import com.dawood.e_commerce.repository.CartItemRepository;
import com.dawood.e_commerce.repository.CartRepository;
import com.dawood.e_commerce.repository.ProductRepository;
import com.dawood.e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;
  private final CartRepository cartRepository;

  public Cart addToCart(UUID productId, int quantity) {

    Cart userCart = getUserCart();

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductException("Product does not exists"));

    if (quantity >= product.getStockQuantity()) {
      throw new ProductException("Product quantity is more than available stock quantity");
    }

    Optional<CartItem> cartItemProduct = userCart.getCartItems()
        .stream()
        .findFirst()
        .filter(cartItem -> cartItem.getProduct().getId().equals(productId));

    if (cartItemProduct.isPresent()) {
      int newQty = cartItemProduct.get().getQuantity() + quantity;
      if (newQty < product.getStockQuantity()) {
        throw new ProductException("Insufficient stock");
      }
      cartItemProduct.get().setQuantity(newQty);
    } else {
      CartItem newCartItem = new CartItem();
      newCartItem.setQuantity(quantity);
      newCartItem.setProduct(product);
      newCartItem.setMrpPrice(product.getMrpPrice());
      newCartItem.setSellingPrice(product.getPrice());
      newCartItem.setCart(userCart);
      newCartItem.setSize(product.getSize());

      userCart.getCartItems().add(newCartItem);
    }

    return userCart;
  }

  public Cart updateCart(UUID productId, int quantity) {

    Cart userCart = getUserCart();

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductException("Product not found"));

    CartItem existingCartItem = userCart.getCartItems()
        .stream()
        .filter(cartItem -> cartItem.getProduct().equals(product))
        .findFirst()
        .orElseThrow(() -> new ProductException("Product not found"));

    if (quantity > product.getStockQuantity()) {
      throw new ProductException("Insufficient stock");
    }

    if (existingCartItem.getQuantity() <= 0) {
      userCart.getCartItems().remove(existingCartItem);
      existingCartItem.setCart(null);
      cartItemRepository.delete(existingCartItem);

    }

    existingCartItem.setQuantity(quantity);

    cartItemRepository.save(existingCartItem);

    return cartRepository.save(userCart);

  }

  public void deleteCartItem(UUID cartItemId) {

    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new ProductException("Cart Item not found"));

    cartItemRepository.delete(cartItem);

  }

  private Cart getUserCart() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    Cart userCart = user.getCart();

    if (userCart == null) {
      userCart = new Cart();
    }

    return userCart;
  }

}
