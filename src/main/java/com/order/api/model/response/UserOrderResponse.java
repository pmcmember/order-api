package com.order.api.model.response;

import com.order.api.model.dto.UserOrder;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * ユーザーの注文情報レスポンス
 */
@Data
@Builder
public class UserOrderResponse {

    /**
     * 総件数
     */
    private long total;

    /**
     * 取得開始位置
     */
    private long start;

    /**
     * 取得件数
     */
    private long count;

    /**
     * 注文情報一覧
     */
    private List<UserOrder> orders;

    /**
     * ordersが空のレスポンスを作成して返す
     *
     * @param start 取得開始位置
     * @param total 総件数
     * @return {@link UserOrderResponse}
     */
    public static UserOrderResponse empty(long total, long start) {
        return UserOrderResponse.builder()
                .total(total)
                .start(start)
                .count(0)
                .orders(Collections.emptyList())
                .build();
    }
}
