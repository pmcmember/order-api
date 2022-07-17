package com.order.api.model.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * ユーザーの注文情報
 */
@Data
@Builder
public class UserOrder {

    /**
     * 注文ID
     */
    private long orderId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 購入数
     */
    private int amount;

    /**
     * 商品の単価
     */
    private int price;

    /**
     * 注文日時
     */
    private LocalDateTime orderDate;

    /**
     * 注文ステータス
     */
    private Integer status;
}
