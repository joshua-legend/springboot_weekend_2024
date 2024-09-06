package com.example.springIs0902.Constants;

public enum Message {
    PRODUCT_NOT_FOUND("There is no product with id: "),
    INVALID_PRODUCT_ID("Invalid product id: "),
    OUT_OF_STOCK("The product is out of stock.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
