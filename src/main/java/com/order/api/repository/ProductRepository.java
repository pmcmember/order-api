package com.order.api.repository;

import com.order.api.model.entity.Product;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 商品情報　リポジトリ
 */
@Repository
public class ProductRepository {

    /**
     * productIdの一覧から商品情報一覧を取得する
     *
     * @param productIds 商品ID一覧
     * @return {@link Product} のリスト
     */
    public List<Product> fetchByIds(List<Long> productIds) {
        var mockProduct = new Product();
        mockProduct.setProductId(100L);
        mockProduct.setName("ワンピース");
        mockProduct.setPrice(1000);
        return Collections.singletonList(mockProduct);
    }
}
