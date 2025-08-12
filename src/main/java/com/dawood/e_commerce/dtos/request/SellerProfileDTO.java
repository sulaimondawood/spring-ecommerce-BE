package com.dawood.e_commerce.dtos.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProfileDTO {

    @Valid private BusinessDetailsDTO businessDetails;

    @Valid private BankDetailsDTO bankDetails;
}
