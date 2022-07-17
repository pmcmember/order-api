package com.order.api.model.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 注文情報
 */
@Data
public class Product {

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 単価
     */
    private int price;

    /**
     * 在庫数
     */
    private int stockAmount;

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
