package com.order.api.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Userテーブル Entity
 */
@Data
public class User {

    /**
     * ユーザーID
     */
    private long userId;

    /**
     * 苗字
     */
    private String lastName;

    /**
     * 氏名
     */
    private String firstName;

    /**
     * 生年月日
     */
    private LocalDate birthday;

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
