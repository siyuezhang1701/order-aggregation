package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Orders;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.util.Validators;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.ketsu.util.Validators.all;
import static com.thoughtworks.ketsu.util.Validators.fieldNotEmpty;
import static com.thoughtworks.ketsu.util.Validators.validate;

public class OrdersApi {
    private User owner;

    public OrdersApi(User owner) {
        this.owner = owner;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Map<String, Object> info,
                                @Context Orders orders,
                                @Context Routes routes){
        Validators.Validator userValidator =
                all(fieldNotEmpty("items", "items is required"));

        validate(userValidator, info);

        Order order = orders.createOrder(info, owner);

        return Response.status(201).location(routes.orderUrl(owner, order)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList(@Context Orders orders){
        return orders.getOrdersForUser(owner);
    }

    @Path("{oid}")
    public OrderApi getOrderApi(@PathParam("oid") long oid,
                                @Context Orders orders){
        Optional<Order> order = orders.findById(oid);

        if(!order.isPresent())
            throw new NotFoundException("order not exist");
        return new OrderApi(order.get(), owner);
    }

}
