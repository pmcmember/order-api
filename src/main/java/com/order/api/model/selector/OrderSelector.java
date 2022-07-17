package com.order.api.model.selector;

import javax.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 注文検索条件
 */
@Data
public class OrderSelector {

    /**
     * 取得開始位置
     */
    @Min(1)
    private long start = 1;

    /**
     * 取得件数
     */
    @Range(min = 0, max = 100)
    private long limit = 20;
}
