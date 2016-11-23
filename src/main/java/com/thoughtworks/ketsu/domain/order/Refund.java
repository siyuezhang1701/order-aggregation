package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Refund implements Record{
    public Refund(Order order, long id) {
        this.order = order;
        this.id = id;
    }

    private long id;
    private RefundRequest refundRequest;
    private double total;
    private Date time;
    private Order order;

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return null;
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.refundUrl(Refund.this));
            put("id", id);
        }};
    }

    public long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }
}
