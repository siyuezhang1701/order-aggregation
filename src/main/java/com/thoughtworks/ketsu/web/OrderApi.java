package com.thoughtworks.ketsu.web;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.order.RefundRequest;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
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

    @POST
    @Path("payment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(Map<String, Object>info,
                                  @Context Routes routes){

        Payment payment = order.createPayment(info);

        return Response.status(201).location(routes.paymentUrl(order)).build();
    }

    @GET
    @Path("refundRequests/{requestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RefundRequest findRefundRequest(@PathParam("requestId")long requestId){
        Optional<RefundRequest> refundRequest = order.findRefundRequest(requestId);

        if(!refundRequest.isPresent())
            throw new NotFoundException("refund request not exist");
        return refundRequest.get();
    }
}
