package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Order implements Record{
    private long id;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private User owner;
    private Payment payment;

    public Order(long id, User owner) {
        this.id = id;
        this.owner = owner;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.orderUrl(owner, Order.this));
            put("total", totalPrice);
        }};
    }

    public long getId() {
        return id;
    }


    public User getOwner() {
        return owner;
    }

    public Optional<Payment> findPayment(){
        return null;
    }

    public Payment createPayment(Map<String, Object>info){
        return null;
    }

    public Optional<RefundRequest> findRefundRequest(long requsetId){
        return null;
    }
}
