package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.CurrentUser;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.Users;
import com.thoughtworks.ketsu.util.Validators;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.ketsu.util.Validators.*;

@Path("/users")
public class UsersApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Map<String, Object> info,
                               @Context Users users,
                               @Context Routes routes){

        Validator userValidator =
                all(fieldNotEmpty("email", "email is required"),
                        fieldNotEmpty("password", "password is required"),
                        unique("email", "email has been taken", users)
                );

        validate(userValidator, info);
        User user = users.createUser(info);

        return Response.status(201).location(routes.userUrl(user)).build();
    }

    @Path("{uid}")
    public UserApi getUserApi(@PathParam("uid") long userId,
                              @Context CurrentUser currentUser,
                              @Context Users users){
        Optional<User> user = users.findById(userId);
        if (!user.isPresent())
            throw new NotFoundException("user not exist");
        if(!currentUser.getCurrentUser().equals(user.get()))
            throw new NotFoundException("user not exist");
        return new UserApi(user.get());
    }
}
