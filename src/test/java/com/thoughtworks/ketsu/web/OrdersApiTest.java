package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.order.RefundRequest;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport {

    private User user;
    private Order mockOrder;

    @Override
    @Before
    public void setUp() throws Exception {
        reset(users);
        user = new User(1, "xxx@xxx.com");
        when(currentUser.getCurrentUser()).thenReturn(user);
        when(users.findById(anyInt())).thenReturn(Optional.of(user));
        mockOrder = mock(Order.class);
        when(mockOrder.getOwner()).thenReturn(user);
        when(mockOrder.getId()).thenReturn(Long.valueOf("1"));
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

    @Test
    public void should_return_201_and_uri_when_create_order() throws Exception {
        Order order = new Order(1, user);
        when(orders.createOrder(anyMap(), any(User.class))).thenReturn(order);

        Response response = post("/users/1/orders", TestHelper.orderMap(user));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/1/orders/1"), is(true));
    }

    @Test
    public void should_return_400_when_create_order_with_invalid_parameter() throws Exception {
        Response response = post("/users/1/orders", new HashMap<>());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_return_200_when_get_all_orders_for_user() throws Exception {
        Order order = new Order(1, user);
        when(orders.getOrdersForUser(any(User.class))).thenReturn(asList(order));

        Response response = get("/users/1/orders");
        assertThat(response.getStatus(), is(200));
        List<Map> list = response.readEntity(List.class);
        assertThat(list.size(), is(1));
    }

    @Test
    public void should_return_404_when_not_find_payment() throws Exception {
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.findPayment()).thenReturn(Optional.empty());

        Response response = get("/users/1/orders/1/payment");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_detail_when_find_payment() throws Exception {
        Payment payment = new Payment(mockOrder);
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.findPayment()).thenReturn(Optional.of(payment));

        Response response = get("/users/1/orders/1/payment");
        assertThat(response.getStatus(), is(200));
        Map<String, Object> map = response.readEntity(Map.class);
        assertThat(map.get("uri").toString().contains("/users/1/orders/1/payment"), is(true));
    }

    @Test
    public void should_return_201_and_uri_when_create_payment() throws Exception {
        Payment payment = new Payment(mockOrder);
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.createPayment(anyMap())).thenReturn(payment);

        Response response = post("/users/1/orders/1/payment", new HashMap<>());
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("users/1/orders/1/payment"), is(true));
    }

    @Test
    public void should_return_404_when_not_find_refund_request() throws Exception {
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.findRefundRequest(anyInt())).thenReturn(Optional.empty());

        Response response = get("/users/1/orders/1/refundRequests/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_detail_when_find_refund_request() throws Exception {
        RefundRequest refundRequest = new RefundRequest(1, mockOrder);
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.findRefundRequest(anyInt())).thenReturn(Optional.of(refundRequest));

        Response response = get("/users/1/orders/1/refundRequests/1");
        assertThat(response.getStatus(), is(200));
        Map<String, Object> map = response.readEntity(Map.class);
        assertThat(map.get("uri").toString().contains("/users/1/orders/1/refundRequests/1"), is(true));
    }

    @Test
    public void should_return_201_when_create_refund_request() throws Exception {
        RefundRequest refundRequest = new RefundRequest(1, mockOrder);
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));
        when(mockOrder.createRefundRequest(anyMap(), any(Order.class))).thenReturn(refundRequest);

        Response response = post("/users/1/orders/1/refundRequests", TestHelper.refundRequest());
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/1/orders/1/refundRequests/1"), is(true));
    }

    @Test
    public void should_return_400_when_create_refund_request_with_invalid_parameter() throws Exception {
        when(orders.findById(anyInt())).thenReturn(Optional.of(mockOrder));

        Response response = post("/users/1/orders/1/refundRequests", new HashMap<>());
        assertThat(response.getStatus(), is(400));
    }
}
