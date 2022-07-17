package com.order.api.service;

import com.order.api.exception.ResourceNotFoundException;
import com.order.api.factory.UserOrderFactory;
import com.order.api.model.dto.UserOrder;
import com.order.api.model.entity.Order;
import com.order.api.model.entity.Product;
import com.order.api.model.response.UserOrderResponse;
import com.order.api.model.selector.OrderSelector;
import com.order.api.repository.OrderRepository;
import com.order.api.repository.ProductRepository;
import com.order.api.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 注文情報 Service
 * TODO Repository ~ DB周りのロジックは省略しています
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserOrderFactory userOrderFactory;

    /**
     * ユーザーの注文情報一覧を返す
     *
     * @param userId   ユーザーID
     * @param selector {@link OrderSelector} 検索条件
     * @return {@link UserOrderResponse}
     */
    public UserOrderResponse fetchUserOrders(long userId, OrderSelector selector) {

        // ユーザーが見つからない場合404エラーを返す
        userRepository.findOne(userId).orElseThrow(() -> new ResourceNotFoundException("ユーザーが見つかりません"));

        var orderResult = orderRepository.fetchUserOrders(userId, selector);
        List<Order> orders = orderResult.getOrders();
        if (orders.isEmpty()) {
            return UserOrderResponse.empty(orderResult.getTotal(), selector.getStart());
        }

        List<Long> productIds = orders.stream()
                .map(Order::getProductId)
                .distinct()
                .collect(Collectors.toList());

        // 注文情報に紐づく商品情報一覧を取得した上でレスポンスに設定する注文情報を作成して返す
        List<Product> products = productRepository.fetchByIds(productIds);
        List<UserOrder> userOrders = orders.stream()
                .map(order -> userOrderFactory.create(order, products))
                .collect(Collectors.toList());

        return UserOrderResponse.builder()
                .total(orderResult.getTotal())
                .start(selector.getStart())
                .count(orderResult.getCount())
                .orders(userOrders)
                .build();
    }
}
