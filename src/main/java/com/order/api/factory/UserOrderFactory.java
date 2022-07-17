package com.order.api.factory;

import com.order.api.model.dto.UserOrder;
import com.order.api.model.entity.Order;
import com.order.api.model.entity.Product;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * UserOrderを作成する
 */
@Component
public class UserOrderFactory {

    /**
     * UserOrderを作成して返す
     *
     * @param order    {@link Order} 注文情報
     * @param products {@link Product} のリスト
     * @return {@link UserOrder}
     */
    public UserOrder create(Order order, List<Product> products) {
        var productId = order.getProductId();
        var product = products.stream()
                .filter(pr -> pr.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "注文に紐づく商品情報が見つかりません productId: " + productId)
                );

        return UserOrder.builder()
                .orderId(order.getOrderId())
                .productId(productId)
                .productName(product.getName())
                .amount(order.getAmount())
                .price(product.getPrice())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .build();
    }
}
