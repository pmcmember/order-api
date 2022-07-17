package com.order.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.order.api.exception.ResourceNotFoundException;
import com.order.api.factory.UserOrderFactory;
import com.order.api.model.dto.UserOrder;
import com.order.api.model.entity.Order;
import com.order.api.model.entity.Product;
import com.order.api.model.entity.User;
import com.order.api.model.response.UserOrderResponse;
import com.order.api.model.result.OrderResult;
import com.order.api.model.selector.OrderSelector;
import com.order.api.repository.OrderRepository;
import com.order.api.repository.ProductRepository;
import com.order.api.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

    @InjectMocks
    private OrderService target;

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserOrderFactory orderFactory;

    @BeforeEach
    public void testInit() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fetchUserOrders_正常系_注文情報取得成功() {
        // given:
        long userId = 1L;
        var selector = new OrderSelector();
        selector.setStart(1);

        // and: repositoryからの戻り値定義
        List<Long> orderIdList = List.of(1L, 2L);
        List<Order> orders = orderIdList.stream()
                .map(it -> {
                    var order = new Order();
                    order.setOrderId(it);
                    order.setProductId((it + 10));
                    return order;
                }).collect(Collectors.toList());

        var orderResult = OrderResult.builder()
                .total(100)
                .orders(orders)
                .build();

        List<Long> productIds = orders.stream().map(Order::getProductId).collect(Collectors.toList());
        List<Product> products = List.of(new Product(), new Product());

        // and: response
        var userOrder1 = UserOrder.builder().orderId(1L).build();
        var userOrder2 = UserOrder.builder().orderId(2L).build();
        var expected = UserOrderResponse.builder()
                .total(100)
                .start(1)
                .count(2)
                .orders(List.of(userOrder1, userOrder2))
                .build();

        // mocks
        doReturn(Optional.of(new User())).when(userRepository).findOne(userId);
        doReturn(orderResult).when(orderRepository).fetchUserOrders(userId, selector);
        doReturn(products).when(productRepository).fetchByIds(productIds);
        doReturn(userOrder1).when(orderFactory).create(orders.get(0), products);
        doReturn(userOrder2).when(orderFactory).create(orders.get(1), products);

        // when
        var actual = target.fetchUserOrders(userId, selector);

        // then
        assertEquals(expected, actual);
        verify(userRepository, times(1)).findOne(userId);
        verify(orderRepository, times(1)).fetchUserOrders(userId, selector);
        verify(productRepository, times(1)).fetchByIds(productIds);
        verify(orderFactory, times(2)).create(Mockito.any(), Mockito.anyList()); // 注文数分factoryが呼ばれているか
    }

    @Test
    public void fetchUserOrders_正常系_注文が見つからない場合空レスポンスが返る() {
        // given:
        long userId = 1L;
        var selector = new OrderSelector();
        selector.setStart(1);

        var orderResult = OrderResult.builder()
                .total(100)
                .orders(Collections.emptyList())
                .build();

        // mocks
        doReturn(Optional.of(new User())).when(userRepository).findOne(userId);
        doReturn(orderResult).when(orderRepository).fetchUserOrders(userId, selector);

        // when
        var actual = target.fetchUserOrders(userId, selector);

        // then
        verify(userRepository, times(1)).findOne(userId);
        verify(orderRepository, times(1)).fetchUserOrders(userId, selector);
        assertAll(
                () -> assertEquals(100, actual.getTotal()),
                () -> assertEquals(1, actual.getStart()),
                () -> assertEquals(Collections.emptyList(), actual.getOrders())
        );
    }

    @Test
    @DisplayName("fetchUserOrders　ユーザーが見つからない場合エラーになる")
    public void fetchUserOrders_異常系_ユーザーが見つからない場合() {
        // given:
        long userId = 1L;
        var selector = new OrderSelector();
        doReturn(Optional.empty()).when(userRepository).findOne(userId);

        // when - then
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                target.fetchUserOrders(userId, selector));
        verify(userRepository, times(1)).findOne(userId);
    }
}