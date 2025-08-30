package com.dawood.e_commerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dawood.e_commerce.dtos.request.CheckoutRequestDTO;
import com.dawood.e_commerce.entities.Address;
import com.dawood.e_commerce.entities.Cart;
import com.dawood.e_commerce.entities.CartItem;
import com.dawood.e_commerce.entities.MasterOrder;
import com.dawood.e_commerce.entities.OrderItem;
import com.dawood.e_commerce.entities.SellerOrder;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.OrderStatus;
import com.dawood.e_commerce.enums.PaymentStatus;
import com.dawood.e_commerce.exceptions.CartException;
import com.dawood.e_commerce.exceptions.order.InvalidOrderTransitionException;
import com.dawood.e_commerce.exceptions.order.OrderException;
import com.dawood.e_commerce.exceptions.order.OrderNotFoundException;
import com.dawood.e_commerce.exceptions.user.UserNotFoundException;
import com.dawood.e_commerce.repository.AddressRepository;
import com.dawood.e_commerce.repository.MasterOrderRepository;
import com.dawood.e_commerce.repository.OrderItemRepository;
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
  private final OrderItemRepository orderItemRepository;
  private final AddressRepository addressRepository;

  @Transactional
  public MasterOrder createOrder(CheckoutRequestDTO checkoutRequest) {
    // Print city for debugging (remove in production)
    System.out.println(checkoutRequest.getAddress().getCity());

    // Get authenticated user
    User user = getUser();

    // Get user's cart
    Cart userCart = user.getCart();
    if (userCart == null || userCart.getCartItems().isEmpty()) {
      throw new CartException("Cart is empty");
    }

    // Convert AddressDTO to Address entity
    Address address = new Address();
    address.setAddress(checkoutRequest.getAddress().getAddress());
    address.setCountry(checkoutRequest.getAddress().getCountry());
    address.setCity(checkoutRequest.getAddress().getCity());
    address.setState(checkoutRequest.getAddress().getState());

    // Check for existing address
    Address existingAddress = addressRepository
        .findByAddressAndCountryAndCityAndState(
            checkoutRequest.getAddress().getAddress(),
            checkoutRequest.getAddress().getCountry(),
            checkoutRequest.getAddress().getCity(),
            checkoutRequest.getAddress().getState())
        .orElseGet(() -> addressRepository.save(address));

    // Associate address with user (avoid duplicate entries in users_addresses)
    if (!user.getAddresses().contains(existingAddress)) {
      user.getAddresses().add(existingAddress);
      userRepository.save(user);
    }

    // Create MasterOrder
    MasterOrder masterOrder = new MasterOrder();
    masterOrder.setCustomer(user);
    masterOrder.setOrderId(OrderUtils.generateOrderNumber());
    masterOrder.setPaymentStatus(PaymentStatus.PENDING);
    masterOrder.setStatus(OrderStatus.PENDING);
    masterOrder.setShippingAddress(existingAddress); // Use persisted address
    masterOrder.setSellerOrders(new ArrayList<>());

    // Group cart items by vendor
    Map<UUID, List<CartItem>> groupVendorOrders = userCart.getCartItems().stream()
        .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getSeller().getUuid()));

    long totalAmount = 0;

    // Create SellerOrders for each vendor
    for (Map.Entry<UUID, List<CartItem>> entry : groupVendorOrders.entrySet()) {
      List<CartItem> cartItems = entry.getValue();

      SellerOrder sellerOrder = new SellerOrder();
      sellerOrder.setSellerOrderId(OrderUtils.generateOrderNumber());
      sellerOrder.setCustomer(user);
      sellerOrder.setOrder(masterOrder);
      sellerOrder.setShippingAddress(existingAddress); // Use persisted address
      sellerOrder.setPaymentStatus(PaymentStatus.PENDING);
      sellerOrder.setSellOrderStatus(OrderStatus.PENDING);
      sellerOrder.setTrackingCode(OrderUtils.gernerateTrackingCode()); // Fixed typo
      sellerOrder.setOrderItems(new ArrayList<>());

      for (CartItem cartItem : cartItems) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMasterOrder(masterOrder);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setSellerOrder(sellerOrder);
        orderItem.setSize(cartItem.getSize());
        orderItem.setSellingPrice(cartItem.getSellingPrice());
        orderItem.setMrpPrice(cartItem.getMrpPrice());

        sellerOrder.getOrderItems().add(orderItem);
        masterOrder.getSellerOrders().add(sellerOrder);

        totalAmount += cartItem.getSellingPrice() * cartItem.getQuantity();
      }

      // Save SellerOrder and its OrderItems
      sellerOrderRepository.save(sellerOrder);
      for (OrderItem orderItem : sellerOrder.getOrderItems()) {
        orderItemRepository.save(orderItem);
      }
    }

    masterOrder.setTotalAmount(totalAmount);

    // Payment service integration (to be implemented)
    // e.g., paymentService.processPayment(masterOrder);

    // Save and return MasterOrder
    return masterOrderRepository.save(masterOrder);
  }
  // public MasterOrder createOrder(CheckoutRequestDTO checkoutRequest) {

  // System.out.println(checkoutRequest.getAddress().getCity());

  // User user = getUser();

  // Cart userCart = user.getCart();

  // if (userCart == null) {
  // throw new CartException("Cart is empty");
  // }

  // Address existingAddress = addressRepository
  // .findByAddressAndCountryAndCityAndState(checkoutRequest.getAddress().getAddress(),
  // checkoutRequest.getAddress().getCountry(),
  // checkoutRequest.getAddress().getCity(),
  // checkoutRequest.getAddress().getState())
  // .orElseGet(
  // () -> addressRepository.save(checkoutRequest.getAddress()));

  // if (!user.getAddresses().contains(existingAddress)) {
  // user.getAddresses().add(existingAddress);
  // userRepository.save(user); // Updates users_addresses
  // }

  // MasterOrder masterOrder = new MasterOrder();
  // masterOrder.setCustomer(user);
  // masterOrder.setOrderId(OrderUtils.generateOrderNumber());
  // masterOrder.setPaymentStatus(PaymentStatus.PENDING);
  // masterOrder.setStatus(OrderStatus.PENDING);
  // masterOrder.setShippingAddress(existingAddress);

  // long totalAmount = 0;

  // Map<UUID, List<CartItem>> groupVendorOrders = userCart
  // .getCartItems()
  // .stream()
  // .collect(Collectors.groupingBy(cartItem ->
  // cartItem.getProduct().getSeller().getUuid()));

  // for (Map.Entry<UUID, List<CartItem>> entry : groupVendorOrders.entrySet()) {

  // List<CartItem> cartItems = entry.getValue();

  // SellerOrder sellerOrder = new SellerOrder();
  // sellerOrder.setSellerOrderId(OrderUtils.generateOrderNumber());
  // sellerOrder.setCustomer(user);
  // sellerOrder.setOrder(masterOrder);
  // sellerOrder.setShippingAddress(checkoutRequest.getAddress());
  // sellerOrder.setPaymentStatus(PaymentStatus.PENDING);
  // sellerOrder.setSellOrderStatus(OrderStatus.PENDING);
  // sellerOrder.setTrackingCode(OrderUtils.gernerateTrackingCode());

  // for (CartItem cartItem : cartItems) {

  // OrderItem orderItem = new OrderItem();

  // orderItem.setMasterOrder(masterOrder);
  // orderItem.setProduct(cartItem.getProduct());
  // orderItem.setQuantity(cartItem.getQuantity());
  // orderItem.setSellerOrder(sellerOrder);
  // orderItem.setSize(cartItem.getSize());
  // orderItem.setSellingPrice(cartItem.getSellingPrice());
  // orderItem.setMrpPrice(cartItem.getMrpPrice());
  // orderItem.getSellerOrder().getOrderItems().add(orderItem);

  // sellerOrder.getOrderItems().add(orderItem);
  // totalAmount += cartItem.getSellingPrice() * cartItem.getQuantity();
  // sellerOrderRepository.save(sellerOrder);
  // orderItemRepository.save(orderItem);
  // masterOrder.getSellerOrders().add(sellerOrder);

  // }

  // // Save SellerOrder and its OrderItems
  // sellerOrderRepository.save(sellerOrder);
  // for (OrderItem orderItem : sellerOrder.getOrderItems()) {
  // orderItemRepository.save(orderItem);
  // }

  // }
  // // masterOrder.getSellerOrders(se);
  // masterOrder.setTotalAmount(totalAmount);

  // // Payment service here
  // return masterOrderRepository.save(masterOrder);

  // }

  public void cancelOrder(UUID orderId) {

    SellerOrder sellerOrder = sellerOrderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

    User user = getUser();

    if (!user.equals(sellerOrder.getCustomer())) {
      throw new OrderException("Order belongs to a different user");
    }

    if (!OrderUtils.isValidOrderStatusTransition(sellerOrder.getSellOrderStatus(), OrderStatus.CANCELLED)) {
      throw new InvalidOrderTransitionException("Order status cannot be changed");
    }

    MasterOrder masterOrder = sellerOrder.getOrder();

    masterOrder.setStatus(OrderStatus.CANCELLED);
    sellerOrder.setSellOrderStatus(OrderStatus.CANCELLED);

    sellerOrderRepository.save(sellerOrder);
    masterOrderRepository.save(masterOrder);

    // Payment functionality here
    // Refund Sevice

  }

  public MasterOrder updateMasterOrder(UUID orderId, OrderStatus status) {
    MasterOrder masterOrder = getOrderById(orderId);

    SellerOrder sellerOrder = sellerOrderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

    if (!OrderUtils.isValidOrderStatusTransition(masterOrder.getStatus(), status)) {
      throw new InvalidOrderTransitionException("Order status cannot be changed");
    }

    masterOrder.setStatus(status);
    sellerOrder.setSellOrderStatus(status);

    sellerOrderRepository.save(sellerOrder);
    masterOrderRepository.save(masterOrder);

    return masterOrder;

  }

  public MasterOrder getOrderById(UUID orderId) {
    return masterOrderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order does not exists"));
  }

  private User getUser() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User does not exists"));

  }

}
