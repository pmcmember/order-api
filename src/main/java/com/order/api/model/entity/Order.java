package com.order.api.model.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 注文情報
 */
@Data
public class Order {

    /**
     * 注文ID
     */
    private long orderId;

    /**
     * ユーザーID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 購入数
     */
    private int amount;

    /**
     * 注文日時
     */
    private LocalDateTime orderDate;

    /**
     * 注文ステータス
     */
    private Integer status;

    /**
     * 作成者
     */
    private String createdBy;

    /**
     * 作成日時
     */
    private LocalDateTime createdAt;

    /**
     * 作成元
     */
    private String createdSrc;

    /**
     * 更新者
     */
    private String updatedBy;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

    /**
     * 更新元
     */
    private String updatedSrc;
}
