package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(ApiTestRunner.class)

public class UsersApiTest extends ApiSupport {

    private String email;
    private String password;
    private User user;
    private User otherUser;


    @Override
    @Before
    public void setUp() throws Exception {
        reset(users);
        email = "xxx@xxx.com";
        password = "pass";
        user = new User(1, email);
        otherUser = new User(2, "yyy@xxx.com");
        when(currentUser.getCurrentUser()).thenReturn(user);
        when(users.findBy(anyString())).thenReturn(Optional.empty());
        super.setUp();
    }

    @Test
    public void should_return_404_when_user_not_found() throws Exception {
        when(users.findById(anyInt())).thenReturn(Optional.empty());

        Response response = target("/users/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_200_and_user_detail_when_find_user() throws Exception {
        when(users.findById(anyInt())).thenReturn(Optional.of(user));

        Response response = target("/users/1").request().get();
        assertThat(response.getStatus(), is(200));
        Map<String, Object> map = response.readEntity(Map.class);
        assertThat(map.get("uri").toString().contains("users/1"), is(true));
    }

    @Test
    public void should_return_404_when_other_user_try_to_get_user_detail() throws Exception {
        when(currentUser.getCurrentUser()).thenReturn(otherUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(user));

        Response response = target("/users/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_201_when_create_user() throws Exception {
        when(users.createUser(anyMap())).thenReturn(user);

        Response response = post("/users", TestHelper.userMap(email, password));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/1"), is(true));
    }

    @Test
    public void should_return_400_when_create_user_with_invalid_parameter() throws Exception {
        when(users.createUser(anyMap())).thenReturn(user);

        Response response = post("/users", new HashMap<>());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_return_400_when_create_user_with_used_email() throws Exception {
        when(users.findBy(anyString())).thenReturn(Optional.of(user));

        Response response = post("/users", TestHelper.userMap(email, password));
        assertThat(response.getStatus(), is(400));
    }
}
