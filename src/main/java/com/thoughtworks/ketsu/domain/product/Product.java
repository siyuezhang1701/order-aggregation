package com.thoughtworks.ketsu.domain.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Product {
    private long id;
    private String name;
    private List<ProductPrice> priceHistory;

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        priceHistory = new ArrayList<ProductPrice>(){{
            add(new ProductPrice(0, price));
        }};
    }
}
