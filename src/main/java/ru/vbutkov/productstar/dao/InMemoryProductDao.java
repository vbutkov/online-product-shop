package ru.vbutkov.productstar.dao;

import org.springframework.stereotype.Repository;
import ru.vbutkov.productstar.entity.Article;
import ru.vbutkov.productstar.entity.Bar;
import ru.vbutkov.productstar.entity.Product;
import ru.vbutkov.productstar.entity.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryProductDao implements ProductDao {

    private List<Product> products;

    public InMemoryProductDao() {
        this.products = new ArrayList<>();
        initSomeProducts();
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void deleteProduct(Product product) {
        products.remove(product);

    }

    private void initSomeProducts() {
        Article article1 = new Article("Молоко Новая деревня 2,5%", "1");
        Bar bar1 = new Bar("11000001", List.of(article1), List.of(100.00));
        products.add(new Product(UUID.randomUUID().toString(), article1, bar1));

        Article article2 = new Article("Сыр масдам 45%", "2");
        Bar bar2 = new Bar("10000001", List.of(article2), List.of(450.00), Unit.кг);
        products.add(new Product(UUID.randomUUID().toString(), article2, bar2));
    }
}
