package com.order.api.factory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.order.api.model.entity.Order;
import com.order.api.model.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserOrderFactoryTest {

    UserOrderFactory target;

    @BeforeEach
    public void testInit() {
        target = new UserOrderFactory();
    }

    @Test
    @DisplayName("正常系 create")
    public void create_UserOrderの作成に成功() {
        // setup
        long orderId = 1L;
        long productId = 10L;
        String productName = "test";
        int amount = 2;
        int price = 100;
        LocalDateTime orderDate = LocalDateTime.of(2022, 7, 15, 15, 10, 20);
        int status = 1000;

        // and: 引数の作成
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(productId);
        order.setAmount(amount);
        order.setOrderDate(orderDate);
        order.setStatus(status);

        Product product = new Product();
        product.setProductId(productId);
        product.setName(productName);
        product.setPrice(price);

        // when
        var actual = target.create(order, List.of(product));

        // then
        assertAll(
                () -> assertEquals(orderId, actual.getOrderId()),
                () -> assertEquals(productId, actual.getProductId()),
                () -> assertEquals(productName, actual.getProductName()),
                () -> assertEquals(amount, actual.getAmount()),
                () -> assertEquals(price, actual.getPrice()),
                () -> assertEquals(orderDate, actual.getOrderDate()),
                () -> assertEquals(status, actual.getStatus())
        );
    }

    @Test
    @DisplayName("異常系 create orderに紐づくproductが見つからない場合エラーになる")
    public void create_productIdが紐づくproductがない場合エラーとなる() {
        // setup
        Order order = new Order();
        order.setOrderId(1L);
        order.setProductId(10L);

        Product product1 = new Product();
        product1.setProductId(2L);
        Product product2 = new Product();
        product2.setProductId(3L);

        // when
        // 異常系テストの場合のAssert: https://blogs.oracle.com/otnjp/post/migrating-from-junit-4-to-junit-5-important-differences-and-benefits-ja
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            target.create(order, List.of(product1, product2));
        });
    }
}