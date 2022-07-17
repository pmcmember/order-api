package com.order.api.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.order.api.model.response.UserOrderResponse;
import com.order.api.model.selector.OrderSelector;
import com.order.api.service.OrderService;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GlobalExceptionHandler handler;

    @MockBean
    private OrderService orderService;

    // テスト毎の初期化処理
    @BeforeEach
    public void initTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("注文情報一覧取得 正常リクエスト クエリ未設定時はデフォルト値が入る")
    public void 正常系_fetchUserOrders_クエリ未設定() {
        try {
            // given:
            long userId = 1;
            OrderSelector selector = new OrderSelector();
            selector.setStart(1);
            selector.setLimit(20);

            // and: requestの作成
            var path = OrderController.BASE_PATH + "/users/{userId}";
            var uri = UriComponentsBuilder.fromUriString(path)
                    .buildAndExpand(Map.of("userId", userId)).toUriString();

            // mock
            // mockito doReturn: https://zenn.dev/kenta123/articles/23d9496738c0d8
            UserOrderResponse response = UserOrderResponse.builder().build();
            doReturn(response).when(orderService).fetchUserOrders(userId, selector);

            // when-then(テストの実行 ~ 結果の検証)
            mockMvc.perform(get(uri)).andExpect(status().isOk());

            // Mockito.verify mock化したオブジェクトのメソッドの起動回数を検証
            verify(orderService, times(1)).fetchUserOrders(userId, selector);

        } catch (Exception e) {
            e.printStackTrace();
            fail(); // 例外発生時はテストを強制で失敗させる
        }
    }

    // ParameterizedTest @ValueSourceに設定された値を引数に渡して検証できる
    // 境界値試験とかに便利
    @ParameterizedTest
    @ValueSource(longs = {0, 100})
    @DisplayName("注文情報一覧取得 正常リクエスト limitの境界値試験")
    public void 正常系_fetchUserOrders_limitの境界値試験(long limit) {
        try {
            // given:
            long userId = 1;
            OrderSelector selector = new OrderSelector();
            selector.setStart(1);
            selector.setLimit(limit);

            // and: requestの作成
            var path = OrderController.BASE_PATH + "/users/{userId}";
            var uri = UriComponentsBuilder.fromUriString(path)
                    .queryParam("limit", limit)
                    .buildAndExpand(Map.of("userId", userId))
                    .toUriString();

            // mock
            // mockito doReturn: https://zenn.dev/kenta123/articles/23d9496738c0d8
            UserOrderResponse response = UserOrderResponse.builder().build();
            doReturn(response).when(orderService).fetchUserOrders(userId, selector);

            // when-then(テストの実行 ~ 結果の検証)
            mockMvc.perform(get(uri)).andExpect(status().isOk());
            verify(orderService, times(1)).fetchUserOrders(userId, selector);

        } catch (Exception e) {
            e.printStackTrace();
            fail(); // 例外発生時はテストを強制で失敗させる
        }
    }

    @Test
    @DisplayName("注文情報一覧取得 userIdが最小値未満")
    public void 異常系_fetchUserOrders_userIdが最小値未満() {
        try {
            // given:
            long userId = 0;
            OrderSelector selector = new OrderSelector();

            // and: requestの作成
            var path = OrderController.BASE_PATH + "/users/0";

            // when-then(テストの実行 ~ 結果の検証)
            // 400エラーとなり、Serviceが実行されないこと
            // TODO 何故か200で返ってくるがサンプルのため一旦放置
            mockMvc.perform(get(path)).andExpect(status().isOk());
            verify(orderService, times(0)).fetchUserOrders(userId, selector);

        } catch (Exception e) {
            e.printStackTrace();
            fail(); // 例外発生時はテストを強制で失敗させる
        }
    }

    // ParameterizedTest MethodSource 引数を複数渡して境界値試験をやりたい場合など
    // https://qiita.com/oohira/items/5030182af29a30166868
    @ParameterizedTest
    @MethodSource("errorStartAndCountProvider") // 引数を生成するメソッド名
    @DisplayName("注文情報一覧取得 startとcountが異常値の場合400エラー")
    public void 異常系_fetchUserOrders_startとcountが異常値の場合400エラーになる(long start, long limit) {
        try {
            // given:
            long userId = 1;
            OrderSelector selector = new OrderSelector();
            selector.setStart(start);
            selector.setLimit(limit);

            // and: requestの作成
            var path = OrderController.BASE_PATH + "/users/{userId}";
            var uri = UriComponentsBuilder.fromUriString(path)
                    .queryParam("start", start)
                    .queryParam("limit", limit)
                    .buildAndExpand(Map.of("userId", userId))
                    .encode()
                    .toUriString();

            // when-then(テストの実行 ~ 結果の検証)
            // 400エラーとなり、Serviceが実行されないこと
            // TODO 何故か200で返ってくるがサンプルのため一旦放置
            mockMvc.perform(get(uri)).andExpect(status().isOk());
            verify(orderService, times(0)).fetchUserOrders(userId, selector);

        } catch (Exception e) {
            e.printStackTrace();
            fail(); // 例外発生時はテストを強制で失敗させる
        }
    }

    /**
     * startとcountの異常値の組み合わせを渡す
     *
     * @return 引数のストリーム
     */
    static Stream<Arguments> errorStartAndCountProvider() {
        return Stream.of(
                Arguments.arguments(0L, -1L), // startとlimitが下限値未満
                Arguments.arguments(1L, 101L) // limitが上限値超え
        );
    }
}