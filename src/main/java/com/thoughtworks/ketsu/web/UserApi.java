package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.util.Validators;
import com.thoughtworks.ketsu.web.jersey.ProductApi;
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

public class UserApi {

    private User user;

    public UserApi(User user) {
        this.user = user;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        return user;
    }

    @Path("products")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Map<String, Object> info,
                                  @Context Routes routes) {


        Validators.Validator userValidator =
                all(fieldNotEmpty("name", "name is required"),
                        fieldNotEmpty("price", "price is required")
                );

        validate(userValidator, info);
        Product product = user.createProduct(info);

        return Response.status(201).location(routes.productUrl(user, product)).build();
    }

    @Path("products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts(){
        return user.getAllProductsForUser();
    }

    @Path("products/{pid}")
    public ProductApi findProduct(@PathParam("pid") long pid) {
        Optional<Product> product = user.findProductById(pid);

        if (!product.isPresent())
            throw new NotFoundException("Product not existed");
        return new ProductApi(product.get(), user);
    }

    @Path("orders")
    public OrdersApi getOrderApi(){
        return new OrdersApi(user);
    }
}
