package com.order.api.controller;

import com.order.api.model.selector.OrderSelector;
import com.order.api.service.OrderService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = OrderController.BASE_PATH)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_PATH = "/order/v1/";

    private final OrderService orderService;

    /**
     * ユーザーの注文情報一覧を取得する
     *
     * @param userId   ユーザーID
     * @param selector {@link OrderSelector} 検索条件
     * @return ユーザーの注文情報一覧 存在しないユーザーの場合は404エラーを返す
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> fetchUserOrders(@PathVariable("userId") @Min(1) long userId,
                                             @Validated @ModelAttribute OrderSelector selector) {
        var response = orderService.fetchUserOrders(userId, selector);
        return ResponseEntity.ok(response);
    }
}
