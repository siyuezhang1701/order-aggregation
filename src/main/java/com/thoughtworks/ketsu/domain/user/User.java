package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.Aggregation;
import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
    private long id;
    private String email;

    private Aggregation<Order> orders;

    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public Aggregation<Order> getOrders() {
        return orders;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.userUrl(User.this));
            put("id", id);
            put("email", email);
        }};
    }

    public Optional<Product> findProductById(long id){
        return null;
    }

    public Product createProduct(Map<String, Object> info){
        return null;
    }

    public List<Product> getAllProductsForUser(){
        return null;
    }

    public void changeProductPrice(Product product, double price){
        product.changePrice(price);
    }
}
