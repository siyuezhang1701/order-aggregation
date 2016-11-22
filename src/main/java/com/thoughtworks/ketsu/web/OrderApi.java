package com.thoughtworks.ketsu.web;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

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

    @GET
    @Path("payment")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(){
        Optional<Payment> payment = order.findPayment();

        if(!payment.isPresent())
            throw new NotFoundException("payment not exist");
        return payment.get();
    }
}
