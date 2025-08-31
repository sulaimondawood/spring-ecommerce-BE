package com.dawood.e_commerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.dtos.response.EcommerceMeta;
import com.dawood.e_commerce.dtos.response.order.SellerOrderPagedResponse;
import com.dawood.e_commerce.entities.MasterOrder;
import com.dawood.e_commerce.entities.SellerOrder;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.OrderStatus;
import com.dawood.e_commerce.exceptions.order.InvalidOrderTransitionException;
import com.dawood.e_commerce.exceptions.order.OrderNotFoundException;
import com.dawood.e_commerce.exceptions.user.UserNotFoundException;
import com.dawood.e_commerce.repository.MasterOrderRepository;
import com.dawood.e_commerce.repository.SellerOrderRepository;
import com.dawood.e_commerce.repository.UserRepository;
import com.dawood.e_commerce.utils.OrderSpecification;
import com.dawood.e_commerce.utils.OrderUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerOrderService {

  private final SellerOrderRepository sellerOrderRepository;
  private final UserRepository userRepository;
  private final MasterOrderRepository masterOrderRepository;

  public SellerOrderPagedResponse getAllSellerOrders(
      int pageNo, int pageSize, String query) {

    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Specification<SellerOrder> spec = OrderSpecification.hasSellerOrderId(query);

    Page<SellerOrder> pagedOrders = sellerOrderRepository.findAll(spec, pageable);

    EcommerceMeta meta = new EcommerceMeta();
    meta.setHasNext(pagedOrders.hasNext());
    meta.setHasPrev(pagedOrders.hasPrevious());
    meta.setPageNumber(pagedOrders.getNumber());
    meta.setPageSize(pagedOrders.getSize());
    meta.setTotalPages(pagedOrders.getTotalPages());

    SellerOrderPagedResponse response = new SellerOrderPagedResponse();
    response.setMeta(meta);
    response.setOrders(pagedOrders.getContent());

    return response;
  }

  public SellerOrder getSellerOrderById(UUID id) {
    return sellerOrderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException("Order not found"));
  }

  public SellerOrder updateSellerOrder(UUID orderId, OrderStatus status) {

    User currentUser = getUser();
    SellerOrder sellerOrder = getSellerOrderById(orderId);

    MasterOrder masterOrder = masterOrderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

    if (!OrderUtils.isValidOrderStatusTransition(sellerOrder.getSellOrderStatus(), status)) {
      throw new InvalidOrderTransitionException("Order status cannot be changed");
    }

    if (!sellerOrder.getSellerId().equals(currentUser.getUuid())) {
      throw new SecurityException("You are not authorized to update this order.");
    }

    masterOrder.setStatus(status);
    sellerOrder.setSellOrderStatus(status);

    sellerOrderRepository.save(sellerOrder);
    masterOrderRepository.save(masterOrder);

    return sellerOrder;

  }

  public SellerOrderPagedResponse getSellerHistory(
      int pageNo, int pageSize, String query) {

    User currentUser = getUser();

    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Specification<SellerOrder> specification = OrderSpecification.hasSellerId(currentUser.getUuid());

    Page<SellerOrder> pagedSellerOrder = sellerOrderRepository.findAll(specification, pageable);

    EcommerceMeta meta = new EcommerceMeta();
    meta.setHasNext(pagedSellerOrder.hasNext());
    meta.setHasPrev(pagedSellerOrder.hasPrevious());
    meta.setPageNumber(pagedSellerOrder.getNumber());
    meta.setPageSize(pagedSellerOrder.getSize());
    meta.setTotalPages(pagedSellerOrder.getTotalPages());

    SellerOrderPagedResponse response = new SellerOrderPagedResponse();
    response.setMeta(meta);
    response.setOrders(pagedSellerOrder.getContent());

    return response;
  }

  public SellerOrder cancelSellerOrder(UUID orderId) {

    User currentUser = getUser();
    SellerOrder sellerOrder = getSellerOrderById(orderId);

    MasterOrder masterOrder = masterOrderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

    if (!OrderUtils.isValidOrderStatusTransition(sellerOrder.getSellOrderStatus(), status)) {
      throw new InvalidOrderTransitionException("Order status cannot be changed");
    }

    if (!sellerOrder.getSellerId().equals(currentUser.getUuid())) {
      throw new SecurityException("You are not authorized to update this order.");
    }

    masterOrder.setStatus(OrderStatus.CANCELLED);
    sellerOrder.setSellOrderStatus(OrderStatus.CANCELLED);

    sellerOrderRepository.save(sellerOrder);
    masterOrderRepository.save(masterOrder);

    return sellerOrder;

  }

  private User getUser() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User does not exists"));

  }

}
