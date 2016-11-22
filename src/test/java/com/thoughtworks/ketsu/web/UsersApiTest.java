package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.Product;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
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

    @Test
    public void should_return_404_when_not_find_product() throws Exception {
        User mockUser = mock(User.class);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(mockUser.findProductById(anyInt())).thenReturn(Optional.empty());

        Response response = get("/users/1/orders/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_detail_when_find_product() throws Exception {
        User mockUser = mock(User.class);
        Product product = new Product(1, "book", 10, mockUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(currentUser.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.findProductById(anyInt())).thenReturn(Optional.of(product));
        when(mockUser.getId()).thenReturn(Long.valueOf("1"));

        Response response = get("/users/1/products/1");
        assertThat(response.getStatus(), is(200));
        Map<String, Object> map = response.readEntity(Map.class);
        assertThat(map.get("uri").toString().contains("/users/1/products/1"), is(true));
    }

    @Test
    public void should_return_201_when_create_product() throws Exception {
        User mockUser = mock(User.class);
        Product product = new Product(1, "book", 10, mockUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(currentUser.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.createProduct(anyMap())).thenReturn(product);
        when(mockUser.getId()).thenReturn(Long.valueOf("1"));

        Response response = post("/users/1/products", TestHelper.productMap("book"));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/1/products/1"), is(true));
    }

    @Test
    public void should_return_400_when_create_product_with_invalid_parameter() throws Exception {
        when(users.findById(anyInt())).thenReturn(Optional.of(user));

        Response response = post("/users/1/products", new HashMap<>());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_return_200_when_gRet_all_products_for_user() throws Exception {
        User mockUser = mock(User.class);
        Product product = new Product(1, "book", 10, mockUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(currentUser.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getAllProductsForUser()).thenReturn(asList(product));
        when(mockUser.getId()).thenReturn(Long.valueOf("1"));

        Response response = get("/users/1/products");
        assertThat(response.getStatus(), is(200));
        List<Map<String, Object>> list = response.readEntity(List.class);
        assertThat(list.size(), is(1));
    }


    @Test
    public void should_return_200_when_change_product_price_success() throws Exception {
        User mockUser = mock(User.class);
        Product product = new Product(1, "book", 10, mockUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(currentUser.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.findProductById(anyInt())).thenReturn(Optional.of(product));

        Response response = post("/users/1/products/1", TestHelper.productPriceMap(12));
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_200_when_change_product_price_with_invalid_parameter() throws Exception {
        User mockUser = mock(User.class);
        Product product = new Product(1, "book", 10, mockUser);
        when(users.findById(anyInt())).thenReturn(Optional.of(mockUser));
        when(currentUser.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.findProductById(anyInt())).thenReturn(Optional.of(product));

        Response response = post("/users/1/products/1", new HashMap<>());
        assertThat(response.getStatus(), is(400));
    }
}
