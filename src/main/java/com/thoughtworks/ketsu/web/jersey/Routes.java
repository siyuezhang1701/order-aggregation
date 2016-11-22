package com.thoughtworks.ketsu.web.jersey;


import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.order.RefundRequest;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Map;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI userUrl(User user) {
        return URI.create(String.format("%susers/%s", baseUri, user.getId()));
    }

    public URI productUrl(User user, Product product) {
        return URI.create(String.format("%susers/%s/products/%s", baseUri, user.getId(), product.getId()));
    }

    public URI orderUrl(User user, Order order) {
        return URI.create(String.format("%susers/%s/orders/%s", baseUri, user.getId(), order.getId()));

    }

    public URI paymentUrl(Order order) {
        return URI.create(String.format("%susers/%s/orders/%s/payment", baseUri, order.getOwner().getId(), order.getId()));

    }

    public URI refundRequestURL(RefundRequest refundRequest) {
        return URI.create(String.format("%susers/%s/orders/%s/refundRequests/%s", baseUri, refundRequest.getOrder().getOwner().getId(), refundRequest.getOrder().getId(), refundRequest.getId()));
    }
}
