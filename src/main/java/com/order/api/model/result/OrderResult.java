package com.order.api.model.result;

import com.order.api.model.entity.Order;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 注文情報取得結果
 */
@Data
@Builder
public class OrderResult {

    /**
     * 総件数
     */
    private long total;

    /**
     * 注文情報一覧
     */
    private List<Order> orders;

    /**
     * 取得件数を返す
     *
     * @return 取得件数
     */
    public int getCount() {
        return orders.size();
    }
}
