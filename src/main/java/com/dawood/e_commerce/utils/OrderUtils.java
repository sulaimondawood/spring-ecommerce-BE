package com.dawood.e_commerce.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dawood.e_commerce.entities.OrderItem;
import com.dawood.e_commerce.enums.OrderStatus;

public class OrderUtils {
  private static final SecureRandom random = new SecureRandom();

  public static String gernerateTrackingCode() {

    final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXWZ0123456789";
    StringBuilder trackingCode = new StringBuilder();
    for (int i = 0; i <= 8; i++) {
      trackingCode.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
    }

    return trackingCode.toString();

  }

  public static String generateOrderNumber() {
    final String ORDER_PREFIX = "ORD";

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    String timestamp = LocalDateTime.now().format(timeFormatter);

    return ORDER_PREFIX + "-" + timestamp + "-" + random.nextInt(999999);

  }

  public static long calculateTotalAmount(List<OrderItem> orderItems) {
    return orderItems.stream().mapToLong(item -> item.getSellingPrice() * item.getQuantity()).sum();

  }

  public static boolean isValidOrderStatusTransition(OrderStatus currentStatus, OrderStatus nextStatus) {

    switch (currentStatus) {

      case PENDING:
        return nextStatus == OrderStatus.CANCELLED || nextStatus == OrderStatus.PROCESSING;

      case PROCESSING:
        return nextStatus == OrderStatus.CANCELLED || nextStatus == OrderStatus.SHIPPED;

      case SHIPPED:
        return nextStatus == OrderStatus.DELIVERED;

      case DELIVERED:
        return false;

      case CANCELLED:
        return false;

      default:
        return false;

    }
  }

}
