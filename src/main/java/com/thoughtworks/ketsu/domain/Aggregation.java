package com.thoughtworks.ketsu.domain;

import com.thoughtworks.ketsu.domain.order.Order;

import java.util.List;
import java.util.Map;

public interface Aggregation<T> {
    long count();

    List<T> get(long start, long count);

    List<T> all();

    T add(Map<String, Object> aggregation);

    void remove(T aggregation);

    T findBy(long id);
}

