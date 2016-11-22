package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport {

    private User user;

    @Override
    @Before
    public void setUp() throws Exception {
        reset(users);
        user = new User(1, "xxx@xxx.com");
        when(currentUser.getCurrentUser()).thenReturn(user);
        when(users.findById(anyInt())).thenReturn(Optional.of(user));
        super.setUp();
    }

    @Test
    public void should_return_404_when_order_not_exist() throws Exception {
        when(orders.findById(anyInt())).thenReturn(Optional.empty());

        Response response = get("/users/1/orders/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_detail_when_find_order() throws Exception {
        Order order = new Order(1, user);
        when(orders.findById(anyInt())).thenReturn(Optional.of(order));

        Response response = get("/users/1/orders/1");
        assertThat(response.getStatus(), is(200));
        Map<String, Object> map = response.readEntity(Map.class);
        assertThat(map.get("uri").toString().contains("/users/1/orders/1"), is(true));
    }
}
