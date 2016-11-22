package com.thoughtworks.ketsu.web;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class OrderApi {
    private User user;
    private Order order;

    public OrderApi(Order order, User user) {
        this.order = order;
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(){
        return order;
    }
}
