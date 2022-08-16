package com.order.api;

import org.springframework.util.StringUtils;

public class Sample {

    /**
     * 苗字と名前を連結して返す
     *
     * @param lastName  苗字
     * @param firstName 名前
     * @return フルネーム
     *
     * @throws IllegalArgumentException 苗字、名前のいずれか未設定の場合throw
     */
    public String getFullName(String lastName, String firstName) {
        if (!StringUtils.hasText(lastName) || !StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("名前が未設定です");
        }
        return lastName + firstName;
    }
}
