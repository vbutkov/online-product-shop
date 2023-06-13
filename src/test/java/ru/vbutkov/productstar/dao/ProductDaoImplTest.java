package ru.vbutkov.productstar.dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vbutkov.productstar.entity.Article;
import ru.vbutkov.productstar.entity.Bar;
import ru.vbutkov.productstar.entity.Product;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(
        properties = {"jdbc.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductDaoImplTest {

    @Qualifier("productDaoImpl")
    @Autowired
    ProductDao productDao;

    @BeforeEach
    public void beforeEach() {
        productDao.deleteAllProducts();
    }

    @Test
    void getProducts() throws SQLException {
        assertThat(productDao.getProducts()).isEmpty();

        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        productDao.addProduct(new Product(article1, bar1));
        assertThat(productDao.getProducts()).isNotEmpty();
    }

    @Test
    void addProduct() throws SQLException {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        Product product = productDao.addProduct(new Product(article1, bar1));
        assertThat(product.getId()).isNotBlank();
        assertThat(productDao.getProducts())
                .extracting("id")
                .containsExactly(product.getId());
    }

    @Test
    void deleteProduct() {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        Product product1 = productDao.addProduct(new Product(article1, bar1));

        Article article2 = new Article("Молоко Новая деревня 1,5%", "2");
        Bar bar2 = new Bar("4829830394817", List.of(article1), List.of(115.00));

        Product product2 = productDao.addProduct(new Product(article2, bar2));

        productDao.deleteProduct(product2);
        assertThat(productDao.getProductById(product1.getId())).isNotNull();
        assertThatThrownBy(() -> productDao.getProductById(product2.getId())).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getByIdThrowsRuntimeExceptionIfNotProduct() {
        assertThatThrownBy(() -> productDao.getProductById("1")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getByIdReturnCorrectProduct() {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        Product product = productDao.addProduct(new Product(article1, bar1));

        Article article2 = new Article("Молоко Новая деревня 1,5%", "2");
        Bar bar2 = new Bar("4829830394817", List.of(article1), List.of(115.00));

        productDao.addProduct(new Product(article2, bar2));

        assertThat(productDao.getProductById(product.getId())).isNotNull()
                .extracting("article")
                .isEqualTo(product.getArticle());
    }

    @Test
    void deleteAllProducts() throws SQLException {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        productDao.addProduct(new Product(article1, bar1));
        assertThat(productDao.getProducts()).isNotEmpty();

        productDao.deleteAllProducts();
        assertThat(productDao.getProducts()).isEmpty();
    }

    @Test
    void update() {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("4829830394839", List.of(article1), List.of(125.00));

        Product product = productDao.addProduct(new Product(article1, bar1));
        product.getArticle().setName("Кефир Новая деревня 2,5%");

        productDao.updateProduct(product);

        assertThat(productDao.getProductById(product.getId()).getArticle().getName()).isEqualTo("Кефир Новая деревня 2,5%");
    }
}