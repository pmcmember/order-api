package com.order.api.repository;

import com.order.api.model.entity.Order;
import com.order.api.model.result.OrderResult;
import com.order.api.model.selector.OrderSelector;
import java.time.LocalDateTime;
import java.util.Collections;
import org.springframework.stereotype.Repository;

/**
 * 注文情報 Repository
 */
@Repository
public class OrderRepository {

    /**
     * ユーザーの注文情報一覧を取得する
     *
     * @param userId   ユーザーID
     * @param selector {@link OrderSelector} 注文条件
     * @return {@link OrderResult}
     */
    public OrderResult fetchUserOrders(long userId, OrderSelector selector) {

        var mockTotal = 100;
        var mockOrder = new Order();
        mockOrder.setOrderId(10L);
        mockOrder.setProductId(100L);
        mockOrder.setAmount(50);
        mockOrder.setOrderDate(LocalDateTime.of(2022, 7, 15, 15, 0, 0));
        mockOrder.setStatus(1);

        return OrderResult.builder()
                .total(mockTotal)
                .orders(Collections.singletonList(mockOrder))
                .build();
    }
}
