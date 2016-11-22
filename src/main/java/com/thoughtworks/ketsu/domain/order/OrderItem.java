package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.domain.product.Product;

public class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
