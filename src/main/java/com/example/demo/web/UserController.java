package com.example.demo.web;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Path("/users")
public class UserController {
    private final List<User> userList = new ArrayList<>(Arrays.asList(new User("John", 20), new User("Anna", 22)));

    @GET
    @Produces("application/json")
    public List<User> getUsers() {
        log.info("get all users");
        log.info("get all users 1");
        log.info("get all users 2");
        return userList;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUser(@PathParam("id") int id) {
        log.info("get user with id " + id);

        try {
            return Response.ok(userList.get(id)).build();
        } catch (final IndexOutOfBoundsException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User with id " + id + " does not exist.").build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(@RequestBody @Valid @NotNull(message = "User must not be null") final User newUser) {
        log.info("create user " + newUser);
        userList.add(newUser);
        return Response.ok(newUser).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteUser(@RequestBody @Valid @NotNull(message = "User must not be null") final User deletedUser) {
        log.info("delete user " + deletedUser);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(@RequestBody @Valid @NotNull(message = "User must not be null") final User updatedUser) {
        log.info("update user " + updatedUser);
        return Response.ok(updatedUser).build();
    }
}