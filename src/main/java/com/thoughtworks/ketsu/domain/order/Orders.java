package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.domain.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Orders {
    Optional<Order> findById(long id);

    Order createOrder(Map<String, Object>info, User owner);

    List<Order> getOrdersForUser(User owner);
}
