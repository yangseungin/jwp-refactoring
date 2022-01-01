package kitchenpos.application;

import kitchenpos.common.exceptions.EmptyMenuException;
import kitchenpos.dto.order.OrderLineItemRequest;
import kitchenpos.fixture.TestOrderFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {
    @Mock
    private MenuService menuService;
    @InjectMocks
    private OrderValidator orderValidator;

    @DisplayName("주문 상품의 메뉴가 존재해야 한다.")
    @Test
    void validatorMenu() {
        final OrderLineItemRequest 주문상품_요청 = TestOrderFactory.주문_상품_생성_요청(1L, 2);

        assertThatThrownBy(() -> orderValidator.validatorMenu(주문상품_요청))
                .isInstanceOf(EmptyMenuException.class);
    }
}
