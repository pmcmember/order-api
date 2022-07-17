package com.order.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.order.api.model.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    UserRepository target;

    @BeforeEach
    public void before() {
        target = new UserRepository();
    }

    @Test
    public void 引数に渡したIDが設定されたユーザー情報が返ってくる() {
        // setup(テストデータの用意)
        long userId = 10L;
        User user = new User();
        user.setUserId(userId);
        Optional<User> expected = Optional.of(user);

        // when(テストの実行)
        var actual = target.findOne(userId);

        // then(結果の検証)
        assertEquals(expected, actual);
    }
}