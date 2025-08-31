package com.dawood.e_commerce.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.dtos.response.EcommerceMeta;
import com.dawood.e_commerce.dtos.response.order.SellerOrderPagedResponse;
import com.dawood.e_commerce.entities.SellerOrder;
import com.dawood.e_commerce.repository.SellerOrderRepository;
import com.dawood.e_commerce.utils.OrderSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerOrderService {

  private final SellerOrderRepository sellerOrderRepository;

  public SellerOrderPagedResponse getAllSellerOders(
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

}
