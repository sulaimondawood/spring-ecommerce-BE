package com.dawood.e_commerce.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.dtos.request.CheckoutRequestDTO;
import com.dawood.e_commerce.entities.Cart;
import com.dawood.e_commerce.entities.CartItem;
import com.dawood.e_commerce.entities.MasterOrder;
import com.dawood.e_commerce.entities.SellerOrder;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.OrderStatus;
import com.dawood.e_commerce.enums.PaymentStatus;
import com.dawood.e_commerce.exceptions.CartException;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
import com.dawood.e_commerce.repository.MasterOrderRepository;
import com.dawood.e_commerce.repository.SellerOrderRepository;
import com.dawood.e_commerce.repository.UserRepository;
import com.dawood.e_commerce.utils.OrderUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final SellerOrderRepository sellerOrderRepository;
  private final MasterOrderRepository masterOrderRepository;
  private final UserRepository userRepository;

  public MasterOrder createOrder(CheckoutRequestDTO checkoutRequest) {

    User user = getUser();

    Cart userCart = user.getCart();

    if (userCart == null) {
      throw new CartException("Cart is empty");
    }

    MasterOrder masterOrder = new MasterOrder();
    masterOrder.setCustomer(user);
    masterOrder.setOrderId(OrderUtils.generateOrderNumber());
    masterOrder.setPaymentStatus(PaymentStatus.PENDING);
    masterOrder.setStatus(OrderStatus.PENDING);

    Map<UUID, List<CartItem>> groupVendorOrders = userCart
        .getCartItems()
        .stream()
        .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getSeller().getUuid()));

    for (Map.Entry<UUID, List<CartItem>> entry : groupVendorOrders.entrySet()) {

      SellerOrder sellerOrder = new SellerOrder();

    }

    return null;
  }

  private User getUser() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User does not exists"));

  }

}
