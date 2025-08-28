package com.dawood.e_commerce.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderUtils {

  private static String ORDER_PREFIX = "ORD";

  private static final SecureRandom random = new SecureRandom();

  public static String generateOrderNumber() {

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    String timestamp = LocalDateTime.now().format(timeFormatter);

    return ORDER_PREFIX + "-" + timestamp + "-" + random.nextInt(999999);

  }

}
