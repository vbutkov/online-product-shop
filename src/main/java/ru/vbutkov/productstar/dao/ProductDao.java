package ru.vbutkov.productstar.dao;

import ru.vbutkov.productstar.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> getProducts() throws SQLException;

    Product addProduct(Product product);

    void deleteProduct(Product product);

    Product getProductById(String id);

    void deleteAllProducts();

    Product updateProduct(Product product);
}
