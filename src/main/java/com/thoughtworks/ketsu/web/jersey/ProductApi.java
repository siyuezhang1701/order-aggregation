package com.thoughtworks.ketsu.web.jersey;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.util.Validators;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.ketsu.util.Validators.all;
import static com.thoughtworks.ketsu.util.Validators.fieldNotEmpty;
import static com.thoughtworks.ketsu.util.Validators.validate;

public class ProductApi {
    private Product product;
    private User user;

    public ProductApi(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product findProduct(){
        return product;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeProductPrice(Map<String, Object> info,
                                       @PathParam("pid") long pid) {

        Validators.Validator userValidator =
                all(fieldNotEmpty("price", "price is required"));

        validate(userValidator, info);
       user.changeProductPrice(product, Double.valueOf(info.get("price").toString()));
        return Response.status(200).build();
    }
}
