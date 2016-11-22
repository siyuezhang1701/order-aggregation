package com.thoughtworks.ketsu.domain.order;

import java.util.Optional;

public interface Orders {
    Optional<Order> findById(long id);
}
