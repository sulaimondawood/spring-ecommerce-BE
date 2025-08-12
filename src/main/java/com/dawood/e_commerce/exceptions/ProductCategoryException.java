package com.dawood.e_commerce.exceptions;

public class ProductCategoryException extends EcommerceException{

    public ProductCategoryException(){
        super("Category name already exist");
    }

    public ProductCategoryException(String message){
        super(message);
    }

}
