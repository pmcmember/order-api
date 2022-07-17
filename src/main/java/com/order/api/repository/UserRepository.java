package com.order.api.repository;

import com.order.api.model.entity.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * ユーザー情報 Repository
 */
@Repository
public class UserRepository {

    /**
     * ユーザー情報を返す
     *
     * @param userId ユーザーID
     * @return {@link User}
     */
    public Optional<User> findOne(long userId) {
        var mockUser = new User();
        mockUser.setUserId(1L);
        return Optional.of(mockUser);
    }
}
