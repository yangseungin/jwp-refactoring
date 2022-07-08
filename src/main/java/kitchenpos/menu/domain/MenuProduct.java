package kitchenpos.menu.domain;

import kitchenpos.product.domain.Price;
import kitchenpos.product.domain.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class MenuProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private long quantity;

    public MenuProduct() {
    }

    public MenuProduct(long quantity) {
        this.quantity = quantity;
    }

    public MenuProduct(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(Long productId, Long quantity) {
        this(new Product(productId), quantity);
    }

    public static MenuProduct from(Product product, Long quantity) {
        return new MenuProduct(product, quantity);
    }

    public BigDecimal getAmount() {
        return Price.multiply(product, quantity).getValue();
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }
}
