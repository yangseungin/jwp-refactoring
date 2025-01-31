package kitchenpos.order.application;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderLineItemRepository;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderValidator;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.order.dto.OrderStatusRequest;
import kitchenpos.table.domain.OrderTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static kitchenpos.order.application.OrderServiceFactory.주문생성;
import static kitchenpos.order.application.OrderServiceFactory.주문요청생성;
import static kitchenpos.order.application.OrderServiceFactory.주문항목생성;
import static kitchenpos.order.application.OrderServiceFactory.테이블생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;


@DisplayName("주문 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderValidator orderValidator;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MenuRepository menuRepository;
    @InjectMocks
    private OrderService orderService;
    private OrderLineItem orderLineItemRequest;
    private OrderRequest orderRequest;
    private Order order;
    private OrderLineItem orderLineItem;

    @BeforeEach
    void setUp() {
        orderLineItemRequest = new OrderLineItem();
        orderRequest = 주문요청생성(1L, Arrays.asList(orderLineItemRequest));
        order = 주문생성(1L, Arrays.asList(orderLineItemRequest));
        orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(order.getId());
    }

    @Test
    void 주문을_생성할_수_있다() {
        MenuProduct menuProduct = new MenuProduct(1L, 3L);
        Menu menu = new Menu("메뉴이름", 1000, 1L, Arrays.asList(menuProduct));
        given(orderRepository.save(any())).willReturn(order);
        given(menuRepository.findById(any())).willReturn(Optional.of(menu));

        OrderResponse result = orderService.create(orderRequest);

        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.COOKING);
    }

    @Test
    void 주문항목이_비어있으면_주문을_생성할_수_없다() {
        OrderRequest 빈주문 = 주문요청생성(1L, Arrays.asList());

        assertThatThrownBy(() -> orderService.create(빈주문))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴가_존재하지_않으면_주문을_생성할_수_없다() {
        OrderRequest 존재하지않는메뉴_주문 = 주문요청생성(1L, Arrays.asList(new OrderLineItem()));

        doThrow(IllegalArgumentException.class).when(orderValidator).validateOrderLineItems(존재하지않는메뉴_주문.getOrderLineItemRequests());

        assertThatThrownBy(() -> orderService.create(존재하지않는메뉴_주문))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문항목이_존재하지_않으면_주문을_생성할_수_없다() {
        OrderRequest 주문항목이_빈_주문 = 주문요청생성(1L, Arrays.asList());
        assertThatThrownBy(() -> orderService.create(주문항목이_빈_주문))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블이_존재하지않으면_주문을_생성할_수_없다() {
        OrderRequest orderRequest = 주문요청생성(999L, Arrays.asList(orderLineItemRequest));

        doThrow(IllegalArgumentException.class).when(orderValidator).validateOrderLineItems(orderRequest.getOrderLineItemRequests());

        assertThatThrownBy(() -> orderService.create(orderRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문을_조회할_수_있다() {
        Order 주문1 = 주문생성(1L, Arrays.asList(new OrderLineItem()));
        List<Order> orders = Arrays.asList(주문1);
        given(orderRepository.findAll()).willReturn(orders);

        List<OrderResponse> result = orderService.list();

        assertThat(result).hasSize(1);
    }

    @Test
    void 주문의_주문상태를_변경할_수_있다() {
        OrderStatus 식사중 = OrderStatus.MEAL;
        OrderLineItem orderLineItem = 주문항목생성(null, 1);
        OrderStatusRequest request = new OrderStatusRequest(식사중);
        given(orderRepository.findById(any())).willReturn(Optional.of(order));
        orderRequest = 주문요청생성(1L, Arrays.asList(orderLineItem));

        OrderResponse result = orderService.changeOrderStatus(1L, request);

        assertThat(result.getOrderStatus()).isEqualTo(식사중);
    }

    @Test
    void 주문이_존재하지않으면_주문상태를_변경할_수_없다() {
        OrderStatusRequest request = new OrderStatusRequest(OrderStatus.MEAL);
        given(orderRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.changeOrderStatus(1L, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문의_주문상태가_완료되었으면_주문상태를_변경할_수_없다() {
        OrderStatusRequest request = new OrderStatusRequest(OrderStatus.MEAL);
        order.setOrderStatus(OrderStatus.COMPLETION);

        given(orderRepository.findById(any())).willReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.changeOrderStatus(order.getId(), request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
