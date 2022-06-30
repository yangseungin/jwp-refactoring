package kitchenpos.application;

import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("상품서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;
    private Product 지코바치킨;

    @BeforeEach
    void setUp() {
        지코바치킨 = new Product();
        지코바치킨.setId(1L);
        지코바치킨.setName("지코바치킨");
        지코바치킨.setPrice(BigDecimal.valueOf(20000));
    }

    @Test
    void 상품을_등록할_수_있다() {
        given(productDao.save(지코바치킨)).willReturn(지코바치킨);

        Product createdProduct = productService.create(지코바치킨);

        assertAll(
                () -> assertThat(createdProduct.getName()).isEqualTo(지코바치킨.getName()),
                () -> assertThat(createdProduct.getPrice()).isEqualTo(지코바치킨.getPrice()),
                () -> assertThat(createdProduct).isNotNull()
        );
    }

    @Test
    void 가격이_0보다작은_상품을_등록할_수_없다() {
        Product 음수가격의_상품 = new Product();
        음수가격의_상품.setPrice(BigDecimal.valueOf(-1000));
        음수가격의_상품.setName("음수 상품");

        assertThatThrownBy(() -> productService.create(음수가격의_상품))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_조회할_수_있다() {
        given(productDao.findAll()).willReturn(Arrays.asList(지코바치킨));

        List<Product> 상품리스트 = productService.list();

        assertThat(상품리스트).isNotNull();
    }
}
