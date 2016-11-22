package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Orders;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import java.util.Optional;

public class OrdersApi {
    private User user;

    public OrdersApi(User user) {
        this.user = user;
    }

    @Path("{oid}")
    public OrderApi getOrderApi(@PathParam("oid") long oid,
                                @Context Orders orders){
        Optional<Order> order = orders.findById(oid);

        if(!order.isPresent())
            throw new NotFoundException("order not exist");
        return new OrderApi(order.get(), user);
    }

}
