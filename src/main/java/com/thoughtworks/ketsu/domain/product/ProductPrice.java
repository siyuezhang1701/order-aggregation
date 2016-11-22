package com.thoughtworks.ketsu.domain.product;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class ProductPrice{
    private long id;
    private double price;

    public ProductPrice(long id, double price) {
        this.id = id;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
