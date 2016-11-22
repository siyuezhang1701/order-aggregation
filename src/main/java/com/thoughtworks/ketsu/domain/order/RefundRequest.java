package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundRequest implements Record{
    private long id;
    private Order order;
    private List<OrderItem> items;
    private double totalPrice;
    private Date Time;

    public RefundRequest(long id, Order order) {
        this.id = id;
        this.order = order;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.refundRequestURL(RefundRequest.this));
            put("order", order.getId());
        }};
    }

    public Order getOrder() {
        return order;
    }

    public Object getId() {
        return id;
    }
}
